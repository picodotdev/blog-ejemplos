package io.github.picodotdev.blogbitix.eventbus.application.order;

import io.github.picodotdev.blogbitix.eventbus.domain.order.Item;
import io.github.picodotdev.blogbitix.eventbus.domain.order.OrderId;
import io.github.picodotdev.blogbitix.eventbus.domain.shared.commandbus.Command;

import java.util.List;

public class CreateOrderCommand extends Command {

    private OrderId orderId;
    private List<Item> items;

    public CreateOrderCommand(OrderId orderId, List<Item> items) {
        this.orderId = orderId;
        this.items = items;
    }

    public OrderId getOrderId() {
        return orderId;
    }

    public List<Item> getItems() {
        return items;
    }
}
