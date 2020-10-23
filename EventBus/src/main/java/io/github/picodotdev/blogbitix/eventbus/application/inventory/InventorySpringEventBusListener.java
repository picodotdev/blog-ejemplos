package io.github.picodotdev.blogbitix.eventbus.application.inventory;

import io.github.picodotdev.blogbitix.eventbus.domain.order.OrderCreated;
import io.github.picodotdev.blogbitix.eventbus.domain.shared.commandbus.CommandBus;
import io.github.picodotdev.blogbitix.eventbus.infrastructure.MemoryEventRepository;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class InventorySpringEventBusListener {

    private CommandBus commandBus;

    private InventorySpringEventBusListener(CommandBus commandBus, MemoryEventRepository eventRepository) {
        this.commandBus = commandBus;
    }

    @EventListener
    public void onOrderCreated(OrderCreated orderCreated) throws Exception {
        OrderCreatedCommand command = new OrderCreatedCommand(orderCreated);
        commandBus.handle(command);
    }
}
