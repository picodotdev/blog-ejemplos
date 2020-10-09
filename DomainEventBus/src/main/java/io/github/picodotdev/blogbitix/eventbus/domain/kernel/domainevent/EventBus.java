package io.github.picodotdev.blogbitix.eventbus.domain.kernel.domainevent;

import io.github.picodotdev.blogbitix.eventbus.domain.kernel.aggregateroot.AggregateRoot;

import java.util.Collection;

public interface EventBus {

    void publish(DomainEvent e);

    default void publish(Collection<DomainEvent> e) {
        e.stream().forEach(this::publish);
    }

    default void publish(DomainEventCollection c) {
        c.publish(this);
    }

    default void publish(AggregateRoot a) {
        publish(a.getEvents());
    }
}
