package com.roche.web.swagger;

import com.fasterxml.classmate.ResolvedType;
import com.fasterxml.classmate.TypeResolver;
import com.google.common.base.Function;
import org.springframework.beans.factory.annotation.Value;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.schema.ModelReference;
import springfox.documentation.schema.ResolvedTypes;
import springfox.documentation.schema.TypeNameExtractor;
import springfox.documentation.service.Parameter;
import springfox.documentation.service.ResolvedMethodParameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.schema.contexts.ModelContext;
import springfox.documentation.spi.service.OperationBuilderPlugin;
import springfox.documentation.spi.service.contexts.OperationContext;
import springfox.documentation.spi.service.contexts.ParameterContext;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static springfox.documentation.spi.schema.contexts.ModelContext.inputParam;
import static springfox.documentation.spring.web.plugins.Docket.DEFAULT_GROUP_NAME;

class PageableParameterBuilder implements OperationBuilderPlugin {

    private final TypeNameExtractor nameExtractor;
    private final TypeResolver resolver;

    @Value("${roche.swagger.pageableType:}")
    private Class pageableType;

    public PageableParameterBuilder(TypeNameExtractor nameExtractor, TypeResolver resolver) {
        this.nameExtractor = nameExtractor;
        this.resolver = resolver;
    }

    @Override
    public void apply(OperationContext context) {
        if (pageableType == null) {
            return;
        }
        List<ResolvedMethodParameter> methodParameters = context.getParameters();
        List<Parameter> parameters = newArrayList();

        for (ResolvedMethodParameter resolvedMethodParameter : methodParameters) {
            ResolvedType resolvedType = resolvedMethodParameter.getParameterType();

            if (resolvedType.getErasedType() == pageableType) {
                ParameterContext parameterContext = new ParameterContext(resolvedMethodParameter,
                        new ParameterBuilder(),
                        context.getDocumentationContext(),
                        context.getGenericsNamingStrategy(),
                        context);
                Function<ResolvedType, ? extends ModelReference> factory = createModelRefFactory(parameterContext);

                ModelReference intModel = factory.apply(resolver.resolve(Integer.TYPE));
                ModelReference stringModel = factory.apply(resolver.resolve(List.class, String.class));

                parameters.add(new ParameterBuilder()
                        .parameterType("query")
                        .name("page")
                        .modelRef(intModel)
                        .description("Results page you want to retrieve (0..N-1)").build());
                parameters.add(new ParameterBuilder()
                        .parameterType("query")
                        .name("size")
                        .modelRef(intModel)
                        .description("Number of records per page").build());
                parameters.add(new ParameterBuilder()
                        .parameterType("query")
                        .name("sort")
                        .modelRef(stringModel)
                        .allowMultiple(true)
                        .description("Sorting criteria in the format: 'property,ASC||DESC'. "
                                + "Default sort order is ascending. "
                                + "Multiple sort criteria are supported.")
                        .build());
                context.operationBuilder().parameters(parameters);
            }
        }
    }

    @Override
    public boolean supports(DocumentationType delimiter) {
        return true;
    }

    private Function<ResolvedType, ? extends ModelReference> createModelRefFactory(ParameterContext context) {
        ModelContext modelContext = inputParam(DEFAULT_GROUP_NAME, context.resolvedMethodParameter().getParameterType().getErasedType(),
                context.getDocumentationType(),
                context.getAlternateTypeProvider(),
                context.getGenericNamingStrategy(),
                context.getIgnorableParameterTypes());
        return ResolvedTypes.modelRefFactory(modelContext, nameExtractor);
    }
}