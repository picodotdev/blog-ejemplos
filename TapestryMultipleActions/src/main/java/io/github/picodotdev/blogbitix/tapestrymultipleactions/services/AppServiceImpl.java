package io.github.picodotdev.blogbitix.tapestrymultipleactions.services;

import io.github.picodotdev.blogbitix.tapestrymultipleactions.entities.Product;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class AppServiceImpl implements AppService {

    @Override
    public Set<Product.Action> getAvaliableActions(Product product) {
        return Arrays.stream(Product.Action.values()).filter((Product.Action action) -> {
            switch (action) {
                case ENABLE:
                    return product.getState() == Product.State.DISABLED;
                case DISABLE:
                    return product.getState() == Product.State.ENABLED;
                default:
                    return false;
            }
        }).collect(Collectors.toSet());
    }
}
