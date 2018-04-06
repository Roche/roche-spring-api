package com.roche.web.api

import spock.lang.Specification
import spock.lang.Unroll

import static com.roche.web.annotation.ApiVersion.UNVERSIONED
import static com.roche.web.api.ApiTestHelper.*


class ApiUtilsSpec extends Specification {

    @Unroll
    void 'for path #path version is #expectedVersion'() {
        expect:
        ApiUtils.extractVersionFromPath(path, VERSION_PREFIX) == expectedVersion

        where:
        path                                                          || expectedVersion
        '/test/'                                                      || UNVERSIONED
        PATH_PREFIX                                                   || UNVERSIONED
        null                                                          || UNVERSIONED
        ''                                                            || UNVERSIONED
        VERSION_PREFIX + '-1'                                         || -1
        VERSION_PREFIX + '0'                                          || 0
        VERSION_PREFIX + '99'                                         || 99
        '/' + PATH_PREFIX + '/' + VERSION_PREFIX + '99/test'          || 99
        '/' + PATH_PREFIX + '/vtest/' + VERSION_PREFIX + '99/v1/test' || 99
    }

    @Unroll
    void 'for contentType #contentType version is #expectedVersion'() {
        expect:
        ApiUtils.extractVersionFromContentType(contentType, CONTENT_TYPE_VND, VERSION_PREFIX) == expectedVersion

        where:
        contentType                                             || expectedVersion
        'text/html'                                             || UNVERSIONED
        "${VERSION_PREFIX}1+json"                               || UNVERSIONED
        null                                                    || UNVERSIONED
        ''                                                      || UNVERSIONED
        "application/$CONTENT_TYPE_VND+json"                    || UNVERSIONED
        "application/${CONTENT_TYPE_VND}.version1+json"         || UNVERSIONED
        "application/$CONTENT_TYPE_VND.${VERSION_PREFIX}1+json" || 1
        "test/$CONTENT_TYPE_VND.${VERSION_PREFIX}99/test"       || 99
    }

    void 'version is applied'() {
        given:
        def targets = [new TestVersionTarget(), new TestVersionTarget(), new TestVersionTarget()]

        when:
        ApiUtils.applyVersion(targets, '5')

        then:
        targets.every { it.version == '5' }
    }

    private class TestVersionTarget implements VersionTarget {
        String version
    }
}