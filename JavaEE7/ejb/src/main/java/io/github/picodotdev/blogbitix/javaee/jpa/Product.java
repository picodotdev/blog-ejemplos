package io.github.picodotdev.blogbitix.javaee.jpa;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Product implements Serializable {

    private static final long serialVersionUID = 6189838988651825054L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Size(min = 1)
    private String name;
    @Min(value = 0)
    private Integer stock;
    @Min(value = 0)
    private BigDecimal price;
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public Integer getStock() {
        return stock;
    }
    
    public BigDecimal getPrice() {
        return price;
    }
    
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    
    public boolean hasStock() {
        return stock > 0;
    }
    
    public boolean hasStock(Integer amount) {
        return stock >= amount;
    }
    
    public void addStock(Integer amount) {
        stock += amount;
    }
    
    public void subtractStock(Integer amount) throws NoStockException {        
        if (!hasStock()) {
            throw new NoStockException(this);
        }
        stock -= amount;
    }
}