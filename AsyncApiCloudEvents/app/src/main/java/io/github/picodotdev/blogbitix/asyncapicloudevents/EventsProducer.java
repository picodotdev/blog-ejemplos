package io.github.picodotdev.blogbitix.asyncapicloudevents;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cloudevents.CloudEvent;
import io.cloudevents.core.builder.CloudEventBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.UUID;

@Component
public class EventsProducer {

    private static final Logger log = LogManager.getLogger(EventsProducer.class);

    private final KafkaTemplate<String, CloudEvent> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private final String topic;

    public EventsProducer(KafkaTemplate<String, CloudEvent> kafkaTemplate,
                          ObjectMapper objectMapper,
                          @Value("${app.topic}") String topic) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
        this.topic = topic;
    }

    public void send(EventPayload payload) {
        try {
            byte[] data = objectMapper.writeValueAsBytes(payload);

            CloudEvent event = CloudEventBuilder.v1()
                    .withId(UUID.randomUUID().toString())
                    .withSource(URI.create("io.github.picodotdev.blogbitix.asyncapicloudevents/events"))
                    .withType("io.github.picodotdev.blogbitix.asyncapicloudevents.Event")
                    .withTime(OffsetDateTime.now())
                    .withDataContentType("application/json")
                    .withData(data)
                    .build();
            kafkaTemplate.send(topic, payload.eventId(), event);
            log.info("Published event event (id={}, eventId={})", event.getId(), payload.eventId());
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Failed to serialize EventPayload", e);
        }
    }
}
