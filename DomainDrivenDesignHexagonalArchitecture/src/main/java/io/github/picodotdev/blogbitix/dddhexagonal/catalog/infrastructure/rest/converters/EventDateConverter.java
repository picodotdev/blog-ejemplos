package io.github.picodotdev.blogbitix.dddhexagonal.catalog.infrastructure.rest.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import io.github.picodotdev.blogbitix.dddhexagonal.catalog.domain.model.event.EventDate;

@Component
public class EventDateConverter implements Converter<String, EventDate> {

    @Override
    public EventDate convert(String source) {
        try {
            return EventDate.valueOf(source);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }
}
