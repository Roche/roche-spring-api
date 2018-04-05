package com.roche.web.api;

/**
 * Provider used for handling API path mapping
 * when path is not provided in {@link com.roche.web.annotation.Api}.
 *
 * Implement this interface to enable custom handling
 * for path mappings with unspecified path in {@link com.roche.web.annotation.Api}.
 * By default path name is derived from name of class annotated with {@link io.swagger.annotations.Api}
 *
 * @see BasePathNamingProvider
 */
public interface EmptyPathNamingProvider {

    String getNameForHandlerType(Class handler);

}
