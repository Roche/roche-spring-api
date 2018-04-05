package com.roche.web.swagger;

import springfox.documentation.service.Parameter;

public interface AuthenticationParameterProvider {

    Parameter getAuthenticationParameter();

    class BasicAuthenticationParameterProvider implements AuthenticationParameterProvider {

        @Override
        public Parameter getAuthenticationParameter() {
            return CustomParameterBuilder.buildForHeader("Authorization");
        }
    }

    class NoAuthenticationParameterProvider implements AuthenticationParameterProvider {

        @Override
        public Parameter getAuthenticationParameter() {
            return null;
        }
    }
}
