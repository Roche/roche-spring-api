package com.roche.web.api

class ApiPathEnhancerSpec extends BaseApiSpec {

    ApiProperties properties = ApiTestHelper.apiProperties
    ApiPathEnhancer enhancer = new ApiPathEnhancer(properties)

    void 'api prefix is added properly'() {
        given:
        def mappingInfo = createRequestMappingInfo(createPatternConditions(paths))
        def builder = createBuilder(mappingInfo)

        when:
        enhancer.decorate(builder)
        mappingInfo = builder.build()

        then:
        def patterns = mappingInfo.patternsCondition.patterns
        patterns.size() == expectedSize
        patterns.every() { it.startsWith('/api') }

        where:
        paths = ['/api/test',
                 'api/test',
                 '/test',
                 'test',

                 '/api/test55',

                 'test/test2',
                 '/test/test2',
        ]
        expectedSize = 3
    }
}