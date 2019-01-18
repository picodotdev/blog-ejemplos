package io.github.picodotdev.blogbitix.javajson;

import java.util.ArrayList;
import java.util.List;

public class Comprador {

    private String nombre;
    private int edad;
    private List<Direccion> direcciones;

    public Comprador() {
        direcciones = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public List<Direccion> getDirecciones() {
        return direcciones;
    }

    public void setDirecciones(List<Direccion> direcciones) {
        this.direcciones = direcciones;
    }
}