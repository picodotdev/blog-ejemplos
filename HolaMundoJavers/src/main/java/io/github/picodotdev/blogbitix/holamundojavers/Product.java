package io.github.picodotdev.blogbitix.holamundojavers;

import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Set;

public class Product {

    @Id
    private String name;
    private BigDecimal price;
    private Set<Category> categories;

    public Product(String name, BigDecimal price, Set<Category> categories) {
        this.name = name;
        this.price = price;
        this.categories = categories;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }
}
