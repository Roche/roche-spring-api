package com.roche.web.api.jsend;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

/**
 * If you communicate with other system or introduce JSend format for your microservices,
 * you can register this Jackson message converter which will take care of
 * JSend format unwrapping.
 *
 * <p>If unwrapped message status is not SUCCESS, then {@link ErrorResponseUnwrapperHandler}
 * is used to handle received message, as message is considered to be erroneous. If custom
 * error handler is not provided, basic handler will be used, which is just throwing
 * {@link ApiResponseException} with data provided</p>
 *
 * <p>Unwrapper is capable of handling pageable objects. Object is considered pageable
 * when <i>data</i> contains <i>content</i> and <i>totalPages</i> objects</p>
 */
public class ApiResponseUnwrapper extends MappingJackson2HttpMessageConverter {

    private static final Logger LOG = LoggerFactory.getLogger(ApiResponseUnwrapper.class);

    private static final String DATA = "data";
    private static final String STATUS = "status";
    private static final String MESSAGE = "message";

    private static final String CONTENT = "content";
    private static final String TOTAL_PAGES = "totalPages";
    private final ErrorResponseUnwrapperHandler errorResponseUnwrapperHandler;

    ApiResponseUnwrapper(ObjectMapper objectMapper) {
        this(objectMapper, new ExceptionThrowingResponseHandler());
    }

    ApiResponseUnwrapper(ObjectMapper objectMapper, ErrorResponseUnwrapperHandler errorResponseUnwrapperHandler) {
        super(objectMapper);
        this.errorResponseUnwrapperHandler = errorResponseUnwrapperHandler;
    }

    @Override
    public Object read(Type type, Class<?> contextClass, HttpInputMessage inputMessage) throws IOException {
        JavaType javaType = getJavaType(type, contextClass);
        return readJavaType(javaType, inputMessage);
    }

    private Object readJavaType(JavaType javaType, HttpInputMessage inputMessage) {
        try {

            String stringContent = IOUtils.toString(inputMessage.getBody(), StandardCharsets.UTF_8);
            JsonNode jsonContent = objectMapper.readTree(stringContent);
            JsonNode statusElement = jsonContent.get(STATUS);
            ApiResponseStatus responseStatus = objectMapper.readValue(statusElement.toString(), ApiResponseStatus.class);
            JsonNode dataElement = jsonContent.get(DATA);
            String dataString = Optional.ofNullable(dataElement)
                    .map(JsonNode::toString)
                    .orElse(null);

            // This usually shouldn't happen, because we set non-2xx HTTP response codes for applications errors,
            // so they will be caught by Hystrix error handling first
            if (responseStatus != ApiResponseStatus.SUCCESS) {
                LOG.debug("Unexpected response status '{}' with content: {}", responseStatus, stringContent);
                JsonNode messageElement = jsonContent.get(MESSAGE);
                errorResponseUnwrapperHandler.handle(responseStatus, messageElement.toString(), dataString);
            }

            if (isPageObject(dataElement)) {
                // TODO pagination imporovements AIR-4004
                return objectMapper.readValue(dataElement.get(CONTENT).toString(), javaType);
            }
            if (dataString == null) {
                return null;
            }
            return objectMapper.readValue(dataString, javaType);
        } catch (IOException ex) {
            throw new HttpMessageNotReadableException("Could not read document: " + ex.getMessage(), ex);
        }
    }

    private static boolean isPageObject(JsonNode dataElement) {
        if (dataElement != null && dataElement instanceof ObjectNode) {
            ObjectNode dataElementObject = (ObjectNode) dataElement;
            return dataElementObject.has(TOTAL_PAGES)
                    && dataElementObject.has(CONTENT);
        }
        return false;
    }

    private static class ExceptionThrowingResponseHandler implements ErrorResponseUnwrapperHandler {

        @Override
        public void handle(ApiResponseStatus status, String message, String data) {
            throw new ApiResponseException(message, status, data);
        }
    }
}