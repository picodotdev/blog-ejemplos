package io.github.picodotdev.blogbitix.eventbus.domain.kernel.domainevent;

import java.util.ArrayList;
import java.util.List;

public class DomainEventCollection {

    private List<DomainEvent> domainEvents;

    public DomainEventCollection() {
        this.domainEvents = new ArrayList<>();
    }

    public List<DomainEvent> getAll() {
        return domainEvents;
    }

    public void add(DomainEvent domainEvent) {
        domainEvents.add(domainEvent);
    }

    public void clear() {
        domainEvents.clear();
    }

    public void publish(DomainEventBus eventBus) {
        eventBus.publish(getAll());
        clear();
    }
}
