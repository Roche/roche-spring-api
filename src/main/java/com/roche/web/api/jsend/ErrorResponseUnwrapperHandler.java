package com.roche.web.api.jsend;

/**
 * If unwrapped message status is not SUCCESS, then {@link ErrorResponseUnwrapperHandler}
 * is used to handle received message, as message is considered to be erroneous.
 *
 * <p>To implement custom error handling, create Spring Bean implementing this interface
 * and register it within application context.</p>
 */
public interface ErrorResponseUnwrapperHandler {

    void handle(ApiResponseStatus status, String message, String data);
}
