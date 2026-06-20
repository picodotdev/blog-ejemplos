package io.github.picodotdev.blogbitix.springcloudstream;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.picodotdev.blogbitix.springcloudstream.support.InMemoryLogAppender;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.binder.test.EnableTestBinder;
import org.springframework.cloud.stream.binder.test.InputDestination;
import org.springframework.cloud.stream.binder.test.OutputDestination;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

/**
 * Exercises the binding wiring with the in-memory test binder (no Kafka broker).
 *
 * <p>{@link EnableTestBinder} swaps the Kafka binder for the test binder and
 * contributes the {@link InputDestination} / {@link OutputDestination} beans.
 * Both functions ({@code uppercase;logger}) stay active from application.yml;
 * destinations are addressed by their topic names.
 */
@SpringBootTest
@EnableTestBinder
class StreamBindingsTest {

    @Autowired
    private InputDestination input;

    @Autowired
    private OutputDestination output;

    private InMemoryLogAppender appender;

    @BeforeEach
    void attachLogAppender() {
        appender = InMemoryLogAppender.attachToRootLogger();
    }

    @AfterEach
    void detachLogAppender() {
        appender.detachFromRootLogger();
    }

    @Test
    void uppercaseProcessorForwardsTransformedPayloadToOutputTopic() {
        Message<byte[]> incoming = MessageBuilder
                .withPayload("hello".getBytes(UTF_8))
                .build();

        // uppercase-in-0 -> topic "message"
        input.send(incoming, "message");

        // uppercase-out-0 -> topic "message-uppercase"
        Message<byte[]> received = output.receive(5_000, "message-uppercase");

        assertThat(received).isNotNull();
        assertThat(new String(received.getPayload(), UTF_8)).isEqualTo("HELLO");
    }

    @Test
    void loggerConsumesFromUppercaseTopicAndLogsPayload() {
        Message<byte[]> incoming = MessageBuilder
                .withPayload("HELLO".getBytes(UTF_8))
                .build();

        // logger-in-0 -> topic "message-uppercase"
        input.send(incoming, "message-uppercase");

        assertThat(appender.events())
                .anyMatch(event -> event.getMessage().getFormattedMessage().contains("HELLO"));
    }
}
