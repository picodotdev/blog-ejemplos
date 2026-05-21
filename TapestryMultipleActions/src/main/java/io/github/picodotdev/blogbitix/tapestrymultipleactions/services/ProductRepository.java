package io.github.picodotdev.blogbitix.tapestrymultipleactions.services;

import io.github.picodotdev.blogbitix.tapestrymultipleactions.entities.Product;

import java.util.Collection;
import java.util.List;

public interface ProductRepository {

    public enum DisableReason {
        DEFECTIVE, BAD_PRICE, DEPRECATED
    }

    Product find(Long id);
    List<Product> findAll();

    void enable(Product product);
    void enable(Collection<Product> products);
    void disable(Product product, DisableReason reason);
    void disable(Collection<Product> products, DisableReason reason);
}
