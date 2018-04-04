package com.roche.web.api

import spock.lang.Shared
import spock.lang.Specification


/**
 * @author Mateusz Filipowicz (mateusz.filipowicz@roche.com).
 */
class BasePathNamingProviderSpec extends Specification {

    @Shared
    BasePathNamingProvider provider = new BasePathNamingProvider()

    void 'for handler #handler resolved path name is #name'() {
        expect:
        provider.getNameForHandlerType(handler) == name

        where:
        handler                 || name
        TestController.class    || 'test'
        ApiTestController.class || 'apiTest'
    }

}