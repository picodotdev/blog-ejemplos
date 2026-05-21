package io.github.picodotdev.blogbitix.eventbus.domain.order;

import java.util.Collection;

public interface OrderRepository {

    OrderId generateId();

    void save(Order order);

    Order findById(OrderId id);
    Collection<Order> findAll();
}
