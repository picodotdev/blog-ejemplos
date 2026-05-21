package io.github.picodotdev.blogbitix.eventbus.domain.order;

import io.github.picodotdev.blogbitix.eventbus.domain.inventory.ProductId;

import java.math.BigDecimal;

public class Item {

    private ProductId productId;
    private BigDecimal price;
    private long quantity;
    private BigDecimal tax;

    public Item(ProductId productId, BigDecimal price, long quantity, BigDecimal tax) {
        this.productId = productId;
        this.price = price;
        this.quantity = quantity;
        this.tax = tax;
    }

    public ProductId getProductId() {
        return productId;
    }

    public long getQuantity() {
        return quantity;
    }

    public BigDecimal getAmount() {
        return price.multiply(tax.add(new BigDecimal("1")).multiply(BigDecimal.valueOf(quantity)));
    }
}
