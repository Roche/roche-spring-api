package com.roche.web.swagger

import spock.lang.Specification

class CustomParameterBuilderSpec extends Specification {

    def 'should build header parameter'() {
        when:
        def param = CustomParameterBuilder.buildForHeader('headerName')

        then:
        param.with {
            name == 'headerName'
            paramType == 'header'
            modelRef.type == 'string'
        }
    }

    def 'should build cookie parameter'() {
        when:
        def param = CustomParameterBuilder.buildForCookie('cookieName')

        then:
        param.with {
            name == 'cookieName'
            paramType == 'cookie'
            modelRef.type == 'string'
        }
    }
}