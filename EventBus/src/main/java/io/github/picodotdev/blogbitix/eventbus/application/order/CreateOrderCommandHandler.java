package io.github.picodotdev.blogbitix.eventbus.application.order;

import io.github.picodotdev.blogbitix.eventbus.domain.order.Item;
import io.github.picodotdev.blogbitix.eventbus.domain.order.OrderId;
import io.github.picodotdev.blogbitix.eventbus.domain.order.OrderService;
import io.github.picodotdev.blogbitix.eventbus.domain.shared.commandbus.CommandHandler;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CreateOrderCommandHandler implements CommandHandler<CreateOrderCommand> {

    private OrderService orderService;

    public CreateOrderCommandHandler(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public void handle(CreateOrderCommand command) throws Exception {
        OrderId orderId = command.getOrderId();
        List<Item> items = command.getItems();
        orderService.create(orderId, items);
    }
}
