package io.github.picodotdev.blogbitix.eventbus.application.inventory;

import io.github.picodotdev.blogbitix.eventbus.domain.inventory.OrderOversold;
import io.github.picodotdev.blogbitix.eventbus.domain.inventory.Product;
import io.github.picodotdev.blogbitix.eventbus.domain.inventory.ProductId;
import io.github.picodotdev.blogbitix.eventbus.domain.inventory.ProductRepository;
import io.github.picodotdev.blogbitix.eventbus.domain.order.Item;
import io.github.picodotdev.blogbitix.eventbus.domain.order.Order;
import io.github.picodotdev.blogbitix.eventbus.domain.order.OrderCreated;
import io.github.picodotdev.blogbitix.eventbus.domain.order.OrderId;
import io.github.picodotdev.blogbitix.eventbus.domain.order.OrderRepository;
import io.github.picodotdev.blogbitix.eventbus.domain.shared.commandbus.CommandHandler;
import io.github.picodotdev.blogbitix.eventbus.domain.shared.eventbus.EventBus;
import io.github.picodotdev.blogbitix.eventbus.domain.shared.repository.EventRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderCreatedCommandHandler implements CommandHandler<OrderCreatedCommand> {

    private ProductRepository productRepository;
    private OrderRepository orderRepository;
    private EventRepository eventRepository;
    private EventBus eventBus;

    public OrderCreatedCommandHandler(ProductRepository productRepository, OrderRepository orderRepository, EventRepository eventRepository, EventBus eventBus) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.eventRepository = eventRepository;
        this.eventBus = eventBus;
    }

    @Override
    public void handle(OrderCreatedCommand command) {
        OrderCreated event = command.getEvent();

        if (eventRepository.exists(event)) {
            System.out.printf("Duplicated event %s%n", event.getId().getValue());
            return;
        }

        OrderId orderId = event.getOrderId();
        Order order = orderRepository.findById(orderId);

        List<ProductId> oversoldProductIds = order.getItems().stream().filter(it -> {
            Product product = productRepository.findById(it.getProductId());
            return !product.hasStock(it.getQuantity());
        }).map(Item::getProductId).collect(Collectors.toList());

        order.getItems().forEach(it -> {
            Product product = productRepository.findById(it.getProductId());
            product.subtractStock(it.getQuantity());
            eventBus.publish(product);
        });

        if (!oversoldProductIds.isEmpty()) {
            eventBus.publish(new OrderOversold(orderId, oversoldProductIds));
        }

        eventRepository.add(event);
    }
}
