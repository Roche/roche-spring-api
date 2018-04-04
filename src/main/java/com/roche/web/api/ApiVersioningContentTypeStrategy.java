package com.roche.web.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.MimeType;
import org.springframework.web.servlet.mvc.condition.MediaTypeExpression;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;

import javax.annotation.PostConstruct;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Mateusz Filipowicz (mateusz.filipowicz@roche.com).
 */
class ApiVersioningContentTypeStrategy extends AbstractApiVersioningStrategy {

    private static final Logger LOG = LoggerFactory.getLogger(ApiVersioningContentTypeStrategy.class);

    ApiVersioningContentTypeStrategy(ApiVersionNamingProvider namingProvider, ApiProperties properties) {
        super(namingProvider, properties);
    }

    @PostConstruct
    void init() {
        LOG.info("Enabled API versioning in content-type (e.g. application/{}.{}1+json", properties.getContentTypeVnd(), properties.getVersionPrefix());
    }

    @Override
    public Set<Integer> parseVersion(RequestMappingInfo requestMappingInfo) {
        return requestMappingInfo.getConsumesCondition()
                .getExpressions()
                .stream()
                .map(MediaTypeExpression::getMediaType)
                .map(MimeType::getType)
                .map(media -> ApiUtils.extractVersionFromContentType(media, properties.getContentTypeVnd(), properties.getVersionPrefix()))
                .collect(Collectors.toSet());
    }

    @Override
    protected String convertVersion(String version) {
        String format = properties.getContentTypeVnd() + ".%s";
        return String.format(format, version);
    }

    @Override
    protected VersionTarget getVersionTarget(ApiBuilder builder) {
        return builder.getContentTypeHolders();
    }

}
