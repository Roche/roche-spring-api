package com.roche.web.api

import com.roche.web.annotation.Api
import org.springframework.web.servlet.mvc.condition.*
import org.springframework.web.servlet.mvc.method.RequestMappingInfo
import spock.lang.Specification

import java.lang.reflect.Method

/**
 * Created by Mateusz Filipowicz (mateusz.filipowicz@roche.com).
 */
abstract class BaseApiSpec extends Specification {

    static final String BASE_PATH = '/service/resource'

    def createPatternConditions(List<String> patterns) {
        new PatternsRequestCondition(patterns.toArray(new String[patterns.size()]))
    }

    def createRequestMappingInfo(ConsumesRequestCondition consumes = new ConsumesRequestCondition(),
                                 ProducesRequestCondition produces = new ProducesRequestCondition(),
                                 PatternsRequestCondition patterns = new PatternsRequestCondition(BASE_PATH),
                                 RequestMethodsRequestCondition methods = new RequestMethodsRequestCondition(),
                                 ParamsRequestCondition params = new ParamsRequestCondition(),
                                 HeadersRequestCondition headers = new HeadersRequestCondition()) {
        new RequestMappingInfo(patterns, methods, params, headers, consumes, produces, null)
    }

    def createRequestMappingInfo(PatternsRequestCondition patterns) {
        createRequestMappingInfo(new ConsumesRequestCondition(), new ProducesRequestCondition(), patterns)
    }

    def createRequestMappingInfo(String consumes, String produces, String path = BASE_PATH) {
        createRequestMappingInfo(new ConsumesRequestCondition(consumes), new ProducesRequestCondition(produces), new PatternsRequestCondition(path))
    }

    def createBuilder(RequestMappingInfo info = createRequestMappingInfo(), Api api, Method method, Class handlerType = BaseApiSpec) {
        new ApiBuilder(api, method, handlerType, info, ApiTestHelper.getApiProperties())
    }

    def createBuilder(RequestMappingInfo info = createRequestMappingInfo()) {
        createBuilder(info, Mock(Api))
    }

    def createBuilder(RequestMappingInfo info = createRequestMappingInfo(), Api api) {
        createBuilder(info, api, ApiTestController.declaredMethods[0], ApiTestController)
    }
}