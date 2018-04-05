package com.roche.web.api;

class ApiPathEnhancer implements ApiDecorator {

    private final ApiProperties properties;

    ApiPathEnhancer(ApiProperties properties) {
        this.properties = properties;
    }

    @Override
    public void decorate(ApiBuilder builder) {
        builder.getPathHolders().applyApi(properties.getPathPrefix());
    }

    @Override
    public boolean supports(ApiBuilder builder) {
        return builder.getApi() != null;
    }
}
