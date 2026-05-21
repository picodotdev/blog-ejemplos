package io.github.picodotdev.blogbitix.dddhexagonal.catalog.domain.shared.domaineventbus;

import java.util.ArrayList;
import java.util.List;

public class DomainEventCollection {

    private List<DomainEvent> events;

    public DomainEventCollection() {
        this.events = new ArrayList<>();
    }

    public List<DomainEvent> getAll() {
        return events;
    }

    public void add(DomainEvent event) {
        events.add(event);
    }

    public void clear() {
        events.clear();
    }

    public void publish(DomainEventBus eventBus) {
        eventBus.publish(events);
        clear();
    }
}