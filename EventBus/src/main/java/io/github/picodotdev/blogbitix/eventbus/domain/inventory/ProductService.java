package io.github.picodotdev.blogbitix.eventbus.domain.inventory;

import io.github.picodotdev.blogbitix.eventbus.domain.order.Order;
import io.github.picodotdev.blogbitix.eventbus.domain.order.OrderCreated;
import io.github.picodotdev.blogbitix.eventbus.domain.order.OrderId;
import io.github.picodotdev.blogbitix.eventbus.domain.order.OrderRepository;
import io.github.picodotdev.blogbitix.eventbus.domain.shared.eventbus.EventBus;
import io.github.picodotdev.blogbitix.eventbus.infrastructure.MemoryEventRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class ProductService {

    private ProductRepository productRepository;
    private EventBus eventBus;
    private MemoryEventRepository eventRepository;

    public ProductService(ProductRepository productRepository, EventBus eventBus) {
        this.productRepository = productRepository;
        this.eventBus = eventBus;
    }

    public ProductId generateId() {
        return productRepository.generateId();
    }
}
