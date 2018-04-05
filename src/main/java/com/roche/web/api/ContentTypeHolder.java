package com.roche.web.api;

import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;

import java.util.Map;

final class ContentTypeHolder implements VersionTarget {

    private MediaType contentType;
    private String version;
    private final String contentVnd;

    ContentTypeHolder(MediaType contentType, String version, String contentVnd) {
        this.contentType = contentType;
        this.version = version;
        this.contentVnd = contentVnd;
    }

    String toContentType() {
        return contentType.toString();
    }

    public static ContentTypeHolder of(String contentType, String contentVnd) {
        return new ContentTypeHolder(MediaType.parseMediaType(contentType), null, contentVnd);
    }

    public static ContentTypeHolder of(MediaType contentType, String contentVnd) {
        return new ContentTypeHolder(contentType, null, contentVnd);
    }

    @Override
    public String getVersion() {
        return version;
    }

    @Override
    public void setVersion(String version) {
        if (!isVersioned() && !StringUtils.isEmpty(version)) {
            this.version = version;
            if (!contentType.getSubtype().contains(version)) {
                String newContentType = applyVersion(version);
                this.contentType = MediaType.parseMediaType(newContentType);
            }
        }
    }

    private String applyVersion(String version) {
        if (contentType.isConcrete() && !contentType.getSubtype().startsWith(contentVnd)) {
            StringBuilder builder = new StringBuilder();
            builder.append(contentType.getType());
            builder.append("/");
            builder.append(version);
            builder.append("+");
            builder.append(contentType.getSubtype());

            for (Map.Entry<String, String> entry : contentType.getParameters().entrySet()) {
                builder.append(";");
                builder.append(entry.getKey());
                builder.append("=");
                builder.append(entry.getValue());
            }
            return builder.toString();
        }
        return contentType.toString();
    }
}
