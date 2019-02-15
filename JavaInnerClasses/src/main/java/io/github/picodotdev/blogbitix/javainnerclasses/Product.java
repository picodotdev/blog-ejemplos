package io.github.picodotdev.blogbitix.javainnerclasses;

import java.math.BigDecimal;

public class Product implements Comparable<Product> {

    private BigDecimal price;

    public Product(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public int compareTo(Product o) {
        return price.compareTo(o.getPrice());
    }
}