package com.roche.web.annotation;

import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotate your Spring configuration class with this annotation
 * to enable spring-api library and API handling.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Import(ApiConfigurationImportSelector.class)
public @interface EnableApi {

    String USE_JSEND = "useJSend";
    String ENABLE_SWAGGER = "enableSwagger";

    /**
     * Define as true if you want to use JSend response formatting.
     *
     * @see <a href="https://labs.omniti.com/labs/jsend">https://labs.omniti.com/labs/jsend</a>
     * @see com.roche.web.api.jsend.ApiResponseWrapper
     * @see com.roche.web.api.jsend.ApiResponseUnwrapper
     * @see com.roche.web.api.jsend.ApiResponse
     */
    boolean useJSend() default true;

    /**
     * Define as true if you want to initialize Swagger configuration.
     *
     * @see com.roche.web.swagger.SwaggerConfiguration
     * @see com.roche.web.swagger.SwaggerProperties
     */
    boolean enableSwagger() default true;
}
