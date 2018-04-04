package com.roche.web.api;

import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

/**
 * Created by Mateusz Filipowicz (mateusz.filipowicz@roche.com).
 */
final class PathHolder implements VersionTarget {

    private String apiPrefix;
    private String version;
    private String context;
    private final List<String> subPaths = new ArrayList<>();

    private PathHolder() {
    }

    static PathHolder with() {
        return new PathHolder();
    }

    static PathHolder with(String path, String pathPrefix, String versionPrefix) {
        PathHolder holder = new PathHolder();
        String[] subPaths = path.split(ApiUtils.PATH_DELIMETER);
        for (String subPath : subPaths) {
            if (!StringUtils.isEmpty(subPath)) {
                holder.append(subPath);
            }
        }
        initializeApiPrefix(holder, pathPrefix);
        initializeVersion(holder, versionPrefix);
        initializeContext(holder);
        return holder;
    }

    private static void initializeContext(PathHolder holder) {
        //TODO not needed now, AIR-3043
    }

    private static void initializeApiPrefix(PathHolder holder, String pathPrefix) {
        if (!holder.subPaths.isEmpty() && holder.subPaths.get(0).equals(pathPrefix)) {
            holder.apiPrefix = pathPrefix;
        }
    }

    private static void initializeVersion(PathHolder holder, String versionPrefix) {
        String first = getAtOrEmpty(holder.getSubPaths(), 0);
        if (!initializeVersionIfAny(holder, first, versionPrefix)) {
            String second = getAtOrEmpty(holder.getSubPaths(), 1);
            initializeVersionIfAny(holder, second, versionPrefix);
        }
    }

    private static String getAtOrEmpty(List<String> list, int pos) {
        return list.size() > pos ? list.get(pos) : "";
    }

    private static boolean initializeVersionIfAny(PathHolder holder, String part, String versionPrefix) {
        if (ApiUtils.extractVersionFromPath(part, versionPrefix) > 0) {
            holder.version = part;
            return true;
        }
        return false;
    }

    private String cleanPart(String part) {
        return part.replaceAll(ApiUtils.PATH_DELIMETER, "");
    }

    private void append(int position, String part) {
        this.subPaths.add(position, cleanPart(part));
    }

    void append(String part) {
        this.subPaths.add(cleanPart(part));
    }

    public String getApiPrefix() {
        return apiPrefix;
    }

    public String getContext() {
        return context;
    }

    public List<String> getSubPaths() {
        return subPaths;
    }

    @Override
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        if (!isVersioned() && !StringUtils.isEmpty(version)) {
            this.version = version;
            if (!subPaths.contains(version)) {
                int position = isApi() ? 1 : 0;
                append(position, version);
            }
        }
    }

    public void setContext(String context) {
        if (!hasContext() && !StringUtils.isEmpty(context)) {
            this.context = context;
            if (!subPaths.subList(0, subPaths.size() - 1).contains(context)) {
                int position = 0;
                if (isApi()) {
                    position++;
                }
                if (isVersioned()) {
                    position++;
                }
                append(position, context);
            }
        }
    }

    public void setApi(String api) {
        if (!isApi() && !StringUtils.isEmpty(api)) {
            this.apiPrefix = api;
            if (!subPaths.contains(api)) {
                append(0, api);
            }
        }
    }

    private boolean hasContext() {
        return !StringUtils.isEmpty(context);
    }

    public boolean isApi() {
        return !StringUtils.isEmpty(apiPrefix);
    }

    String toPath() {
        StringJoiner joiner = new StringJoiner(ApiUtils.PATH_DELIMETER);
        subPaths.forEach(joiner::add);
        return joiner.toString();
    }

}
