package io.github.picodotdev.blogbitix.eventbus.domain.kernel.domainevent;

import io.github.picodotdev.blogbitix.eventbus.domain.kernel.aggregate.Aggregate;

import java.util.Collection;

public interface DomainEventBus {

    void publish(DomainEvent e);

    default void publish(Collection<DomainEvent> e) {
        e.stream().forEach(this::publish);
    }

    default void publish(DomainEventCollection c) {
        c.publish(this);
    }

    default void publish(Aggregate a) {
        publish(a.getEvents());
    }
}
