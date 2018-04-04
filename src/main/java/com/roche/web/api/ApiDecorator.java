package com.roche.web.api;

/**
 * Created by Mateusz Filipowicz (mateusz.filipowicz@roche.com).
 */
public interface ApiDecorator {

    void decorate(ApiBuilder builder);

    boolean supports(ApiBuilder builder);

}
