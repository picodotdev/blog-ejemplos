package io.github.picodotdev.blogbitix.dddhexagonal.catalog.domain.model.event;

import io.github.picodotdev.blogbitix.dddhexagonal.catalog.domain.shared.domaineventbus.DomainEvent;

public class EventCancelledDomainEvent extends DomainEvent {

    private EventId eventId;

    public EventCancelledDomainEvent(EventId eventId) {
        this.eventId = eventId;
    }

    public EventId getEventId() {
        return eventId;
    }
}