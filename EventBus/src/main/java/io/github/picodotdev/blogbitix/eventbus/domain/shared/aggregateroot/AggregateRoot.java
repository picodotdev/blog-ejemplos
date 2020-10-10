package io.github.picodotdev.blogbitix.eventbus.domain.shared.aggregateroot;

import io.github.picodotdev.blogbitix.eventbus.domain.shared.eventbus.EventCollection;

public interface AggregateRoot {

    EventCollection getEvents();
}
