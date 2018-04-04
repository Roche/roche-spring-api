package com.roche.web.annotation;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.annotation.*;

/**
 * This class was designed to annotate controllers. Swagger ui automatically adds a documentation for api provided  by
 * annotated controller. It includes @RestController and @RequestMapping with empty value, but can be defined with
 * 'value' attribute.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
@ResponseBody
@io.swagger.annotations.Api
public @interface Api {

    String[] value() default {};

    String[] consumes() default {};

    String[] produces() default {};

    String baseContext() default "";

    ApiVersion version() default @ApiVersion;
}
