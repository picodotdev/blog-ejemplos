package io.github.picodotdev.blogbitix.javaee7.beans;

import io.github.picodotdev.blogbitix.javaee.ejb.SupermarketLocal;
import io.github.picodotdev.blogbitix.javaee.jpa.Cart;
import io.github.picodotdev.blogbitix.javaee.jpa.Product;

import javax.ejb.EJB;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Named
@RequestScoped
public class IndexBean {

    @EJB
    private SupermarketLocal supermarket;

    public List<Product> getProducts() {
        return supermarket.findProducts();
    }

    public Cart getCart() {
        Cart cart = (Cart) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("cart");
        if (cart == null) {
            cart = Cart.EMPTY;
        }
        return cart;
    }

    public boolean isBuyer() {
        return FacesContext.getCurrentInstance().getExternalContext().isUserInRole("buyer");
    }

    public Integer getAmount(Product product) {
        for (Map<String, String> item : getCart().getItems()) {
            Long id = Long.parseLong(item.get("id"));
            if (id.equals(product.getId())) {
                return Integer.parseInt(item.get("amount"));
            }
        }
        return 0;
    }

    public BigDecimal getPrice() {
        BigDecimal price = new BigDecimal("0");
        for (Map<String, String> item : getCart().getItems()) {
            Long id = Long.parseLong(item.get("id"));
            Product product = supermarket.findProduct(id);
            Integer amount = Integer.parseInt(item.get("amount"));
            price = price.add(product.getPrice().multiply(new BigDecimal(amount)));
        }
        return price;
    }
}
