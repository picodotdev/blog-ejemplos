package io.github.picodotdev.blogbitix.eventbus.infrastructure;

import io.github.picodotdev.blogbitix.eventbus.domain.inventory.Product;
import io.github.picodotdev.blogbitix.eventbus.domain.inventory.ProductId;
import io.github.picodotdev.blogbitix.eventbus.domain.order.Order;
import io.github.picodotdev.blogbitix.eventbus.domain.order.OrderId;
import io.github.picodotdev.blogbitix.eventbus.domain.order.OrderRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
public class MemoryOrderRepository implements OrderRepository {

    private Map<OrderId, Order> orders;

    public MemoryOrderRepository() {
        this.orders = new HashMap<>();
    }

    @Override
    public OrderId generateId() {
        return new OrderId(UUID.randomUUID());
    }

    @Override
    public void save(Order order) {
        orders.put(order.getId(), order);
    }

    @Override
    public Order findById(OrderId id) {
        return orders.get(id);
    }

    @Override
    public Collection<Order> findAll() {
        return orders.values();
    }
}
