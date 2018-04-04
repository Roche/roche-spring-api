package com.roche.web.api.jsend;


public class ApiResponseException extends RuntimeException {

    private final ApiResponseStatus apiResponseStatus;
    private final Object data;

    public ApiResponseException(String message, ApiResponseStatus apiResponseStatus, Object data) {
        super(message);
        this.apiResponseStatus = apiResponseStatus;
        this.data = data;
    }

    public ApiResponseStatus getApiResponseStatus() {
        return apiResponseStatus;
    }

    public Object getData() {
        return data;
    }
}
