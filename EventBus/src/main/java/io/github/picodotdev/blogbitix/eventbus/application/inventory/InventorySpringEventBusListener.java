package io.github.picodotdev.blogbitix.eventbus.application.inventory;

import io.github.picodotdev.blogbitix.eventbus.domain.order.OrderCreated;
import io.github.picodotdev.blogbitix.eventbus.domain.shared.commandbus.CommandBus;
import io.github.picodotdev.blogbitix.eventbus.infrastructure.MemoryEventRepository;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class InventorySpringEventBusListener {

    private CommandBus commandBus;
    private MemoryEventRepository eventRepository;

    private InventorySpringEventBusListener(CommandBus commandBus, MemoryEventRepository eventRepository) {
        this.commandBus = commandBus;
        this.eventRepository = eventRepository;
    }

    @EventListener
    public void onOrderCreated(OrderCreated orderCreated) throws Exception {
        if (eventRepository.exists(orderCreated)) {
            System.out.printf("Duplicated event %s%n", orderCreated.getId().getValue());
            return;
        }

        OrderCreatedCommand command = new OrderCreatedCommand(orderCreated);
        commandBus.handle(command);
        eventRepository.add(orderCreated);
    }
}
