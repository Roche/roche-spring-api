package com.roche.web.annotation;

import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Mateusz Filipowicz (mateusz.filipowicz@roche.com).
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Import(ApiConfigurationImportSelector.class)
public @interface EnableApi {

    String USE_JSEND = "useJSend";
    String ENABLE_SWAGGER = "enableSwagger";

    boolean useJSend() default true;

    boolean enableSwagger() default true;
}
