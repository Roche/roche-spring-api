package com.roche.web.api;

import com.roche.web.utils.BeanUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.List;
import java.util.Optional;

/**
 * @author Mateusz Filipowicz (mateusz.filipowicz@roche.com).
 */
public class ApiConfiguration {

    @Bean
    ApiProperties apiProperties() {
        return new ApiProperties();
    }

    @Bean
    ApiPathEnhancer apiPathEnhancer(ApiProperties apiProperties) {
        return new ApiPathEnhancer(apiProperties);
    }

    @Bean
    @Primary
    EmptyPathNamingProvider emptyPathNamingProvider(ApplicationContext context) {
        Optional<EmptyPathNamingProvider> provider = BeanUtils.getBean(context, EmptyPathNamingProvider.class);
        return provider.orElseGet(BasePathNamingProvider::new);
    }

    @Bean
    ContextPathEnhancer contextPathEnhancer(ApiProperties apiProperties) {
        return new ContextPathEnhancer(apiProperties);
    }

    @Bean
    ApiRequestMappingHandlerMapping apiRequestMappingHandlerMapping(ApiProperties apiProperties, List<ApiDecorator> apiDecorators, EmptyPathNamingProvider pathNamingProvider) {
        return new ApiRequestMappingHandlerMapping(apiDecorators, apiProperties, pathNamingProvider);
    }

    @Configuration
    @Conditional(ApiVersioningEnabledCondition.class)
    static class ApiVersioningConfiguration {
        @Bean
        ApiVersionNamingProvider apiVersionNamingProvider(ApiProperties apiProperties) {
            return new ApiVersionNamingProvider(apiProperties);
        }

        @Bean
        @Conditional(PathVersioningCondition.class)
        ApiVersioningPathStrategy apiVersioningPathStrategy(ApiVersionNamingProvider namingProvider, ApiProperties apiProperties) {
            return new ApiVersioningPathStrategy(namingProvider, apiProperties);
        }

        @Bean
        @Conditional(ContentTypeVersioningCondition.class)
        ApiVersioningContentTypeStrategy apiVersioningContentTypeStrategy(ApiVersionNamingProvider namingProvider, ApiProperties apiProperties) {
            return new ApiVersioningContentTypeStrategy(namingProvider, apiProperties);
        }
    }
}
