package io.github.picodotdev.blogbitix.dddhexagonal.catalog.domain.model.event;

import io.github.picodotdev.blogbitix.dddhexagonal.catalog.domain.shared.domaineventbus.DomainEvent;

public class EventDateRescheduledDomainEvent extends DomainEvent {

    private EventId eventId;

    public EventDateRescheduledDomainEvent(EventId eventId) {
        this.eventId = eventId;
    }

    public EventId getEventId() {
        return eventId;
    }
}