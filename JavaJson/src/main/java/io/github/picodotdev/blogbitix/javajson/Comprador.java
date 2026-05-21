package io.github.picodotdev.blogbitix.javajson;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Comprador {

    private String nombre;
    private LocalDate fechaNacimiento;
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

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public List<Direccion> getDirecciones() {
        return direcciones;
    }

    public void setDirecciones(List<Direccion> direcciones) {
        this.direcciones = direcciones;
    }
}