package io.github.picodotdev.blogbitix.eventbus.domain.inventory;

import io.github.picodotdev.blogbitix.eventbus.domain.order.OrderId;
import io.github.picodotdev.blogbitix.eventbus.domain.shared.eventbus.Event;

import java.util.List;

public class OrderOversold extends Event {

    private OrderId orderId;
    private List<ProductId> productIds;

    public OrderOversold(OrderId orderId, List<ProductId> productIds) {
        this.orderId = orderId;
        this.productIds = productIds;
    }

    public OrderId getOrderId() {
        return orderId;
    }

    public List<ProductId> getProductIds() {
        return productIds;
    }
}
