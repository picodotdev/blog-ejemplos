package io.github.picodotdev.blogbitix.dddhexagonal.catalog.infrastructure.rest.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;

import io.github.picodotdev.blogbitix.dddhexagonal.catalog.domain.model.event.Event;

@JsonComponent
public class EventSerializer extends JsonSerializer<Event> {

    @Override
    public Class<Event> handledType() {
        return Event.class;
    }

    @Override
    public void serialize(Event value, JsonGenerator generator, SerializerProvider serializers) throws IOException {
        generator.writeStartObject();
        generator.writeFieldName("id");
        serializers.defaultSerializeValue(value.getId(), generator);
        generator.writeFieldName("status");
        serializers.defaultSerializeValue(value.getStatus(), generator);
        generator.writeFieldName("schedule");
        serializers.defaultSerializeValue(value.getSchedule(), generator);
        generator.writeEndObject();
    }
}
