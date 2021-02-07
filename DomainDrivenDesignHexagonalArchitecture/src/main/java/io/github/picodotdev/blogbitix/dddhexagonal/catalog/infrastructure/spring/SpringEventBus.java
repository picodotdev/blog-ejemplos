package io.github.picodotdev.blogbitix.dddhexagonal.catalog.infrastructure.spring;

import java.time.format.DateTimeFormatter;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import io.github.picodotdev.blogbitix.dddhexagonal.catalog.domain.shared.domaineventbus.DomainEvent;
import io.github.picodotdev.blogbitix.dddhexagonal.catalog.domain.shared.domaineventbus.DomainEventBus;

@Component
@Primary
public class SpringEventBus implements DomainEventBus {

    public void publish(DomainEvent event) {
        System.out.printf("%s %s %s%n", event.getClass().getName(), event.getId().getValue(), event.getDate().format(DateTimeFormatter.ISO_DATE_TIME));
    }
}