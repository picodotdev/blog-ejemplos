package io.github.picodotdev.blogbitix.eventbus.application.order;

import io.github.picodotdev.blogbitix.eventbus.domain.inventory.OrderOversold;
import io.github.picodotdev.blogbitix.eventbus.domain.shared.commandbus.CommandBus;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class OrderSpringEventBusListener {

    private CommandBus commandBus;

    private OrderSpringEventBusListener(CommandBus commandBus) {
        this.commandBus = commandBus;
    }

    @EventListener
    public void onOrderOversold(OrderOversold orderOversold) throws Exception {
        OrderOversoldCommand command = new OrderOversoldCommand(orderOversold.getOrderId());
        commandBus.handle(command);
    }
}
