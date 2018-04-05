package com.roche.web.api.jsend;

import com.roche.web.api.ApiProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.List;

/**
 * Class intended to wrap into {@link ApiResponse} objects returned from controller.
 * If some types (like String) need unique way of wrapping new ResponseBodyAdvice may be added.
 */
@ControllerAdvice
@Order
class ApiResponseWrapper implements ResponseBodyAdvice<Object> {

    private static final Logger LOG = LoggerFactory.getLogger(ApiResponseWrapper.class);
    private static final List<Class> EXCLUDED_RETURNED_TYPE = Arrays.asList(String.class, HttpEntity.class, ApiResponse.class);

    private final ApiProperties properties;

    ApiResponseWrapper(ApiProperties properties) {
        this.properties = properties;
    }

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        if (isTypeExcluded(getClass(returnType))) {
            LOG.warn("Maybe specific wrapper/handling should be added for returned type: {}", returnType
                    .getParameterType().getSimpleName());
            return false;
        }
        return shouldPathBeHandled();
    }

    private ResponseEntity<ApiResponse> wrapResponseEntityBody(ResponseEntity responseEntity) {
        return ResponseEntity.status(responseEntity.getStatusCode())
                .headers(responseEntity.getHeaders())
                .body(ApiResponseFactory.getApiResponse(responseEntity.getBody()));
    }

    @Override
    public Object beforeBodyWrite(Object body,
                                  MethodParameter returnType,
                                  MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request, ServerHttpResponse response) {
        Object toWrite = body;
        if (body instanceof ResponseEntity) {
            return wrapResponseEntityBody((ResponseEntity) body);
        }
        return ApiResponseFactory.getApiResponse(toWrite);
    }

    private boolean isTypeExcluded(Class<?> returnedType) {
        boolean handleResponseEntity = properties.isWrapResponseEntity() && ResponseEntity.class.isAssignableFrom(returnedType);
        return !handleResponseEntity && EXCLUDED_RETURNED_TYPE.stream().anyMatch(aClass -> aClass.isAssignableFrom(returnedType));
    }

    private Class<?> getClass(MethodParameter returnType) {
        if (returnType.getGenericParameterType() instanceof ParameterizedType) {
            return (Class) ((ParameterizedType) (returnType.getGenericParameterType())).getRawType();
        }
        return returnType.getParameterType();
    }

    private boolean shouldPathBeHandled() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        String uri = request.getRequestURI();
        String test = properties.getPathPrefix() + "/";
        return uri.contains(test);
    }
}
