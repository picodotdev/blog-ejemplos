package io.github.picodotdev.blogbitix.patronobserver;

import java.math.BigDecimal;
import java.util.Observable;

public class Producto {

    public class PrecioEvent {
        
        private Producto producto;
        private BigDecimal precioAntiguo;
        private BigDecimal precioNuevo;
        
        public PrecioEvent(Producto producto,  BigDecimal precioAntiguo, BigDecimal precioNuevo) {
            this.producto = producto;
            this.precioAntiguo = precioAntiguo;
            this.precioNuevo = precioNuevo;
        }
        
        public Producto getProducto() {
            return producto;
        }
        
        public BigDecimal getPrecioAntiguo() {
            return precioAntiguo;
        }
        
        public BigDecimal getPrecioNuevo() {
            return precioNuevo;
        }        
    }

    private static final ProductoObservable OBSERVABLE;

    private String nombre;
    private BigDecimal precio;

    static {
        OBSERVABLE = new ProductoObservable();
    }

    public static Observable getObservable() {
        return OBSERVABLE;
    }

    public Producto(String nombre, BigDecimal precio) {
        this.nombre = nombre;
        this.precio = precio;
    }

    public String getNombre() {
        return nombre;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        PrecioEvent event = new PrecioEvent(this, this.precio, precio);

        this.precio = precio;

        synchronized (OBSERVABLE) {
            OBSERVABLE.setChanged();
            OBSERVABLE.notifyObservers(event);            
        }
    }
    
    private static class ProductoObservable extends Observable {
        @Override
        public synchronized void setChanged() {
            super.setChanged();
        }
    }
}