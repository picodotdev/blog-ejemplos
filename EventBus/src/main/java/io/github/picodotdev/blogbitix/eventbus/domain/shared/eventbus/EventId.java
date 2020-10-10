package io.github.picodotdev.blogbitix.eventbus.domain.shared.eventbus;

import java.util.UUID;

public class EventId {

    private UUID id;

    public EventId(UUID id) {
        this.id = id;
    }

    public String getValue() {
        return id.toString();
    }
}
