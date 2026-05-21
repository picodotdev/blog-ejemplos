package io.github.picodotdev.blogbitix.dddhexagonal.catalog.infrastructure;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Component;

import io.github.picodotdev.blogbitix.dddhexagonal.catalog.domain.model.event.Event;
import io.github.picodotdev.blogbitix.dddhexagonal.catalog.domain.model.event.EventId;
import io.github.picodotdev.blogbitix.dddhexagonal.catalog.domain.model.event.EventRepository;

@Component
public class InMemoryEventRepository implements EventRepository {

    private AtomicLong sequence;
    private Map<EventId, Event> events;

    public InMemoryEventRepository() {
        this.sequence = new AtomicLong();
        this.events = new HashMap<>();
    }

    @Override
    public EventId getId() {
        Long id = sequence.addAndGet(1);
        return EventId.valueOf(new BigInteger(id.toString()));
    }

    @Override
    public Event findById(EventId id) {
        return events.get(id);
    }

    @Override
    public void persist(Event event) {
        events.put(event.getId(), event);
    }
}
