package io.github.picodotdev.blogbitix.eventbus.domain.purchase;

import io.github.picodotdev.blogbitix.eventbus.domain.inventory.Product;

import java.math.BigDecimal;

public class Item {

    private Product product;
    private long quantity;
    private BigDecimal tax;

    public Item(Product product, long quantity, BigDecimal tax) {
        this.product = product;
        this.quantity = quantity;
        this.tax = tax;
    }

    public Product getProduct() {
        return product;
    }

    public long getQuantity() {
        return quantity;
    }

    public BigDecimal getAmount() {
        return product.getPrice().multiply(tax.add(new BigDecimal("1")).multiply(BigDecimal.valueOf(quantity)));
    }
}
