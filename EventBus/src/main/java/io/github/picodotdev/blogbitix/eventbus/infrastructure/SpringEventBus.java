package io.github.picodotdev.blogbitix.eventbus.infrastructure;

import io.github.picodotdev.blogbitix.eventbus.domain.shared.eventbus.Event;
import io.github.picodotdev.blogbitix.eventbus.domain.shared.eventbus.EventBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component("SpringEventBus")
@Primary
public class SpringEventBus implements EventBus {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    public void publish(Event event) {
        System.out.printf("%s %s %s%n", event.getClass().getName(), event.getId().getValue(), event.getDate().format(DateTimeFormatter.ISO_DATE_TIME));
        applicationEventPublisher.publishEvent(event);
    }
}
