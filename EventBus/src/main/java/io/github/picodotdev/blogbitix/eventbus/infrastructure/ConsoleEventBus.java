package io.github.picodotdev.blogbitix.eventbus.infrastructure;

import io.github.picodotdev.blogbitix.eventbus.domain.shared.eventbus.Event;
import io.github.picodotdev.blogbitix.eventbus.domain.shared.eventbus.EventBus;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component("ConsoleEventBus")
public class ConsoleEventBus implements EventBus {

    @Override
    public void publish(Event event) {
        System.out.printf("%s %s %s%n", event.getClass().getName(), event.getId().getValue(), event.getDate().format(DateTimeFormatter.ISO_DATE_TIME));
    }
}
