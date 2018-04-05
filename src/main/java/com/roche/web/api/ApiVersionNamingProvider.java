package com.roche.web.api;

class ApiVersionNamingProvider {

    static final String LATEST_VERSION = "latest"; //TODO add support for handling latest

    private final ApiProperties properties;

    ApiVersionNamingProvider(ApiProperties properties) {
        this.properties = properties;
    }

    String nameForVersion(int version) {
        return version < 1 ? "" : properties.getVersionPrefix() + version;
    }

    String nameForVersion(int version, boolean isLatest) {
        throw new UnsupportedOperationException("To be implemented");
//        return isLatest ? LATEST_VERSION : nameForVersion(version);
    }
}
