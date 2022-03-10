package io.github.picodotdev.blogbitix.springbootconfigconditional.service;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(prefix = "app.message", name = "implementation", havingValue = "spanish")
public class SpanishMessage implements Message {

    @Override
    public String get() {
        return "¡Hola mundo!";
    }
}
