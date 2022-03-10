package io.github.picodotdev.blogbitix.springbootconfigconditional.service;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(prefix = "app.message", name = "implementation", havingValue = "english")
public class EnglishMessage implements Message {

    @Override
    public String get() {
        return "Hello World!";
    }
}
