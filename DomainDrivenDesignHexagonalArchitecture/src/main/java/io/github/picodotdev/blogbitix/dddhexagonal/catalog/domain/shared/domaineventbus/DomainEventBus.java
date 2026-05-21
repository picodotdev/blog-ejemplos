package io.github.picodotdev.blogbitix.dddhexagonal.catalog.domain.shared.domaineventbus;

import java.util.Collection;

public interface DomainEventBus {

    void publish(DomainEvent e);

    default void publish(Collection<DomainEvent> events) {
        events.stream().forEach(this::publish);
    }

    default void publish(DomainEventCollection collection) {
        collection.publish(this);
    }
}