package com.roche.web.api

import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.mvc.method.RequestMappingInfo
import spock.lang.Specification

/**
 * @author Mateusz Filipowicz (mateusz.filipowicz@roche.com).
 */
@WebAppConfiguration
@ContextConfiguration(classes = [TestConfiguration, ApiTestController, TestController])
@Slf4j
class RequestMappingHandlerMappingIntSpec extends Specification {

    @Autowired
    ApplicationContext context

    @Autowired
    ApiRequestMappingHandlerMapping mapping

    def 'new request mapping handler mapping is created properly'() {
        expect:
        mapping.handlerMethods.size() == 1
        for (Map.Entry<RequestMappingInfo, HandlerMethod> map : mapping.handlerMethods) {
            assert map.value.beanType == TestController
            assert map.key.patternsCondition.patterns[0] == '/api/v1/test/aaa'
        }

    }

}