package io.github.picodotdev.blogbitix.javaee.jpa;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
public class Purchase implements Serializable {
 
    private static final long serialVersionUID = 7637832132479607722L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private Date date;

    @NotNull
    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private List<Item> items;
    @NotNull
    @ManyToOne
    private User buyer;    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
     
    public User getBuyer() {
        return buyer;
    }

    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }

    public void add(Item item) throws NoStockException {
        item.getProduct().subtractStock(item.getAmount());
        items.add(item);
    }
    
    public void remove(Item item) throws NoStockException {
        boolean removed = items.remove(item);
        if (removed) {
            item.getProduct().addStock(item.getAmount());
        }
    }

    public BigDecimal getPrice() {
        return items.stream().map(i -> {
            return i.getPrice();
        }).reduce(new BigDecimal("0"), (result, element) -> {
            return result.add(element);
        });
    }
}