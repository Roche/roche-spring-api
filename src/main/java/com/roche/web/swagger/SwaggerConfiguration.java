package com.roche.web.swagger;

import com.fasterxml.classmate.TypeResolver;
import com.roche.web.utils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.util.StringUtils;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.schema.TypeNameExtractor;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.OperationBuilderPlugin;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class SwaggerConfiguration {

    private final List<DocketConfigurer> docketConfigurers;

    @Autowired(required = false)
    public SwaggerConfiguration(List<DocketConfigurer> docketConfigurers) {
        this.docketConfigurers = docketConfigurers;
    }

    @Autowired(required = false)
    public SwaggerConfiguration() {
        this(Collections.emptyList());
    }

    @Bean
    SwaggerProperties swaggerProperties() {
        return new SwaggerProperties();
    }

    @Bean
    OperationBuilderPlugin pageableParameterBuilder(TypeNameExtractor typeNameExtractor, TypeResolver typeResolver) {
        return new PageableParameterBuilder(typeNameExtractor, typeResolver);
    }

    @Bean
    @Primary
    VersionProvider versionProvider(ApplicationContext context) {
        Optional<VersionProvider> provider = BeanUtils.getBean(context, VersionProvider.class);
        return provider.orElseGet(VersionProvider.DefaultVersionProvider::new);
    }

    @Bean
    @Primary
    AuthenticationParameterProvider authenticationParameterProvider(ApplicationContext context) {
        Optional<AuthenticationParameterProvider> provider = BeanUtils.getBean(context, AuthenticationParameterProvider.class);
        return provider.orElseGet(AuthenticationParameterProvider.NoAuthenticationParameterProvider::new);
    }

    @Bean
    Docket docket(SwaggerProperties swaggerProperties, VersionProvider versionProvider, AuthenticationParameterProvider authenticationParameterProvider) {
        Parameter auth = authenticationParameterProvider.getAuthenticationParameter();
        Predicate<String> pathSelector = getPathSelector(swaggerProperties);
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .enable(swaggerProperties.isEnabled())
                .apiInfo(apiInfo(swaggerProperties, versionProvider))
                .select()
                .paths(pathSelector::test) //TODO currently only simple select configuration
                .build()
                .useDefaultResponseMessages(false);
        if (auth != null) {
            docket.globalOperationParameters(Collections.singletonList(auth));
        }
        docketConfigurers.forEach(configurer -> configurer.configure(docket));
        return docket;
    }

    private Predicate<String> getPathSelector(SwaggerProperties swaggerProperties) {
        Predicate<String> pathSelector = PathSelectors.any()::apply;
        if (!StringUtils.isEmpty(swaggerProperties.getAntPathSelector())) {
            pathSelector = PathSelectors.ant(swaggerProperties.getAntPathSelector())::apply;
        } else if (!StringUtils.isEmpty(swaggerProperties.getRegexPathSelector())) {
            pathSelector = PathSelectors.regex(swaggerProperties.getRegexPathSelector())::apply;
        }
        return pathSelector;
    }

    private ApiInfo apiInfo(SwaggerProperties swaggerProperties, VersionProvider versionProvider) {
        ApiInfoBuilder builder = new ApiInfoBuilder()
                .title(swaggerProperties.getTitle())
                .description(swaggerProperties.getDescription())
                .license(swaggerProperties.getLicense())
                .version(versionProvider.getVersion());
        if (!StringUtils.isEmpty(swaggerProperties.getContactName())) {
            builder.contact(new Contact(swaggerProperties.getContactName(), swaggerProperties.getContactUrl(), swaggerProperties.getContactEmail()));
        }
        return builder.build();
    }


}