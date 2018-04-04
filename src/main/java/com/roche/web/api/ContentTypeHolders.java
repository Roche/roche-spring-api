package com.roche.web.api;

import com.roche.web.utils.Tuple;
import org.springframework.web.servlet.mvc.condition.ConsumesRequestCondition;
import org.springframework.web.servlet.mvc.condition.ProducesRequestCondition;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by Mateusz Filipowicz (mateusz.filipowicz@roche.com).
 */
class ContentTypeHolders implements VersionTarget {

    private final List<ContentTypeHolder> consumes = new ArrayList<>();
    private final List<ContentTypeHolder> produces = new ArrayList<>();
    private String version;

    void applyVersion(String version) {
        ApiUtils.applyVersion(consumes, version);
        ApiUtils.applyVersion(produces, version);
    }

    Tuple<ConsumesRequestCondition, ProducesRequestCondition> toCondition() {
        String[] consumesArr = apply(this.consumes.stream());
        String[] producesArr = apply(this.produces.stream());
        ConsumesRequestCondition consumesCondition = new ConsumesRequestCondition(consumesArr);
        ProducesRequestCondition producesCondition = new ProducesRequestCondition(producesArr);
        return new Tuple<>(consumesCondition, producesCondition);
    }

    private String[] apply(Stream<ContentTypeHolder> stream) {
        return stream.map(ContentTypeHolder::toContentType)
                .toArray(String[]::new);
    }

    void addConsumes(String consumes, String contentVnd) {
        this.consumes.add(ContentTypeHolder.of(consumes, contentVnd));
    }

    void addProduces(String produces, String contentVnd) {
        this.produces.add(ContentTypeHolder.of(produces, contentVnd));
    }

    @Override
    public String getVersion() {
        return version;
    }

    @Override
    public void setVersion(String version) {
        applyVersion(version);
        this.version = version;
    }
}
