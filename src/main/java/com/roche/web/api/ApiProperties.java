package com.roche.web.api;

import org.springframework.beans.factory.annotation.Value;

/**
 *
 */
public class ApiProperties {

    /**
     * API path base context
     * Default: empty string
     */
    @Value("${roche.api.baseContext:}")
    private String baseContext;
    /**
     * Content-type vnd, e.g. if {@code contentTypeVnd='api'}, then content-type may be (depending on version)
     * {@code application/vnd.api.v2}
     * Default: empty string
     */
    @Value("${roche.api.contentTypeVnd:}")
    private String contentTypeVnd;
    /**
     * API version prefix
     * Default: <i>v</i>
     */
    @Value("${roche.api.versionPrefix:v}")
    private String versionPrefix;
    /**
     * API path prefix, e.g. api, rest
     * Default: <i>api</i>
     */
    @Value("${roche.api.pathPrefix:api}")
    private String pathPrefix;
    /**
     * Flag if versioning should be in content-type, otherwise in path
     * Default: <i>false</i> (path versioning)
     */
    @Value("${roche.api.versionInContentType:false}")
    private boolean versionInContentType;
    /**
     * Flag if API versioning is enabled
     * Default: <i>true</i> (versioning enabled)
     */
    @Value("${roche.api.enableVersioning:true}")
    private boolean enableVersioning;
    /**
     * Flag if {@link org.springframework.http.ResponseEntity} objects
     * should then be wrapped by {@link com.roche.web.api.jsend.ApiResponseWrapper}.
     * If true, {@link org.springframework.http.ResponseEntity} objects will be
     * wrapped into JSend format, otherwise it will be returned as defined.
     */
    @Value("${roche.api.wrapResponseEntity:false}")
    private boolean wrapResponseEntity;

    public String getBaseContext() {
        return baseContext;
    }

    public String getContentTypeVnd() {
        return contentTypeVnd;
    }

    public String getVersionPrefix() {
        return versionPrefix;
    }

    public String getPathPrefix() {
        return pathPrefix;
    }

    public boolean isVersionInContentType() {
        return versionInContentType;
    }

    public boolean isWrapResponseEntity() {
        return wrapResponseEntity;
    }

    boolean isEnableVersioning() {
        return enableVersioning;
    }

    void setEnableVersioning(boolean enableVersioning) {
        this.enableVersioning = enableVersioning;
    }

    public void setBaseContext(String baseContext) {
        this.baseContext = baseContext;
    }

    public void setContentTypeVnd(String contentTypeVnd) {
        this.contentTypeVnd = contentTypeVnd;
    }

    public void setVersionPrefix(String versionPrefix) {
        this.versionPrefix = versionPrefix;
    }

    public void setPathPrefix(String pathPrefix) {
        this.pathPrefix = pathPrefix;
    }

    public void setVersionInContentType(boolean versionInContentType) {
        this.versionInContentType = versionInContentType;
    }

    public void setWrapResponseEntity(boolean wrapResponseEntity) {
        this.wrapResponseEntity = wrapResponseEntity;
    }
}
