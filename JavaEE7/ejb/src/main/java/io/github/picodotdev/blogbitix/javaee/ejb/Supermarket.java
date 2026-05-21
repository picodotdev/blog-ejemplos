package io.github.picodotdev.blogbitix.javaee.ejb;

import io.github.picodotdev.blogbitix.javaee.jpa.*;

import javax.ejb.AccessTimeout;
import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.enterprise.inject.Any;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Stateless
@AccessTimeout(value = 15, unit = TimeUnit.SECONDS)
public class Supermarket implements SupermarketLocal, SupermarketRemote {

    @Inject @Any
    private Event<Purchase> event;

    @PersistenceContext(name = "primary")
    private EntityManager em;

    @Override
    public List<Product> findProducts() {
        TypedQuery<Product> query = em.createQuery("SELECT p FROM Product p", Product.class);
        return query.getResultList();
    }

    @Override
    public Product findProduct(Long id) {
        TypedQuery<Product> query = em.createQuery("SELECT p FROM Product p where id = :id", Product.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    @Override
    public User findUser(String name) {
        TypedQuery<User> query = em.createQuery("SELECT u FROM User u where name = :name", User.class);
        query.setParameter("name", name);
        return query.getSingleResult();
    }

    @Override
    @Transactional
    public void persistsProduct(Product product) {
        em.persist(product);
    }

    @Override
    @Transactional
    public void deleteProduct(Product product) {
        em.remove(product);
    }

    @Override
    public List<Purchase> findPurchases() {
        TypedQuery<Purchase> query = em.createQuery("SELECT p FROM Purchase p", Purchase.class);
        return query.getResultList();
    }

    @Override
    public Purchase findPurchase(Long id) {
        TypedQuery<Purchase> query = em.createQuery("SELECT p FROM Purchase p where id = :id", Purchase.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    @Override
    @Transactional
    public Purchase buy(Cart cart, User user) throws NoStockException {
        List<Item> items = cart.getItems().stream().map(i -> {
            Long id = Long.parseLong(i.get("id"));
            Integer amount = Integer.parseInt(i.get("amount"));

            Item item = new Item();
            item.setProduct(findProduct(id));
            item.setAmount(amount);

            return item;
        }).collect(Collectors.toList());

        Purchase purchase = new Purchase();
        purchase.setDate(new Date());
        purchase.setBuyer(user);
        purchase.setItems(items);

        List<Item> withoutStock = purchase.getItems().stream().filter(item -> !item.getProduct().hasStock(item.getAmount())).collect(Collectors.toList());
        if (!withoutStock.isEmpty()) {
            throw new NoStockException(withoutStock);
        }
        for (Item item : purchase.getItems()) {
            item.getProduct().subtractStock(item.getAmount());
        }

        em.persist(purchase);
        event.fire(purchase);

        return purchase;
    }
}