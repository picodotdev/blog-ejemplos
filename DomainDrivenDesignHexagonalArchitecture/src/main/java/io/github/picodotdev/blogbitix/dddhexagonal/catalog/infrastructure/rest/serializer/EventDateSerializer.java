package io.github.picodotdev.blogbitix.dddhexagonal.catalog.infrastructure.rest.serializer;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;

import io.github.picodotdev.blogbitix.dddhexagonal.catalog.domain.model.event.EventDate;

@JsonComponent
public class EventDateSerializer extends JsonSerializer<EventDate> {

    @Override
    public void serialize(EventDate value, JsonGenerator generator, SerializerProvider serializers) throws IOException {
        generator.writeString(value.geValue().format(DateTimeFormatter.ISO_DATE_TIME));
    }
}
