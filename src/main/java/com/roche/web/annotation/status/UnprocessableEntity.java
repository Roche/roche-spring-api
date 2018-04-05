package com.roche.web.annotation.status;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Mark your controller type or method with to return {@link HttpStatus#UNPROCESSABLE_ENTITY}.
 * It is shorthand for {@code @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)}
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public @interface UnprocessableEntity {
}
