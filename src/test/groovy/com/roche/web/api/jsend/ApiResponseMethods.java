package com.roche.web.api.jsend;


import org.springframework.http.HttpEntity;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

interface ApiResponseMethods {
    static ApiResponseMethods unsupported() {
        return new UnsupportedResponseMethods();
    }

    static ApiResponseMethods supported() {
        return new SupportedResponseMethods();
    }

    default Collection<Method> getTestMethods() {
        return Arrays.stream(this.getClass().getDeclaredMethods())
                .filter(method -> method.getName().startsWith("test"))
                .collect(Collectors.toList());
    }
}

class UnsupportedResponseMethods implements ApiResponseMethods {
    String test1() {
        return "";
    }

    HttpEntity test2() {
        return new HttpEntity("");
    }

    ApiResponse test3() {
        return ApiResponseFactory.getApiResponse(null);
    }
}

class SupportedResponseMethods implements ApiResponseMethods {
    Object test1() {
        return new Object();
    }
}
