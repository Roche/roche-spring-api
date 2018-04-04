package com.roche.web.api

import com.roche.web.annotation.Api
import com.roche.web.annotation.ApiVersion

/**
 * Created by Mateusz Filipowicz (mateusz.filipowicz@roche.com).
 */
@Api
@ApiVersion(1)
class ApiTestController {

    def method1() {}
}