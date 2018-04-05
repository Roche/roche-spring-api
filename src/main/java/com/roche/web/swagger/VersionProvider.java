package com.roche.web.swagger;

/**
 * Swagger API version provider interface.
 * Implement it to customize Swagger version defined by Swagger
 */
public interface VersionProvider {

    String getVersion();

    class DefaultVersionProvider implements VersionProvider {

        @Override
        public String getVersion() {
            return "N/A";
        }
    }
}
