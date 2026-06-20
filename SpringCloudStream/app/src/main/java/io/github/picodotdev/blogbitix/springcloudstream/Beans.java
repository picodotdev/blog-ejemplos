package io.github.picodotdev.blogbitix.springcloudstream;

import java.util.function.Consumer;
import java.util.function.Function;

import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import io.github.picodotdev.blogbitix.springcloudstream.function.LoggerConsumer;
import io.github.picodotdev.blogbitix.springcloudstream.function.UppercaseFunction;

@Component
public class Beans {

    @Bean
    Function<Message<String>, Message<String>> uppercase() {
        return new UppercaseFunction();
    }

    @Bean
    Consumer<Message<String>> logger() {
        return new LoggerConsumer();
    }
}
