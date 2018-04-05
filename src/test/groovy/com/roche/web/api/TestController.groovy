package com.roche.web.api

import com.roche.web.annotation.Api
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.springframework.web.bind.annotation.GetMapping



@Api("test")
@Slf4j
@CompileStatic
class TestController {

    @GetMapping("aaa")
    def doAction() {
        'test'
    }
}
