package com.roche.web.api.jsend;

/**
 * @author Mateusz Filipowicz (mateusz.filipowicz@roche.com).
 */
public interface ErrorResponseUnwrapperHandler {

    void handle(ApiResponseStatus status, String message, String data);
}
