package com.roche.web.api.jsend

import com.roche.web.api.ApiProperties
import com.roche.web.api.ApiTestHelper
import org.springframework.core.MethodParameter
import org.springframework.http.ResponseEntity
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import spock.lang.Specification
import spock.lang.Unroll

import javax.servlet.http.HttpServletRequest

class ApiResponseWrapperSpec extends Specification {

    ApiProperties properties = ApiTestHelper.apiProperties
    ApiResponseWrapper wrapper = new ApiResponseWrapper(properties)

    def cleanup() {
        RequestContextHolder.resetRequestAttributes()
    }

    @Unroll
    def 'should not support wrap response when type unsupported'() {
        given:
        def returnType = new MethodParameter(method, -1)

        expect:
        !wrapper.supports(returnType, null)

        where:
        method << ApiResponseMethods.unsupported().testMethods
    }

    def 'should not support wrap response when not API endpoint'() {
        given:
        setupRequest(path)

        expect:
        !wrapper.supports(supportedResponseMethod, null)

        where:
        path << ['somepath/test', '', 'test']
    }

    def 'should not support wrap response when request not exist'() {
        expect:
        !wrapper.supports(supportedResponseMethod, null)
    }

    def 'should support wrap response when API endpoint, type supported and request exist'() {
        given:
        def request = Mock(HttpServletRequest) {
            getRequestURI() >> 'api/test'
        }
        def attributes = new ServletRequestAttributes(request) //FIXME unable to mock properly with interaction - to investigate
        RequestContextHolder.setRequestAttributes(attributes)

        expect:
        wrapper.supports(supportedResponseMethod, null)
    }

    def 'should wrap object'() {

        when:
        def result = wrapper.beforeBodyWrite(data, null, null, null, null, null)

        then:
        result instanceof ApiResponse
        result.data == data

        where:
        data << [new Object(), null]
    }

    def 'should wrap response entity'() {
        given:
        def data = Mock(Object)
        def entity = new ResponseEntity.DefaultBuilder(200).body(data)

        when:
        def result = wrapper.beforeBodyWrite(entity, null, null, null, null, null)

        then:
        result instanceof ResponseEntity
        result.body instanceof ApiResponse
        result.body.data == data
    }

    private setupRequest(String path) {
        def attributes = Mock(ServletRequestAttributes)
        def request = Mock(HttpServletRequest) {
            getRequestURI() >> path
        }
        attributes.request >> request
        RequestContextHolder.setRequestAttributes(attributes)
    }

    private getSupportedResponseMethod() {
        def method = ApiResponseMethods.supported().testMethods[0]
        new MethodParameter(method, -1)
    }

}
