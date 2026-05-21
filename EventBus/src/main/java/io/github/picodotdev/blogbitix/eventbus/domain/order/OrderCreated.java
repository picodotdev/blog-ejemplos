package io.github.picodotdev.blogbitix.eventbus.domain.order;

import io.github.picodotdev.blogbitix.eventbus.domain.shared.eventbus.Event;

import java.util.Map;

public class OrderCreated extends Event {

    private OrderId orderId;

    public OrderCreated(OrderId orderId) {
        this.orderId = orderId;
    }

    public OrderId getOrderId() {
        return orderId;
    }
}
