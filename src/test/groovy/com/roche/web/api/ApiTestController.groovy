package com.roche.web.api

import com.roche.web.annotation.Api
import com.roche.web.annotation.ApiVersion


@Api
@ApiVersion(1)
class ApiTestController {

    def method1() {}
}