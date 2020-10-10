package io.github.picodotdev.blogbitix.eventbus.domain.order;

import io.github.picodotdev.blogbitix.eventbus.domain.shared.eventbus.EventBus;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderService {

    private OrderRepository orderRepository;
    private EventBus eventBus;

    public OrderService(OrderRepository orderRepository, EventBus eventBus) {
        this.orderRepository = orderRepository;
        this.eventBus = eventBus;
    }

    public OrderId generateId() {
        return orderRepository.generateId();
    }

    public void create(OrderId id, List<Item> items) throws Exception {
        Order order = Order.create(id, items);
        orderRepository.save(order);
        eventBus.publish(order);
    }
}
