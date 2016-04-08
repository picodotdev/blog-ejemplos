package io.github.picodotdev.blogbitix.javaee.ejb;

import java.util.List;

import io.github.picodotdev.blogbitix.javaee.jpa.NoStockException;
import io.github.picodotdev.blogbitix.javaee.jpa.Product;
import io.github.picodotdev.blogbitix.javaee.jpa.Purchase;

public interface SupermarketRemote {

    List<Product> findProducts();

    Purchase getCart() throws NoStockException;

    void setCart(Purchase purchase) throws NoStockException;

    void buy(Purchase purchase) throws NoStockException;
}