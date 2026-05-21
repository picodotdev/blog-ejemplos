package io.github.picodotdev.blogbitix.dddhexagonal.catalog.infrastructure.rest.spring;

import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import io.github.picodotdev.blogbitix.dddhexagonal.catalog.domain.model.event.Event;
import io.github.picodotdev.blogbitix.dddhexagonal.catalog.domain.model.event.EventId;
import io.github.picodotdev.blogbitix.dddhexagonal.catalog.infrastructure.rest.serializer.EventDateSerializer;
import io.github.picodotdev.blogbitix.dddhexagonal.catalog.infrastructure.rest.serializer.EventIdSerializer;
import io.github.picodotdev.blogbitix.dddhexagonal.catalog.infrastructure.rest.serializer.EventScheduleSerializer;
import io.github.picodotdev.blogbitix.dddhexagonal.catalog.infrastructure.rest.serializer.EventSerializer;

@Configuration
public class SpringRestConfiguration {

    @Bean
    @Order(value = 10)
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return builder -> {
            builder.serializerByType(Event.class, new EventSerializer());
            builder.serializerByType(EventId.class, new EventIdSerializer());
            builder.serializerByType(EventDateSerializer.class, new EventDateSerializer());
            builder.serializerByType(EventScheduleSerializer.class, new EventScheduleSerializer());
        };
    }
}
