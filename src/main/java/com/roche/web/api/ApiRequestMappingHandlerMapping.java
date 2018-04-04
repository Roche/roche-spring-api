package com.roche.web.api;

import com.roche.web.annotation.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.condition.RequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by Mateusz Filipowicz (mateusz.filipowicz@roche.com).
 */
public class ApiRequestMappingHandlerMapping extends RequestMappingHandlerMapping {

    private static final Logger LOG = LoggerFactory.getLogger(ApiRequestMappingHandlerMapping.class);

    private final List<ApiDecorator> apiDecorators;
    private final ApiProperties apiProperties;
    private final EmptyPathNamingProvider pathNamingProvider;

    public ApiRequestMappingHandlerMapping(List<ApiDecorator> apiDecorators,
                                           ApiProperties apiProperties,
                                           EmptyPathNamingProvider pathNamingProvider) {
        this.apiDecorators = apiDecorators;
        this.apiProperties = apiProperties;
        this.pathNamingProvider = pathNamingProvider;
        setOrder(-1);
    }

    @Override
    protected boolean isHandler(Class<?> beanType) {
        Class<?> type = ClassUtils.getUserClass(beanType);
        return type.isAnnotationPresent(Api.class);
    }

    private RequestMappingInfo getMappingForMethodInternal(Method method, Class<?> handlerType) {
        RequestMappingInfo info = createRequestMappingInfo(method);
        if (info != null) {
            RequestMappingInfo typeInfo = createRequestMappingInfo(handlerType);
            if (typeInfo != null) {
                info = typeInfo.combine(info);
            }
        }
        return info;
    }

    private RequestMappingInfo createRequestMappingInfo(AnnotatedElement element) {
        Api api = AnnotatedElementUtils.findMergedAnnotation(element, Api.class);
        RequestMapping requestMapping;
        if (api == null) {
            requestMapping = AnnotatedElementUtils.findMergedAnnotation(element, RequestMapping.class);
        } else {
            requestMapping = ApiToRequestMappingMapper.map(api, pathNamingProvider, (Class) element);
        }

        RequestCondition<?> condition = (element instanceof Class ?
                getCustomTypeCondition((Class<?>) element) : getCustomMethodCondition((Method) element));
        return requestMapping != null ? createRequestMappingInfo(requestMapping, condition) : null;
    }

    @Override
    protected RequestMappingInfo getMappingForMethod(Method method, Class<?> handlerType) {
        RequestMappingInfo info = getMappingForMethodInternal(method, handlerType);
        if (info == null) {
            return null;
        }
        Api api = AnnotationUtils.findAnnotation(handlerType, Api.class);
        if (api != null) {
            final String current = info.toString();
            ApiBuilder builder = new ApiBuilder(api, method, handlerType, info, apiProperties);
            info = getMappingForApi(builder);
            LOG.info("Enhanced request mapping from {} to {}", current, info);
        }
        return info;
    }

    private RequestMappingInfo getMappingForApi(final ApiBuilder builder) {
        apiDecorators.stream()
                .filter(decorator -> decorator.supports(builder))
                .forEach(decorator -> decorator.decorate(builder));
        return builder.build();
    }
}
