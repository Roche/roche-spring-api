package com.roche.web.api

import com.roche.web.annotation.EnableApi
import groovy.transform.CompileStatic
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import springfox.documentation.swagger2.annotations.EnableSwagger2

/**
 * @author Mateusz Filipowicz (mateusz.filipowicz@roche.com).
 */
@Configuration
@EnableWebMvc
@CompileStatic
@EnableApi(useJSend = true)
@EnableSwagger2
class TestConfiguration {
}
