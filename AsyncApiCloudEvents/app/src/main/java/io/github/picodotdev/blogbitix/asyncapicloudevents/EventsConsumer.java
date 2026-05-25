package io.github.picodotdev.blogbitix.asyncapicloudevents;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.cloudevents.CloudEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class EventsConsumer {

    private static final Logger log = LogManager.getLogger(EventsConsumer.class);

    private final ObjectMapper objectMapper;

    public EventsConsumer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "${app.topic}")
    public void onEvent(CloudEvent event) {
        if (event.getData() == null) {
            log.warn("Received CloudEvent {} with no data, skipping", event.getId());
            return;
        }
        try {
            EventPayload payload = objectMapper.readValue(event.getData().toBytes(), EventPayload.class);
            log.info("Received event (id={}, type={}, orderId={}, customerId={}, total={})", event.getId(), event.getType(), payload.orderId(),
                     payload.customerId(), payload.total());
        } catch (IOException e) {
            log.error("Failed to deserialize CloudEvent (id={})", event.getId(), e);
        }
    }
}
