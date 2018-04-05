package com.roche.web.swagger;

import springfox.documentation.spring.web.plugins.Docket;

/**
 * Implement this interface
 * and create Spring Beans to configure Swagger.
 */
public interface DocketConfigurer {
    void configure(Docket docket);
}
