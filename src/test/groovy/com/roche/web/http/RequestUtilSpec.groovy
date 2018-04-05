package com.roche.web.http

import spock.lang.Specification
import spock.lang.Unroll

import javax.servlet.http.HttpServletRequest



class RequestUtilSpec extends Specification {

    @Unroll
    def 'request is xhr == #expectedResult when xhr header has #xhrHeader'() {
        given:
        def request = Mock(HttpServletRequest)

        when:
        def result = RequestUtil.isXhr(request)

        then:
        1 * request.getHeader(HttpConst.Headers.X_REQUESTED_WITH) >> xhrHeader
        result == expectedResult

        where:
        xhrHeader                  || expectedResult
        null                       || false
        'test'                     || false
        HttpConst.XML_HTTP_REQUEST || true
    }

    def 'request attributes not exist without request'() {
        when:
        def result = RequestUtil.getRequestAttributes()

        then:
        !result.isPresent()
    }

}