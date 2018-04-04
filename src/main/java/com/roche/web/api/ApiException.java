package com.roche.web.api;

/**
 * @author Mateusz Filipowicz (mateusz.filipowicz@roche.com).
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
