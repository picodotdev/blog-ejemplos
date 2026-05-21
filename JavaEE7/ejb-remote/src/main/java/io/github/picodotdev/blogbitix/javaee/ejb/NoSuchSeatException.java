package io.github.picodotdev.blogbitix.javaee.ejb;

public class NoSuchSeatException extends Exception {

    private static final long serialVersionUID = 7401981122881781210L;

    private int id;
    
    public NoSuchSeatException(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }    
}