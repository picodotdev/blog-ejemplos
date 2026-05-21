package io.github.picodotdev.blogbitix.tapestrymultipleactions.services;

import io.github.picodotdev.blogbitix.tapestrymultipleactions.entities.Product;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class InMemoryProductRepository implements ProductRepository {

    private static List<Product> products;

    static {
        products = new ArrayList<>();
        products.add(new Product(1l, "Raspberry Pi 3", 10, Product.State.ENABLED));
        products.add(new Product(2l, "Raspberry Pi 2", 7, Product.State.ENABLED));
        products.add(new Product(3l, "Raspberry Pi 1", 0, Product.State.DISABLED));
    }

    public InMemoryProductRepository() {
    }

    @Override
    public Product find(Long id) {
        for (Product product : products) {
            if (product.getId().equals(id)) {
                return product;
            }
        }
        return null;
    }

    @Override
    public List<Product> findAll() {
        return products;
    }

    @Override
    public void enable(Product product) {
        enable(Collections.singleton(product));
    }

    @Override
    public void enable(Collection<Product> products) {
        for (Product product : products) {
            product.setState(Product.State.ENABLED);
        }
    }

    @Override
    public void disable(Product product, DisableReason reason) {
        disable(Collections.singleton(product), reason);
    }

    @Override
    public void disable(Collection<Product> products, DisableReason reason) {
        for (Product product : products) {
            product.setState(Product.State.DISABLED);
        }
    }
}
