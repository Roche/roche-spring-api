package com.roche.web.api

import spock.lang.Ignore
import spock.lang.Specification
import spock.lang.Unroll


/**
 * Created by Mateusz Filipowicz (mateusz.filipowicz@roche.com).
 */
class ApiVersionNamingProviderSpec extends Specification {

    ApiVersionNamingProvider provider = new ApiVersionNamingProvider(ApiTestHelper.apiProperties)

    @Unroll
    void 'for version #version version name is #expectedResult'() {
        expect:
        provider.nameForVersion(version) == expectedResult

        where:
        version || expectedResult
        -1      || ''
        0       || ''
        1       || 'v1'
    }

    @Unroll
    @Ignore("not implemented")
    void 'for latest version #version version name is #expectedResult'() {
        expect:
        provider.nameForVersion(version, true) == expectedResult

        where:
        version || expectedResult
        -1      || 'latest'
        0       || 'latest'
        1       || 'latest'
    }

}