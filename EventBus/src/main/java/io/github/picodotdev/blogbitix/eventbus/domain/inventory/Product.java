package io.github.picodotdev.blogbitix.eventbus.domain.inventory;

import io.github.picodotdev.blogbitix.eventbus.domain.shared.aggregateroot.AggregateRoot;
import io.github.picodotdev.blogbitix.eventbus.domain.shared.eventbus.EventCollection;

import java.math.BigDecimal;
import java.util.Objects;

public class Product implements AggregateRoot {

    private ProductId id;
    private BigDecimal price;
    private long stock;

    private EventCollection events;

    public Product(ProductId id, BigDecimal price, long stock) {
        this.id = id;
        this.price = price;
        this.stock = stock;
        this.events = new EventCollection();
    }

    public ProductId getId() {
        return id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public long getStock() {
        return stock;
    }

    public boolean hasStock(long quantity) {
        return stock >= quantity;
    }

    public void subtractStock(long quantity) {
        if (hasStock(quantity)) {
            stock -= quantity;
        } else {
            stock = 0;
        }
    }

    @Override
    public EventCollection getEvents() {
        return events;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id.equals(product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
