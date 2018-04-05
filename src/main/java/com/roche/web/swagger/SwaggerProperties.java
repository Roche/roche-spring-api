package com.roche.web.swagger;

import org.springframework.beans.factory.annotation.Value;

class SwaggerProperties {

    static final String PREFIX = "roche.swagger.";

    @Value("${" + PREFIX + "contactName:}")
    private String contactName;
    @Value("${" + PREFIX + "contactEmail:}")
    private String contactEmail;
    @Value("${" + PREFIX + "contactUrl:}")
    private String contactUrl;
    @Value("${" + PREFIX + "title:}")
    private String title;
    @Value("${" + PREFIX + "description:}")
    private String description;
    @Value("${" + PREFIX + "license:}")
    private String license;
    @Value("${" + PREFIX + "antPathSelector:}")
    private String antPathSelector;
    @Value("${" + PREFIX + "regexPathSelector:}")
    private String regexPathSelector;
    @Value("${" + PREFIX + "enabled:true}")
    private boolean enabled;


    public String getContactName() {
        return contactName;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public String getContactUrl() {
        return contactUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getLicense() {
        return license;
    }

    public String getAntPathSelector() {
        return antPathSelector;
    }

    public String getRegexPathSelector() {
        return regexPathSelector;
    }

    boolean isEnabled() {
        return enabled;
    }
}
