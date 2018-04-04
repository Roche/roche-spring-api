package com.roche.web.api

/**
 * @author Mateusz Filipowicz (mateusz.filipowicz@roche.com).
 */
class ApiTestHelper {

    static final String BASE_CONTEXT = "context"
    static final String CONTENT_TYPE_VND = "vnd.rocheapi"
    static final String VERSION_PREFIX = "v"
    static final String PATH_PREFIX = "api"
    static final boolean VERSION_IN_CONTENT_TYPE = false

    static
    def getApiProperties(boolean includeContext = false, boolean versionInContentType = VERSION_IN_CONTENT_TYPE) {
        def props = new ApiProperties()
        props.with {
            it.baseContext = includeContext ? BASE_CONTEXT : null
            it.contentTypeVnd = CONTENT_TYPE_VND
            it.pathPrefix = PATH_PREFIX
            it.versionPrefix = VERSION_PREFIX
            it.versionInContentType = versionInContentType
        }
        return props
    }
}
