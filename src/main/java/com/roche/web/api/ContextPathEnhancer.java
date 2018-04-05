package com.roche.web.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.StringUtils;

class ContextPathEnhancer implements ApiDecorator, InitializingBean {

    private static final Logger LOG = LoggerFactory.getLogger(ContextPathEnhancer.class);

    private final ApiProperties properties;

    ContextPathEnhancer(ApiProperties properties) {
        this.properties = properties;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
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
