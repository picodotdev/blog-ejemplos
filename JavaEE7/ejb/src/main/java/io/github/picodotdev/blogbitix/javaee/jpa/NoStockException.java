package io.github.picodotdev.blogbitix.javaee.jpa;

import java.util.Arrays;
import java.util.List;

public class NoStockException extends Exception {

    private static final long serialVersionUID = -102606054290701322L;
    
    private Product product;
    private List<Item> items;
    
    public NoStockException(Product product) {
        this.product = product;
    }
    
    public NoStockException(Item item) {
        this(Arrays.asList(item));
    }
    
    public NoStockException(List<Item> items) {
        this.items = items;
    }
    
    public Product getProduct() {
        return product;
    }
    
    public List<Item> getItems() {
        return items;
    }
}