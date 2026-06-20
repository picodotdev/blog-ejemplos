package io.github.picodotdev.blogbitix.springcloudstream.function;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.picodotdev.blogbitix.springcloudstream.support.InMemoryLogAppender;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.LogEvent;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.messaging.support.MessageBuilder;

class LoggerConsumerTest {

    private final LoggerConsumer loggerConsumer = new LoggerConsumer();

    private InMemoryLogAppender appender;

    @BeforeEach
    void attachAppender() {
        appender = InMemoryLogAppender.attachToRootLogger();
    }

    @AfterEach
    void detachAppender() {
        appender.detachFromRootLogger();
    }

    @Test
    void logsReceivedMessageAtInfoLevel() {
        loggerConsumer.accept(MessageBuilder.withPayload("HELLO WORLD").build());

        assertThat(appender.events())
                .filteredOn(event -> event.getMessage().getFormattedMessage().contains("HELLO WORLD"))
                .singleElement()
                .extracting(LogEvent::getLevel)
                .isEqualTo(Level.INFO);
    }
}
