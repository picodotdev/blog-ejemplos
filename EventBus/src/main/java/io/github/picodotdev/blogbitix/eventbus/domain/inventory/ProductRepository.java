package io.github.picodotdev.blogbitix.eventbus.domain.inventory;

import java.util.Collection;

public interface ProductRepository {

    ProductId generateId();

    void save(Product product);

    Product findById(ProductId id);
    Collection<Product> findAll();
}
