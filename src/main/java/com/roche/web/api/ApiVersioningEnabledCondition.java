package com.roche.web.api;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.PropertyResolver;
import org.springframework.core.type.AnnotatedTypeMetadata;

class ApiVersioningEnabledCondition implements Condition {

    private static final String CONTENT_PROPERTY = "roche.api.enableVersioning";

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        PropertyResolver resolver = context.getEnvironment();
        return resolver.getProperty(CONTENT_PROPERTY, Boolean.class, true);
    }
}
