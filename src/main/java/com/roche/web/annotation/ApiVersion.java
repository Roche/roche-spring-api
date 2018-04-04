package com.roche.web.annotation;

import java.lang.annotation.*;

/**
 * Created by Mateusz Filipowicz (mateusz.filipowicz@roche.com).
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ApiVersion {

    int UNVERSIONED = -1;

    int value() default 1;

}
