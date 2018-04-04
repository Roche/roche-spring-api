package com.roche.web.api;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author Mateusz Filipowicz (mateusz.filipowicz@roche.com).
 */
//@Component
public class ApiProperties {

    @Value("${roche.api.baseContext:}")
    private String baseContext;
    @Value("${roche.api.contentTypeVnd:}")
    private String contentTypeVnd;
    @Value("${roche.api.versionPrefix:v}")
    private String versionPrefix;
    @Value("${roche.api.pathPrefix:api}")
    private String pathPrefix;
    @Value("${roche.api.versionInContentType:false}")
    private boolean versionInContentType;
    @Value("${roche.api.wrapResponseEntity:false}")
    private boolean wrapResponseEntity;
    @Value("${roche.api.enableVersioning:true}")
    private boolean enableVersioning;

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
