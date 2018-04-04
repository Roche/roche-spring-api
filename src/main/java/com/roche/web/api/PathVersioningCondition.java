package com.roche.web.api;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * @author Mateusz Filipowicz (mateusz.filipowicz@roche.com).
 */
class PathVersioningCondition implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        return !new ContentTypeVersioningCondition().matches(context, metadata);
    }
}
