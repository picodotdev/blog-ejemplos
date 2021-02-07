package io.github.picodotdev.blogbitix.dddhexagonal.catalog.domain.model.event;

import java.io.Serializable;

import io.github.picodotdev.blogbitix.dddhexagonal.catalog.domain.model.event.exceptions.EndDateIsBeforeStartDate;
import io.github.picodotdev.blogbitix.dddhexagonal.catalog.domain.model.event.exceptions.InvalidDate;
import io.github.picodotdev.blogbitix.dddhexagonal.catalog.domain.shared.domaineventbus.DomainEventCollection;

public class Event implements Serializable {

    private EventId id;
    private Status status;
    private EventSchedule schedule;

    private DomainEventCollection domainEvents;

    public enum Status {
        ACTIVE, CANCELLED, HIDDEN
    }

    public Event(EventId id, EventSchedule schedule) {
        this.id = id;
        this.status = Status.ACTIVE;
        this.schedule = schedule;
        this.domainEvents = new DomainEventCollection();
    }

    public EventId getId() {
        return id;
    }

    public Event.Status getStatus() {
        return status;
    }

    public EventSchedule getSchedule() {
        return schedule;
    }

    public DomainEventCollection getDomainEvents() {
        return domainEvents;
    }

    public static Event create(EventId id, EventSchedule schedule) throws Exception {
        if (!schedule.isFutureDate()) {
            throw new InvalidDate();
        }

        Event event = new Event(id, schedule);

        event.domainEvents.add(new EventCreatedDomainEvent(event.getId()));
        return event;
    }

    public void activate() {
        this.status = Status.ACTIVE;
        // TODO: domain event
    }

    public void cancel() {
        this.status = Status.CANCELLED;

        domainEvents.add(new EventCancelledDomainEvent(id));
    }

    public void hide() {
        this.status = Status.HIDDEN;
        // TODO: domain event
    }

    public void rescheduleDate(EventSchedule schedule) throws EndDateIsBeforeStartDate {
        this.schedule = schedule;

        domainEvents.add(new EventDateRescheduledDomainEvent(id));
    }
}
