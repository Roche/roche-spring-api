package com.roche.web.annotation;

import java.lang.annotation.*;

/**
 * The version of API, applied to path mapping or content-type based on chosen versioning strategy.
 * Method-level version definition override type-level definition.
 * @see com.roche.web.api.ContentTypeVersioningCondition
 * @see com.roche.web.api.PathVersioningCondition
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ApiVersion {

    int UNVERSIONED = -1;

    /**
     * Version value
     */
    int value() default 1;

}
