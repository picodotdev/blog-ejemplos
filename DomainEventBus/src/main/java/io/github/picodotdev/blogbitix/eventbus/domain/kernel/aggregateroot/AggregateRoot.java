package io.github.picodotdev.blogbitix.eventbus.domain.kernel.aggregateroot;

import io.github.picodotdev.blogbitix.eventbus.domain.kernel.domainevent.DomainEventCollection;

public interface AggregateRoot {

    DomainEventCollection getEvents();
}
