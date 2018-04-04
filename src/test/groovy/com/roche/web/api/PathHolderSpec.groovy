package com.roche.web.api

import spock.lang.Shared
import spock.lang.Specification

/**
 * Created by Mateusz Filipowicz (mateusz.filipowicz@roche.com).
 */
class PathHolderSpec extends Specification {

    static final API_PREFIX = 'api'
    static final API_VERSION = 'v7'

    @Shared
    ApiProperties properties = ApiTestHelper.apiProperties

    void 'path holder deals with path properly'() {
        when:
        def holder = PathHolder.with(path, properties.pathPrefix, properties.versionPrefix)

        then:
        holder.isApi() == isApi
        holder.apiPrefix == (isApi ? API_PREFIX : null)
        holder.version == version
        holder.context == context
        holder.toPath() == path

        when:
        holder.setApi(API_PREFIX)

        then:
        holder.isApi()
        holder.apiPrefix == API_PREFIX
        holder.version == version
        holder.context == context
        holder.toPath() == getPath(true, version, context, path)

        when:
        holder.setContext('testContext')

        then:
        holder.isApi()
        holder.apiPrefix == API_PREFIX
        holder.version == version
        holder.context == 'testContext'
        holder.toPath() == getPath(true, version, 'testContext', path)

        when:
        holder.setVersion('v99')

        then:
        holder.isApi()
        holder.apiPrefix == API_PREFIX
        holder.version == version ?: 'v99'
        holder.context == 'testContext'
        holder.toPath() == getPath(true, version ?: 'v99', 'testContext', path)

        where:
        path                   || isApi || version     || context //TODO AIR-3043
        'resource/test'        || false || null        || null
        'api/resource/test'    || true  || null        || null
        'v7/resource/test'     || false || API_VERSION || null
        'api/v7/resource/test' || true  || API_VERSION || null
    }

    private String getPath(boolean isApi, String version, String context, String path) {
        def sb = new StringBuilder(path)
        if (isApi && !path.startsWith(API_PREFIX)) {
            sb.insert(0, API_PREFIX + '/')
        }
        if (version && !path.contains(version + '/')) {
            int position = 0
            if (isApi) {
                position += API_PREFIX.length() + 1
            }
            sb.insert(position, version + '/')
        }

        if (context && !path.contains(context + '/')) {
            int position = 0
            if (isApi) {
                position += API_PREFIX.length() + 1
            }
            if (version) {
                position += version.length() + 1
            }
            sb.insert(position, context + '/')
        }
        sb.toString()
    }
}