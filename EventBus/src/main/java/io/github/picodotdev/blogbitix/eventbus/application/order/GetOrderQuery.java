package io.github.picodotdev.blogbitix.eventbus.application.order;

import io.github.picodotdev.blogbitix.eventbus.domain.order.Order;
import io.github.picodotdev.blogbitix.eventbus.domain.order.OrderId;
import io.github.picodotdev.blogbitix.eventbus.domain.shared.querybus.Query;

public class GetOrderQuery extends Query<Order> {

    private OrderId orderId;

    public GetOrderQuery(OrderId orderId) {
        this.orderId = orderId;
    }

    public OrderId getOrderId() {
        return orderId;
    }
}
