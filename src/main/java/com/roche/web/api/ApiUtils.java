package com.roche.web.api;

import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;

import static com.roche.web.annotation.ApiVersion.UNVERSIONED;

final class ApiUtils {

    static final String PATH_DELIMETER = "/";

    private ApiUtils() {
    }

    static int extractVersionFromPath(String path, String versionPrefix) {
        int version = UNVERSIONED;
        if (StringUtils.isEmpty(path)) {
            return version;
        }
        String[] parts = path.split(PATH_DELIMETER);
        for (String pathPart : parts) {
            version = extractFromPrefix(pathPart, versionPrefix);
            if (version != UNVERSIONED) {
                break;
            }
        }
        return version;
    }

    private static int extractFromPrefix(String versionString, String versionPrefix) {
        if (versionString.startsWith(versionPrefix)) {
            String numericVersion = versionString.substring(1);
            StringBuilder builder = new StringBuilder();
            for (char c : numericVersion.toCharArray()) {
                if (c == '/') {
                    break;
                }
                builder.append(c);
            }
            String version = builder.toString();
            try {
                return Integer.parseInt(version);
            } catch (NumberFormatException e) {
                return UNVERSIONED;
            }
        }
        return UNVERSIONED;
    }

    static int extractVersionFromContentType(String contentType, String contentVnd, String versionPrefix) {
        if (StringUtils.isEmpty(contentType) || !contentType.contains(contentVnd)) {
            return UNVERSIONED;
        }
        String[] splitted = contentType.split("\\.");
        String versionPart = Arrays.stream(splitted)
                .filter(part -> part.startsWith(versionPrefix))
                .findFirst()
                .orElse(null);
        if (versionPart == null) {
            return UNVERSIONED;
        }
        StringBuilder builder = new StringBuilder();
        for (char c : versionPart.toCharArray()) {
            if (c == '+') {
                break;
            }
            builder.append(c);
        }
        return extractFromPrefix(builder.toString(), versionPrefix);
    }

    static void applyVersion(List<? extends VersionTarget> targets, String version) {
        targets.forEach(target -> target.setVersion(version));
    }
}
