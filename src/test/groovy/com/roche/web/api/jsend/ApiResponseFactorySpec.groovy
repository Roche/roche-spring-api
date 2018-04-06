package com.roche.web.api.jsend

import com.roche.web.api.ApiException
import spock.lang.Specification

class ApiResponseFactorySpec extends Specification {

    void 'should create success response'() {
        when:
        def response = ApiResponseFactory.getApiResponse(object)

        then:
        response.with {
            status == ApiResponseStatus.SUCCESS
            data == object
            !message
        }

        where: 'data is null or not exception'
        object << [null, new Object()]
    }

    void 'should create fail response'() {
        when:
        def response = ApiResponseFactory.getApiResponse(apiException)

        then:
        response.with {
            status == ApiResponseStatus.FAIL
            data == apiException
            message == apiException.message
        }

        where: 'data is an object of type ApiException'
        apiException = new ApiException('test')
    }

    void 'should create error response'() {
        when:
        def response = ApiResponseFactory.getApiResponse(exception)

        then:
        response.with {
            status == ApiResponseStatus.ERROR
            data == null
            message == exception.message
        }

        where: 'data is object of type Exception'
        exception = new Exception('test')
    }
}
