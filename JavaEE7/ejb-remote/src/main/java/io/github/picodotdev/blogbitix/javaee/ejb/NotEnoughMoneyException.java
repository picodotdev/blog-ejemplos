package io.github.picodotdev.blogbitix.javaee.ejb;

public class NotEnoughMoneyException extends Exception {

    private static final long serialVersionUID = -5672803237675984747L;

    private int id;
    
    public NotEnoughMoneyException(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }    
}