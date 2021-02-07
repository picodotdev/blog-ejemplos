package io.github.picodotdev.blogbitix.dddhexagonal.catalog.infrastructure.rest.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import io.github.picodotdev.blogbitix.dddhexagonal.catalog.domain.model.event.EventId;

@Component
public class EventIdConverter implements Converter<String, EventId> {

    @Override
    public EventId convert(String source) {
        try {
            return EventId.valueOf(source);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }
}
