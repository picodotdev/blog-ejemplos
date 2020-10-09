package io.github.picodotdev.blogbitix.eventbus.infrastructure;

import io.github.picodotdev.blogbitix.eventbus.domain.purchase.Order;
import io.github.picodotdev.blogbitix.eventbus.domain.purchase.OrderId;
import io.github.picodotdev.blogbitix.eventbus.domain.purchase.OrderRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MemoryOrderRepository implements OrderRepository {

    private List<Order> orders;

    public MemoryOrderRepository() {
        this.orders = new ArrayList<>();
    }

    @Override
    public OrderId generateId() {
        return new OrderId(UUID.randomUUID());
    }

    @Override
    public void save(Order order) {
        orders.add(order);
    }

    @Override
    public List<Order> findAll() {
        return orders;
    }
}
