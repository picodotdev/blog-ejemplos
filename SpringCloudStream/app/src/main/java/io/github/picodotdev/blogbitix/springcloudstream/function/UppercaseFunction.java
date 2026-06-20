package io.github.picodotdev.blogbitix.springcloudstream.function;

import java.util.Locale;
import java.util.function.Function;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

public class UppercaseFunction implements Function<Message<String>, Message<String>> {

    private static final Logger log = LogManager.getLogger(LoggerConsumer.class);

    @Override
    public Message<String> apply(Message<String> message) {
        if (message == null) {
            return null;
        }
        String upperCase = message.getPayload().toUpperCase(Locale.ROOT);
        log.info("Uppercase message: {}, {}", message.getPayload(), upperCase);
        return MessageBuilder.withPayload(upperCase).build();
    }
}
