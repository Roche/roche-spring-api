package com.roche.web.api;

/**
 * Core exception used to render {@link com.roche.web.api.jsend.ApiResponseStatus#FAIL}.
 * If this exception is thrown anywhere in application code and not handled and JSend format is used,
 * it will be then rendered in response with status {@link com.roche.web.api.jsend.ApiResponseStatus#FAIL}.
 *
 * Make your exception handlers throw (or return if using {@link org.springframework.web.bind.annotation.ControllerAdvice} approach)
 * this exception by wrapping exception cause. It will then be gently handled by {@link com.roche.web.api.jsend.ApiResponseWrapper}.
 *
 * This exception may be especially thrown when data validation fails or any of HTTP 4xx statuses may occur.
 *
 * @see com.roche.web.api.jsend.ApiResponseStatus#FAIL
 */
public class ApiException extends RuntimeException {

    ApiException() {
    }

    ApiException(String message) {
        super(message);
    }

    ApiException(String message, Throwable cause) {
        super(message, cause);
    }

    ApiException(Throwable cause) {
        super(cause);
    }

    ApiException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
