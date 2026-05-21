package io.github.picodotdev.plugintapestry.services.spring;

import io.github.picodotdev.plugintapestry.entities.hibernate.Producto;

public class DummyService {

	public void process(String action, Object entity) {
		if (entity instanceof Producto) {
		    Producto p = (Producto) entity;
			System.out.println(String.format("Action: %s, Id: %d", action, p.getId()));
		}
	}
}