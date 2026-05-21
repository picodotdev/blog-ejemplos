package io.github.picodotdev.plugintapestry.entities.hibernate;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

@Entity
@Table(schema = "PLUGINTAPESTRY")
public class Producto implements Serializable {

    private static final long serialVersionUID = 4301591927955774037L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Length(min = 3, max = 100)
    @Column(name = "nombre", length = 100)
    private String nombre;
    @NotNull
    @Length(min = 0, max = 5000)
    @Column(name = "descripcion", length = 5000)
    private String descripcion;
    @NotNull
    @Min(value = 0)
    @Max(value = 1000)
    @Column(name = "cantidad")
    private Long cantidad;
    @NotNull
    @Column(name = "fecha")
    private Date fecha;

    public Producto() {
    }

    public Producto(String nombre, String descripcion, Long cantidad, Date fecha) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.fecha = fecha;
    }

    public Long getId() {
        return id;
    }
    
    public void setId(Long id ) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Long getCantidad() {
        return cantidad;
    }

    public void setCantidad(Long cantidad) {
        this.cantidad = cantidad;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
