package io.github.picodotdev.blogbitix.eventbus.application.order;

import io.github.picodotdev.blogbitix.eventbus.domain.order.Order;
import io.github.picodotdev.blogbitix.eventbus.domain.order.OrderRepository;
import io.github.picodotdev.blogbitix.eventbus.domain.shared.querybus.QueryHandler;
import org.springframework.stereotype.Component;

@Component
public class GetOrderQueryHandler implements QueryHandler<Order,GetOrderQuery> {

    private OrderRepository orderRepository;

    public GetOrderQueryHandler(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order handle(GetOrderQuery query) throws Exception {
        return orderRepository.findById(query.getOrderId());
    }
}
