package com.roche.web.api

import com.roche.web.annotation.Api
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition
import spock.lang.Shared
import spock.lang.Unroll


class ContextPathEnhancerSpec extends BaseApiSpec {

    @Shared
    ApiProperties properties = ApiTestHelper.getApiProperties(true)
    ContextPathEnhancer enhancer = new ContextPathEnhancer(properties)

    @Unroll
    void 'for path #path and context #baseContext expected path is #expectedPath'() {
        given:
        def aeroApi = Mock(Api)
        def info = createRequestMappingInfo(new PatternsRequestCondition(path))
        def builder = createBuilder(info, aeroApi)

        when:
        enhancer.decorate(builder)
        info = builder.build()

        then:
        1 * aeroApi.baseContext() >> baseContext
        info.patternsCondition.patterns.size() == 1
        info.patternsCondition.patterns[0] == expectedPath

        where:
        path                    | baseContext || expectedPath
        '/api/v1/service/1'     | 'test'      || '/api/v1/test/service/1'
        '/v1/service'           | 'test'      || '/v1/test/service'
        '/service'              | 'test'      || '/test/service'
        'service/resource'      | 'test'      || '/test/service/resource'
        'service/test/resource' | 'test'      || '/service/test/resource'
        'service/test/resource' | 'resource'  || '/resource/service/test/resource'
        '/api/service/resource' | 'test'      || '/api/test/service/resource'
        '/api/service/resource' | ''          || "/api/${properties.baseContext}/service/resource"
        '/api/service/resource' | null        || "/api/${properties.baseContext}/service/resource"
    }

}