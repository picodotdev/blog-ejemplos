package io.github.picodotdev.blogbitix.eventbus.application.order;

import io.github.picodotdev.blogbitix.eventbus.domain.order.OrderId;
import io.github.picodotdev.blogbitix.eventbus.domain.shared.commandbus.CommandHandler;
import org.springframework.stereotype.Component;

@Component
public class OrderOversoldCommandHandler implements CommandHandler<OrderOversoldCommand> {

    @Override
    public void handle(OrderOversoldCommand command) throws Exception {
        OrderId orderId = command.getOrderId();
        System.out.printf("OrderOversold: %s%n", orderId.getId());
    }
}
