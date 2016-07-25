package io.github.picodotdev.blogbitix.tapestrymultipleactions.services;

import io.github.picodotdev.blogbitix.tapestrymultipleactions.entities.Product;

import java.util.Set;

public interface AppService {
    Set<Product.Action> getAvaliableActions(Product product);
}
