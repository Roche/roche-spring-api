package com.roche.web.api

import com.roche.web.annotation.Api
import com.roche.web.annotation.ApiVersion
import spock.lang.Shared

/**
 * Created by Mateusz Filipowicz (mateusz.filipowicz@roche.com).
 */
class ApiVersionPathStrategySpec extends BaseApiSpec {

    static final String VERSION_1 = 'v1'
    static final String VERSION_2 = 'v2'

    ApiVersionNamingProvider provider = Mock(ApiVersionNamingProvider) {
        nameForVersion(_) >> VERSION_1
        nameForVersion(_, _) >> VERSION_1
    }
    @Shared
    ApiProperties properties = ApiTestHelper.apiProperties
    ApiVersioningPathStrategy strategy = new ApiVersioningPathStrategy(provider, properties)

    void 'path version is correctly added'() {
        given:
        def mappingInfo = createRequestMappingInfo(createPatternConditions(paths))
        def version = Mock(ApiVersion) {
            value() >> 1
        }
        def api = Mock(Api) {
            version() >> version
        }
        def builder = createBuilder(mappingInfo, api)

        when:
        strategy.decorate(builder)
        mappingInfo = builder.build()

        then:
        mappingInfo.patternsCondition.patterns.size() == expectedPatterns
        mappingInfo.patternsCondition.patterns.each { assert it.contains(VERSION_1) || it.contains(VERSION_2) }

        where:
        paths = ['/api/test',
                 '/test',
                 'v1/',
                 'v1/test',
                 '/api/v1/test',
                 '/api/v2/test'
        ]
        expectedPatterns = 4
    }
}