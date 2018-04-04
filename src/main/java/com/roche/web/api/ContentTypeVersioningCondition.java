package com.roche.web.api;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.PropertyResolver;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * @author Mateusz Filipowicz (mateusz.filipowicz@roche.com).
 */
class ContentTypeVersioningCondition implements Condition {

    private static final String CONTENT_PROPERTY = "roche.api.versionInContentType";

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        PropertyResolver resolver = context.getEnvironment();
        return resolver.getProperty(CONTENT_PROPERTY, Boolean.class, false);
    }
}
