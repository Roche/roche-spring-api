package com.roche.web.swagger;

import springfox.documentation.service.Parameter;

/**
 * @author Mateusz Filipowicz (mateusz.filipowicz@roche.com).
 */
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
