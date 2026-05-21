package io.github.picodotdev.blogbitix.eventbus.domain.order;

import java.util.Objects;
import java.util.UUID;

public class OrderId {

    private UUID id;

    public OrderId(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        System.out.println("**" + this);
        System.out.println("**" + o);
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderId orderId = (OrderId) o;
        return id.equals(orderId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
