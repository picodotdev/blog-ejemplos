package io.github.picodotdev.blogbitix.springcloudstream.function;

import java.util.function.Consumer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.messaging.Message;

public class LoggerConsumer implements Consumer<Message<String>> {

    private static final Logger log = LogManager.getLogger(LoggerConsumer.class);

    @Override
    public void accept(Message<String> message) {
        log.info("Received message: {}", message.getPayload());
    }
}
