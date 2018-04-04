package com.roche.web.api;

import org.springframework.util.StringUtils;

/**
 * Created by Mateusz Filipowicz (mateusz.filipowicz@roche.com).
 */
interface VersionTarget {

    String getVersion();

    void setVersion(String version);

    default boolean isVersioned() {
        return !StringUtils.isEmpty(getVersion());
    }

}
