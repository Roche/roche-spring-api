package com.roche.web.api

import com.roche.web.annotation.Api
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import spock.lang.Specification



class ApiRequestMappingHandlerMappingSpec extends Specification {

    void 'API decorating works when enabled'() {
        given:
        def decorator = Mock(ApiDecorator)
        def namingProvider = Mock(EmptyPathNamingProvider)
        def mapping = new ApiRequestMappingHandlerMapping([decorator], ApiTestHelper.apiProperties, namingProvider)

        when:
        mapping.getMappingForMethod(clss.getMethod('test'), clss)

        then:
        n * decorator.decorate(_)
        n * decorator.supports(_) >> true
        n * namingProvider.getNameForHandlerType(clss) >> 'test'

        where:
        clss  | n
        Test1 | 1
        Test2 | 0
    }

    @Api
    class Test1 {
        @GetMapping
        def test() {

        }
    }

    @RestController
    class Test2 {
        @GetMapping
        def test() {

        }
    }
}