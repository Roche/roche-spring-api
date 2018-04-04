package com.roche.web.swagger;

import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.Parameter;

/**
 * @author Mateusz Filipowicz (mateusz.filipowicz@roche.com).
 */
public final class CustomParameterBuilder {

    public static Parameter build(String name, String type) {
        return new ParameterBuilder()
                .name(name)
                .modelRef(new ModelRef("string"))
                .parameterType(type)
                .build();
    }

    public static Parameter buildForHeader(String name) {
        return build(name, Target.HEADER);
    }

    public static Parameter buildForCookie(String name) {
        return build(name, Target.COOKIE);
    }

    public static Parameter build(String name, Target target) {
        return build(name, target.name().toLowerCase());
    }

    enum Target {
        HEADER,
        COOKIE
    }
}
