package io.github.picodotdev.blogbitix.dddhexagonal.catalog.infrastructure.rest.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;

import io.github.picodotdev.blogbitix.dddhexagonal.catalog.domain.model.event.EventSchedule;

@JsonComponent
public class EventScheduleSerializer extends JsonSerializer<EventSchedule> {

    @Override
    public Class<EventSchedule> handledType() {
        return EventSchedule.class;
    }

    @Override
    public void serialize(EventSchedule value, JsonGenerator generator, SerializerProvider serializers) throws IOException {
        generator.writeStartObject();
        generator.writeFieldName("startDate");
        serializers.defaultSerializeValue(value.getStartDate(), generator);
        generator.writeFieldName("endDate");
        serializers.defaultSerializeValue(value.getEndDate(), generator);
        generator.writeEndObject();
    }
}
