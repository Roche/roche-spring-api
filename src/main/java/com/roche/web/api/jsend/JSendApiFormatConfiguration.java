package com.roche.web.api.jsend;

import com.roche.web.api.ApiProperties;
import org.springframework.context.annotation.Bean;

/**
 * JSend related configuration. It is imported as Spring configuration
 * when {@code @EnableApi(useJSend = true)} is set.
 *
 * @see com.roche.web.annotation.EnableApi
 * @see com.roche.web.annotation.ApiConfigurationImportSelector
 */
public class JSendApiFormatConfiguration {

    @Bean
    ApiResponseWrapper wrapper(ApiProperties apiProperties) {
        return new ApiResponseWrapper(apiProperties);
    }
}
