package io.github.picodotdev.blogbitix.springcloudstream.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {

    private static final Logger log = LogManager.getLogger(MessageController.class);

    private KafkaTemplate kafkaTemplate;

    public MessageController(KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping("/message")
    public ResponseEntity<?> message() {
        log.info("Sending message");
        String message = "Hello World!";
        kafkaTemplate.send("message", message.getBytes());
        return ResponseEntity.ok().build();
    }
}
