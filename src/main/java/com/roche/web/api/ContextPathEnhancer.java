package com.roche.web.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;

/**
 * Created by Mateusz Filipowicz (mateusz.filipowicz@roche.com).
 */
class ContextPathEnhancer implements ApiDecorator {

    private static final Logger LOG = LoggerFactory.getLogger(ContextPathEnhancer.class);

    private final ApiProperties properties;

    ContextPathEnhancer(ApiProperties properties) {
        this.properties = properties;
    }

    @PostConstruct
    void init() {
        LOG.info("Base context set to: {}", properties.getBaseContext());
    }

    @Override
    public void decorate(ApiBuilder builder) {
        String apiContext = builder.getApi().baseContext();
        String context = StringUtils.isEmpty(apiContext) ? properties.getBaseContext() : apiContext;
        if (!StringUtils.isEmpty(context)) {
            builder.getPathHolders().applyContext(context);
        }
    }

    @Override
    public boolean supports(ApiBuilder builder) {
        return builder.getApi() != null;
    }
}
