package com.roche.web.api;

import org.springframework.web.servlet.mvc.method.RequestMappingInfo;

import java.util.Set;

/**
 * Created by Mateusz Filipowicz (mateusz.filipowicz@roche.com).
 */
interface ApiVersionStrategy extends ApiDecorator {

    Set<Integer> parseVersion(RequestMappingInfo requestMappingInfo);

    @Override
    default boolean supports(ApiBuilder builder) {
        return true;
    }
}
