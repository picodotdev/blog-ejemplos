package io.github.picodotdev.blogbitix.eventbus.domain.purchase;

import java.util.UUID;

public class OrderId {

    private UUID id;

    public OrderId(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }
}
