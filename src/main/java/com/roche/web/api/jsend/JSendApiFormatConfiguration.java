package com.roche.web.api.jsend;

import com.roche.web.api.ApiProperties;
import org.springframework.context.annotation.Bean;

/**
 * @author Mateusz Filipowicz (mateusz.filipowicz@roche.com).
 */
public class JSendApiFormatConfiguration {

    @Bean
    ApiResponseWrapper wrapper(ApiProperties apiProperties) {
        return new ApiResponseWrapper(apiProperties);
    }
}
