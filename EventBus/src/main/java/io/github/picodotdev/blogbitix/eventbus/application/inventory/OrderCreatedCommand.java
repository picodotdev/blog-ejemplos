package io.github.picodotdev.blogbitix.eventbus.application.inventory;

import io.github.picodotdev.blogbitix.eventbus.domain.order.OrderCreated;
import io.github.picodotdev.blogbitix.eventbus.domain.shared.commandbus.Command;

public class OrderCreatedCommand extends Command {

    private OrderCreated event;

    public OrderCreatedCommand(OrderCreated event) {
        this.event = event;
    }

    public OrderCreated getEvent() {
        return event;
    }
}
