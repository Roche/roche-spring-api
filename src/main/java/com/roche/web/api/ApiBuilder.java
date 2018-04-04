package com.roche.web.api;

import com.roche.web.annotation.Api;
import com.roche.web.annotation.ApiVersion;
import com.roche.web.utils.Tuple;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.servlet.mvc.condition.*;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.function.Consumer;

/**
 * Created by Mateusz Filipowicz (mateusz.filipowicz@roche.com).
 */
public class ApiBuilder {

    private final Api api;
    private final Method method;
    private final Class<?> handlerType;

    private final RequestMappingInfo info;
    private final PathHolders pathHolders;
    private final ContentTypeHolders contentTypeHolders;
    private final ApiProperties apiProperties;

    private ApiVersion apiVersion;

    ApiBuilder(Api api, Method method, Class<?> handlerType, RequestMappingInfo info, ApiProperties apiProperties) {
        this.api = api;
        this.method = method;
        this.handlerType = handlerType;
        this.info = info;
        this.pathHolders = new PathHolders(apiProperties.getPathPrefix(), apiProperties.getVersionPrefix());
        this.contentTypeHolders = new ContentTypeHolders();
        this.apiProperties = apiProperties;
        initializeVersion();

        initializePathHolders();
        initializeContentTypeHolders();
    }

    private void initializeVersion() {
        ApiVersion version = AnnotationUtils.findAnnotation(method, ApiVersion.class);
        if (version == null) {
            version = AnnotationUtils.findAnnotation(handlerType, ApiVersion.class);
            if (version == null) {
                version = api.version();
            }
        }
        this.apiVersion = version;
    }

    private void initializePathHolders() {
        info.getPatternsCondition().getPatterns().forEach(pathHolders::add);
    }

    private void initializeContentTypeHolders() {
        initializeContentTypeHolderForExpressions(info.getConsumesCondition().getExpressions(), consumes -> contentTypeHolders.addConsumes(consumes, apiProperties.getContentTypeVnd()));
        initializeContentTypeHolderForExpressions(info.getProducesCondition().getExpressions(), produces -> contentTypeHolders.addProduces(produces, apiProperties.getContentTypeVnd()));
    }

    private void initializeContentTypeHolderForExpressions(Collection<MediaTypeExpression> expressions, Consumer<String> consumer) {
        expressions.stream()
                .map(MediaTypeExpression::toString)
                .forEach(consumer);
    }

    public RequestMappingInfo build() {
        PatternsRequestCondition patterns = pathHolders.toCondition();
        Tuple<ConsumesRequestCondition, ProducesRequestCondition> pair = contentTypeHolders.toCondition();
        RequestMappingInfoBuilder builder = new RequestMappingInfoBuilder(info);
        builder.addCondition(patterns);
        builder.addCondition(pair.getOne());
        builder.addCondition(pair.getTwo());
        return builder.build();
    }

    public Api getApi() {
        return this.api;
    }

    public Method getMethod() {
        return this.method;
    }

    public Class<?> getHandlerType() {
        return this.handlerType;
    }

    public RequestMappingInfo getInfo() {
        return this.info;
    }

    public PathHolders getPathHolders() {
        return this.pathHolders;
    }

    public ContentTypeHolders getContentTypeHolders() {
        return this.contentTypeHolders;
    }

    public ApiVersion getApiVersion() {
        return this.apiVersion;
    }

    public ConsumesRequestCondition getConsumesCondition() {
        return this.info.getConsumesCondition();
    }

    public RequestMappingInfo getMatchingCondition(HttpServletRequest request) {
        return this.info.getMatchingCondition(request);
    }

    public int compareTo(RequestMappingInfo other, HttpServletRequest request) {
        return this.info.compareTo(other, request);
    }

    public HeadersRequestCondition getHeadersCondition() {
        return this.info.getHeadersCondition();
    }

    public RequestMappingInfo combine(RequestMappingInfo other) {
        return this.info.combine(other);
    }

    public ProducesRequestCondition getProducesCondition() {
        return this.info.getProducesCondition();
    }

    public PatternsRequestCondition getPatternsCondition() {
        return this.info.getPatternsCondition();
    }

    public RequestMethodsRequestCondition getMethodsCondition() {
        return this.info.getMethodsCondition();
    }

    public ParamsRequestCondition getParamsCondition() {
        return this.info.getParamsCondition();
    }

    public RequestCondition<?> getCustomCondition() {
        return this.info.getCustomCondition();
    }

    public String getName() {
        return this.info.getName();
    }

    private static class RequestMappingInfoBuilder {
        private final RequestMappingInfo parentMappingInfo;
        private PatternsRequestCondition patterns;
        private RequestMethodsRequestCondition methods;
        private ParamsRequestCondition params;
        private HeadersRequestCondition headers;
        private ConsumesRequestCondition consumes;
        private ProducesRequestCondition produces;
        private RequestCondition custom;

        @java.beans.ConstructorProperties({"parentMappingInfo"})
        public RequestMappingInfoBuilder(RequestMappingInfo parentMappingInfo) {
            this.parentMappingInfo = parentMappingInfo;
        }

        RequestMappingInfoBuilder addCondition(RequestCondition condition) {
            if (condition instanceof PatternsRequestCondition) {
                this.patterns = (PatternsRequestCondition) condition;
            } else if (condition instanceof RequestMethodsRequestCondition) {
                this.methods = (RequestMethodsRequestCondition) condition;
            } else if (condition instanceof ParamsRequestCondition) {
                this.params = (ParamsRequestCondition) condition;
            } else if (condition instanceof HeadersRequestCondition) {
                this.headers = (HeadersRequestCondition) condition;
            } else if (condition instanceof ConsumesRequestCondition) {
                this.consumes = (ConsumesRequestCondition) condition;
            } else if (condition instanceof ProducesRequestCondition) {
                this.produces = (ProducesRequestCondition) condition;
            } else {
                this.custom = condition;
            }
            return this;
        }

        RequestMappingInfo build() {
            if (patterns == null) {
                patterns = parentMappingInfo.getPatternsCondition();
            }
            if (methods == null) {
                methods = parentMappingInfo.getMethodsCondition();
            }
            if (params == null) {
                params = parentMappingInfo.getParamsCondition();
            }
            if (headers == null) {
                headers = parentMappingInfo.getHeadersCondition();
            }
            if (consumes == null) {
                consumes = parentMappingInfo.getConsumesCondition();
            }
            if (produces == null) {
                produces = parentMappingInfo.getProducesCondition();
            }
            if (parentMappingInfo.getCustomCondition() != null) {
                custom = parentMappingInfo.getCustomCondition();
            }
            return new RequestMappingInfo(patterns, methods, params, headers, consumes, produces, custom);
        }
    }
}
