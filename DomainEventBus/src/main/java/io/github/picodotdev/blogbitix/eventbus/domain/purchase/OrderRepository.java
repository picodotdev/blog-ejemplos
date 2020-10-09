package io.github.picodotdev.blogbitix.eventbus.domain.purchase;

import java.util.List;

public interface OrderRepository {

    OrderId generateId();

    void save(Order order);

    List<Order> findAll();
}
