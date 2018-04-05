package com.roche.web.api.jsend;

import com.roche.web.api.ApiException;

import java.util.Optional;

/**
 * This class provides static methods for easily obtaining {@link ApiResponse} objects.
 * It should be used for {@link ApiResponse} object creation.
 */
public class ApiResponseFactory {

    private static final String DEFAULT_ERROR_MESSAGE = "Sorry, no specific message for current error.";

    private ApiResponseFactory() {
    }

    public static ApiResponse getApiResponse(Object data) {
        if (data == null) {
            return ApiResponse.success(null);
        }
        if (ApiException.class.isAssignableFrom(data.getClass())) {
            return ApiResponse.fail(data, ((ApiException) data).getMessage());
        }
        if (Exception.class.isAssignableFrom(data.getClass())) {
            return ApiResponse.error(Optional
                    .ofNullable(((Exception) data).getMessage())
                    .orElse(DEFAULT_ERROR_MESSAGE));
        }
        return ApiResponse.success(data);
    }
}
