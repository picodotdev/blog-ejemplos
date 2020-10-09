package io.github.picodotdev.blogbitix.eventbus.domain.inventory;

import java.math.BigDecimal;

public class Product {

    private BigDecimal price;
    private long stock;

    public Product(BigDecimal price, long stock) {
        this.price = price;
        this.stock = stock;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public long getStock() {
        return stock;
    }
}
