package com.roche.web.api.jsend;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Custom DTO designed to send data from rest controllers according to JSend specification <br>
 * There is factory designed for  three predefined static methods to obtain ApiResponse object.
 * <p>
 * {@literal @}JsonInclude annotation specifies that {@link ApiResponse} object null fields should not be present when
 * serialized with json mapper.
 * <p>
 * For easy receiving {@link ApiResponse} objects consider using {@link ApiResponseFactory} static methods.
 *
 * @see <a href="https://labs.omniti.com/labs/jsend">JSend</a>
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse {

    private final ApiResponseStatus status;
    private final String message;
    private final Object data;

    private ApiResponse(ApiResponseStatus status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public ApiResponseStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public Object getData() {
        return data;
    }

    public static ApiResponse success(Object data) {
        return new ApiResponse(ApiResponseStatus.SUCCESS, null, data);
    }

    public static ApiResponse fail(Object data, String message) {
        return new ApiResponse(ApiResponseStatus.FAIL, message, data);
    }

    public static ApiResponse error(String message) {
        return new ApiResponse(ApiResponseStatus.ERROR, message, null);
    }
}