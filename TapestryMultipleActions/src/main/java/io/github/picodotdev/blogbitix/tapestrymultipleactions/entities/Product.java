package io.github.picodotdev.blogbitix.tapestrymultipleactions.entities;

public class Product {

    public enum State {
        ENABLED, DISABLED
    }

    public enum Action {
        ENABLE, DISABLE
    }

    private Long id;
    private String name;
    private Integer stock;
    private State state;

    public Product(Long id, String name, Integer stock, State state) {
        this.id = id;
        this.name = name;
        this.stock = stock;
        this.state = state;
    }

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

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public boolean hasStock() {
        return stock > 0;
    }

    public boolean isEnabled() { return state == State.ENABLED; }

    public boolean isDisabled() { return state == State.DISABLED; }
}
