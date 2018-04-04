package com.roche.web.api

import com.roche.web.annotation.Api
import com.roche.web.annotation.ApiVersion
import org.springframework.web.servlet.mvc.condition.ConsumesRequestCondition
import org.springframework.web.servlet.mvc.condition.ProducesRequestCondition
import spock.lang.Shared
import spock.lang.Unroll

/**
 * Created by Mateusz Filipowicz (mateusz.filipowicz@roche.com).
 */
class ApiVersionContentStrategySpec extends BaseApiSpec {

    static final String VERSION_1 = 'v1'

    ApiVersionNamingProvider provider = Mock(ApiVersionNamingProvider) {
        nameForVersion(_) >> VERSION_1
        nameForVersion(_, _) >> VERSION_1
    }
    @Shared
    ApiProperties properties = ApiTestHelper.getApiProperties(false, true)
    ApiVersioningContentTypeStrategy strategy = new ApiVersioningContentTypeStrategy(provider, properties)

    @Unroll
    void 'for #contentType expected types are #expectedContentTypes'() {
        given:
        def consumer = new ConsumesRequestCondition(contentType)
        def producer = new ProducesRequestCondition(contentType)
        def mappingInfo = createRequestMappingInfo(consumer, producer)
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
        def cons = mappingInfo.consumesCondition.expressions
        def prod = mappingInfo.producesCondition.expressions
        def conditions = []
        conditions.addAll(cons)
        conditions.addAll(prod)
        conditions.each { condition ->
            def list = condition.inject([]) { list, exp ->
                list << exp.mediaType.toString()
            }
            assert contains(list, expectedContentType)
        }

        where:
        contentType                                        | expectedContentType
        'application/json'                                 | "application/${properties.contentTypeVnd}.v1+json"
        "application/${properties.contentTypeVnd}.v1+json" | "application/${properties.contentTypeVnd}.v1+json"
        'text/html'                                        | "text/${properties.contentTypeVnd}.v1+html"
        "application/${properties.contentTypeVnd}.v2+json" | "application/${properties.contentTypeVnd}.v2+json"
    }

    void 'if no content type defined empty expressions'() {

        given:
        def mappingInfo = createRequestMappingInfo()
        def builder = createBuilder(mappingInfo)

        when:
        strategy.decorate(builder)
        mappingInfo = builder.build()

        then:
        mappingInfo.consumesCondition.expressions.empty
        mappingInfo.producesCondition.expressions.empty
    }

    def contains(list, stringValue) {
        for (value in list) {
            return value == stringValue
        }
        return false
    }
}