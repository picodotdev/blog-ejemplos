package io.github.picodotdev.blogbitix.dddhexagonal.catalog.infrastructure.rest.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;

import io.github.picodotdev.blogbitix.dddhexagonal.catalog.domain.model.event.EventId;

@JsonComponent
public class EventIdSerializer extends JsonSerializer<EventId> {

    @Override
    public Class<EventId> handledType() {
        return EventId.class;
    }

    @Override
    public void serialize(EventId value, JsonGenerator generator, SerializerProvider serializers) throws IOException {
        generator.writeNumber(value.getValue());
    }
}
