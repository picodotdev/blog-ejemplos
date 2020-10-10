package io.github.picodotdev.blogbitix.eventbus.infrastructure;

import io.github.picodotdev.blogbitix.eventbus.domain.shared.eventbus.Event;
import io.github.picodotdev.blogbitix.eventbus.domain.shared.eventbus.EventBus;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component("ConsoleEventBus")
@Primary
public class ConsoleEventBus implements EventBus {

    @Override
    public void publish(Event e) {
        System.out.printf("%s %s %s%n", e.getClass().getName(), e.getId().getValue(), e.getDate().format(DateTimeFormatter.ISO_DATE_TIME));
    }
}
