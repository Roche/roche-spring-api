package com.roche.web.swagger;

/**
 * @author Mateusz Filipowicz (mateusz.filipowicz@roche.com).
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
