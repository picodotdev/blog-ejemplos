package io.github.picodotdev.blogbitix.patronspecification.domain.product;

import io.github.picodotdev.blogbitix.patronspecification.specification.Specificable;
import io.github.picodotdev.blogbitix.patronspecification.specification.Specification;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.TemporalUnit;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;

@Entity
@Table(name = "Product")
public class Product implements Specificable<Product> {

    public static final BigDecimal CHEAP_PRICE = new BigDecimal("5.00");
    public static final Period LONG_TERM_PERIOD = Period.ofYears(1);
    public static final int OVERSTOCK_UNITS = 25;

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private LocalDate date;
    private BigDecimal price;
    private Integer units;

    public Product() {
    }

    public Product(String name, LocalDate date, BigDecimal price, Integer units) {
        this.name = name;
        this.date = date;
        this.price = price;
        this.units = units;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDate() {
        return date;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getUnits() {
        return units;
    }

    public void setUnits(Integer units) {
        this.units = units;
    }

    @Override
    public boolean satisfies(Specification<Product> specification) {
        return specification.isSatisfied(this);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null)
            return false;
        if (!(o instanceof Product))
            return false;

        Product that = (Product) o;
        return super.equals(that)
            && Objects.equals(this.id, that.id);
    }
}