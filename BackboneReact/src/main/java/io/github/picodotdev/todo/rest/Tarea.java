package io.github.picodotdev.todo.rest;

public class Tarea {

	private Long id;
	
	private String descripcion;
	
	private Boolean completada;
	
	public Tarea() {		
	}
	
	public Tarea(String descripcion, Boolean completada) {
		this(null, descripcion, completada);
	}
	
	public Tarea(Long id, String descripcion, Boolean completada) {
		this.id = id;
		this.descripcion = descripcion;
		this.completada = completada;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Boolean isCompletada() {
		return completada;
	}

	public void setCompletada(Boolean completada) {
		this.completada = completada;
	}
}