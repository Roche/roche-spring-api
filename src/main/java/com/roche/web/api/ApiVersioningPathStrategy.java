package com.roche.web.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Component adding versioning on path, i.e. /api/v1..., /v1/...
 * If path already is versioned with this versioning schema (v1, v2, v99, ..)
 * then new pattern will added (current won't be replaced and will still exist)
 * Created by Mateusz Filipowicz (mateusz.filipowicz@roche.com).
 */
class ApiVersioningPathStrategy extends AbstractApiVersioningStrategy implements InitializingBean {

    private static final Logger LOG = LoggerFactory.getLogger(ApiVersioningPathStrategy.class);

    ApiVersioningPathStrategy(ApiVersionNamingProvider namingProvider, ApiProperties properties) {
        super(namingProvider, properties);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        LOG.info("Enabled API versioning in request path (e.g. /{}/{}1/resource", properties.getPathPrefix(), properties.getVersionPrefix());
    }

    @Override
    public Set<Integer> parseVersion(RequestMappingInfo requestMappingInfo) {
        return requestMappingInfo.getPatternsCondition()
                .getPatterns()
                .stream()
                .map(path -> ApiUtils.extractVersionFromPath(path, properties.getVersionPrefix()))
                .collect(Collectors.toSet());
    }

    @Override
    protected VersionTarget getVersionTarget(ApiBuilder builder) {
        return builder.getPathHolders();
    }

}
