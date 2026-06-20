package io.github.picodotdev.blogbitix.springcloudstream.function;

import org.junit.jupiter.api.Test;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import static org.assertj.core.api.Assertions.assertThat;

class UppercaseFunctionTest {

    private final UppercaseFunction function = new UppercaseFunction();

    @Test
    void convertsPayloadToUpperCase() {
        Message<String> upperCase = function.apply(MessageBuilder.withPayload("hello world").build());
        assertThat(upperCase.getPayload()).isEqualTo("HELLO WORLD");
    }

    @Test
    void leavesAlreadyUppercasePayloadUnchanged() {
        Message<String> upperCase = function.apply(MessageBuilder.withPayload("HELLO WORLD").build());
        assertThat(upperCase.getPayload()).isEqualTo("HELLO WORLD");
    }

    @Test
    void handlesEmptyPayload() {
        Message<String> upperCase = function.apply(MessageBuilder.withPayload("").build());
        assertThat(upperCase.getPayload()).isEmpty();
    }
}
