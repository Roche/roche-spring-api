package com.roche.web.annotation;

import com.roche.web.api.ApiConfiguration;
import com.roche.web.api.jsend.JSendApiFormatConfiguration;
import com.roche.web.swagger.SwaggerConfiguration;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

import java.util.ArrayList;
import java.util.List;

/**
 * Selector choosing configuration classes to be imported.
 * It should utilize {@link EnableApi} parameters.
 *
 */

class ApiConfigurationImportSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {

        AnnotationAttributes attributes = AnnotationAttributes.fromMap(
                annotationMetadata.getAnnotationAttributes(EnableApi.class.getName(), false));
        List<String> imports = new ArrayList<>();
        add(ApiConfiguration.class, imports);
        if (attributes.getBoolean(EnableApi.USE_JSEND)) {
            add(JSendApiFormatConfiguration.class, imports);
        }
        if (attributes.getBoolean(EnableApi.ENABLE_SWAGGER)) {
            add(SwaggerConfiguration.class, imports);
        }

        return imports.toArray(new String[imports.size()]);
    }

    private void add(Class type, List<String> imports) {
        imports.add(type.getName());
    }
}
