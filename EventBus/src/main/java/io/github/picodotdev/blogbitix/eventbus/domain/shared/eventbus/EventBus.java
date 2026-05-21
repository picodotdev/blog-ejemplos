package io.github.picodotdev.blogbitix.eventbus.domain.shared.eventbus;

import io.github.picodotdev.blogbitix.eventbus.domain.shared.aggregateroot.AggregateRoot;

import java.util.Collection;

public interface EventBus {

    void publish(Event e);

    default void publish(Collection<Event> events) {
        events.stream().forEach(this::publish);
    }

    default void publish(EventCollection collection) {
        collection.publish(this);
    }

    default void publish(AggregateRoot aggregate) {
        publish(aggregate.getEvents());
    }
}
