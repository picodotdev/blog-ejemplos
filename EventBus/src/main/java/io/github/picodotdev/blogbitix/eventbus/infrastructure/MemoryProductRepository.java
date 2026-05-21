package io.github.picodotdev.blogbitix.eventbus.infrastructure;

import io.github.picodotdev.blogbitix.eventbus.domain.inventory.Product;
import io.github.picodotdev.blogbitix.eventbus.domain.inventory.ProductId;
import io.github.picodotdev.blogbitix.eventbus.domain.inventory.ProductRepository;
import io.github.picodotdev.blogbitix.eventbus.domain.order.Order;
import io.github.picodotdev.blogbitix.eventbus.domain.order.OrderId;
import io.github.picodotdev.blogbitix.eventbus.domain.order.OrderRepository;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
public class MemoryProductRepository implements ProductRepository {

    private Map<ProductId, Product> products;

    public MemoryProductRepository() {
        this.products = new HashMap<>();

        Product product = new Product(generateId(), new BigDecimal("10.0"), 5);
        products.put(product.getId(), product);
    }

    @Override
    public ProductId generateId() {
        return new ProductId(UUID.randomUUID());
    }

    @Override
    public void save(Product product) {
        products.put(product.getId(), product);
    }

    @Override
    public Product findById(ProductId id) {
        return products.get(id);
    }

    @Override
    public Collection<Product> findAll() {
        return products.values();
    }
}
