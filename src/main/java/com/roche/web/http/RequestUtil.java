package com.roche.web.http;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * Useful request-related utilities for operating on {@link HttpServletRequest} and related classes.
 */
public final class RequestUtil {

    public static Optional<RequestAttributes> getRequestAttributes() {
        return Optional.ofNullable(RequestContextHolder.getRequestAttributes());
    }

    public static HttpServletRequest getHttpServletRequest() {
        ServletRequestAttributes requestAttributes = getServletRequestAttributes();
        return requestAttributes != null ? requestAttributes.getRequest() : null;
    }

    private static ServletRequestAttributes getServletRequestAttributes() {
        try {
            return (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        } catch (IllegalStateException e) {
            return null;
        }
    }

    public static boolean isXhr(HttpServletRequest request) {
        if (request == null) {
            return false;
        }
        String xhrHeader = request.getHeader(HttpConst.Headers.X_REQUESTED_WITH);
        return HttpConst.XML_HTTP_REQUEST.equals(xhrHeader);
    }

    private RequestUtil() {

    }
}
