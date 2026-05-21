package io.github.picodotdev.blogbitix.asyncapicloudevents;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/events")
public class EventsController {

    private final EventsProducer producer;

    public EventsController(EventsProducer producer) {
        this.producer = producer;
    }

    @PostMapping
    public ResponseEntity<Void> send(@RequestBody EventPayload payload) {
        producer.send(payload);
        return ResponseEntity.accepted().build();
    }
}
