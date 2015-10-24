package io.github.picodotdev.blogbitix.patronobserver;

import java.math.BigDecimal;
import java.util.Observer;

public class Main {
    public static void main(String[] args) {
        Producto p1 = new Producto("Libro", new BigDecimal("3.99"));
        Producto p2 = new Producto("Lector libros electrónico", new BigDecimal("129"));

        Observer o1 = new ProductoObserver();
        Producto.getObservable().addObserver(o1);

        p1.setPrecio(new BigDecimal("4.99"));
        p2.setPrecio(new BigDecimal("119"));
    }
}