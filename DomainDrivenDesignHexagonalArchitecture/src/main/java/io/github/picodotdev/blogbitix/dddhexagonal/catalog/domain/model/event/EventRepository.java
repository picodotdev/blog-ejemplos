package io.github.picodotdev.blogbitix.dddhexagonal.catalog.domain.model.event;

public interface EventRepository {

    EventId getId();

    Event findById(EventId id);

    void persist(Event event);
}
