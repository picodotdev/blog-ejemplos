package io.github.picodotdev.blogbitix.eventbus.domain.kernel.aggregate;

import io.github.picodotdev.blogbitix.eventbus.domain.kernel.domainevent.DomainEventCollection;

public interface Aggregate {

    DomainEventCollection getEvents();
}
