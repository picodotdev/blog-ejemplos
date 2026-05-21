package io.github.picodotdev.plugintapestry.components;

import org.apache.tapestry5.dom.Document;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.formos.tapestry.testify.core.ForComponents;

import io.github.picodotdev.plugintapestry.services.dao.JooqProductoDAO;
import io.github.picodotdev.plugintapestry.test.AbstractTest;

public class NumeroProductosTesterTest extends AbstractTest {

	// La forma de probar el html de un componente es incluyendolo en una página. Los parámetros se
	// le pasan al componente a través de la página que se le inyectan como si fuesen servicios

	// Propiedades que se inyectarán en la página para las propiedades anotadas con @Inject.
	// Los servicios de las propiedades @Inject se inyectan mediante su interfaz, en caso de tener
	// un tipo primitivo de datos o un String inyectar por nombre como en el caso de la propiedad
	// nombre.

	// @ForComponents("nombre")
	// private String nombre;

	@ForComponents
	private JooqProductoDAO dao;

	@Before
	public void before() {
		// Crear el mock del servicio
		dao = Mockito.mock(JooqProductoDAO.class);
		Mockito.when(dao.countAll()).thenReturn(0l);
	}

	@Test
	public void ceroProductos() {
		Document doc = tester.renderPage("test/NumeroProductosTest");
		Assert.assertEquals("Hay 0 productos", doc.getElementById("componente").getChildMarkup());
	}
}