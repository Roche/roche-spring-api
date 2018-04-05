package com.roche.web.api.jsend;

/**
 * JSend standard response statuses
 */
enum ApiResponseStatus {
    /**
     * Request was processed successfully. Usually means responses with status 2xx or 3xx.
     */
    SUCCESS,
    /**
     * Request was processed with failure, but some issue with received data occurred, e.g. data
     * was unprocessable, incorrect media type was issued or data validation failed.
     * Usually means responses with status 4xx.
     */
    FAIL,
    /**
     * Request was not processed fully, error occurred while processing request. It should mean
     * error which is not handled by application and is unexpected. Usually means responses
     * with status 5xx.
     */
    ERROR
}
