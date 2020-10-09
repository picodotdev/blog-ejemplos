package io.github.picodotdev.blogbitix.eventbus.domain.kernel.domainevent;

import java.util.UUID;

public class DomainEventId {

    private UUID id;

    public DomainEventId(UUID id) {
        this.id = id;
    }

    public String getValue() {
        return id.toString();
    }
}
