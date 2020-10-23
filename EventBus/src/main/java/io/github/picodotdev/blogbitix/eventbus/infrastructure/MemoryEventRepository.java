package io.github.picodotdev.blogbitix.eventbus.infrastructure;

import io.github.picodotdev.blogbitix.eventbus.domain.shared.eventbus.Event;
import io.github.picodotdev.blogbitix.eventbus.domain.shared.eventbus.EventId;
import io.github.picodotdev.blogbitix.eventbus.domain.shared.repository.EventRepository;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class MemoryEventRepository implements EventRepository {

    private Map<EventId, Event> store;

    public MemoryEventRepository() {
        store = new HashMap<>();
    }

    @Override
    public void add(Event event) {
        store.put(event.getId(), event);
    }

    @Override
    public boolean exists(Event event) {
        return store.containsKey(event.getId());
    }
}
