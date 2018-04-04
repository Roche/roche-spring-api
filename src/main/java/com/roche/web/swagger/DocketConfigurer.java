package com.roche.web.swagger;

import springfox.documentation.spring.web.plugins.Docket;

/**
 * @author Mateusz Filipowicz (mateusz.filipowicz@roche.com).
 */
public interface DocketConfigurer {
    void configure(Docket docket);
}
