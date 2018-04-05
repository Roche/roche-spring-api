package com.roche.web.api;

import com.roche.web.annotation.ApiVersion;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;

import java.util.Set;

abstract class AbstractApiVersioningStrategy implements ApiVersionStrategy {

    private final ApiVersionNamingProvider namingProvider;
    protected final ApiProperties properties;

    AbstractApiVersioningStrategy(ApiVersionNamingProvider namingProvider, ApiProperties properties) {
        this.namingProvider = namingProvider;
        this.properties = properties;
    }

    @Override
    public void decorate(ApiBuilder builder) {
        ApiVersion apiVersion = builder.getApiVersion();
        if (apiVersion == null || apiVersion.value() == ApiVersion.UNVERSIONED) {
            return;
        }
        String version = namingProvider.nameForVersion(apiVersion.value());
        String convertedVersion = convertVersion(version);
        getVersionTarget(builder).setVersion(convertedVersion);
    }

    @Override
    public Set<Integer> parseVersion(RequestMappingInfo requestMappingInfo) {
        return null; //TODO
    }

    protected String convertVersion(String version) {
        return version;
    }

    protected abstract VersionTarget getVersionTarget(ApiBuilder builder);
}
