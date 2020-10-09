package io.github.picodotdev.blogbitix.eventbus.domain.purchase;

import io.github.picodotdev.blogbitix.eventbus.domain.kernel.domainevent.DomainEvent;

import java.util.Map;

public class OrderCreated extends DomainEvent {

    private OrderId orderId;
    private boolean oversold;

    public OrderCreated(OrderId orderId, boolean oversold) {
        super(Map.of("orderId", orderId, "oversold", oversold));
    }
}
