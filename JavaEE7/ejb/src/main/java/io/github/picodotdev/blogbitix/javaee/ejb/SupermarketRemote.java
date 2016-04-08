package io.github.picodotdev.blogbitix.javaee.ejb;

import io.github.picodotdev.blogbitix.javaee.jpa.*;

import javax.ejb.Remote;
import java.util.List;

@Remote
public interface SupermarketRemote {

    List<Product> findProducts();

    Product findProduct(Long id);

    User findUser(String name);

    void persistsProduct(Product product);

    void deleteProduct(Product product);

    List<Purchase> findPurchases();

    Purchase findPurchase(Long id);

    Purchase buy(Cart cart, User buyer) throws NoStockException;
}