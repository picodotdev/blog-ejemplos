package io.github.picodotdev.plugintapestry.components;

import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.internal.services.MarkupWriterImpl;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import io.github.picodotdev.plugintapestry.services.dao.JooqProductoDAO;

public class NumeroProductosTest {

	@Test
	public void conNombre() {
		// Si tuviese alguna propiedad de algún servicio con la anotación @Inject tendríamos crear
		// un mock de la dependencia
		JooqProductoDAO dao = Mockito.mock(JooqProductoDAO.class);
		Mockito.when(dao.countAll()).thenReturn(0l);

		// Crear el componente
		NumeroProductos componente = new NumeroProductos();

		// Si tuviese parámetros (anotación @Parameter) deberíamos inyectarlos, para ello debemos
		// crear setters o cambiar el ámbito de visibilidad a package (sin ámbito)
		componente.dao = dao;

		// Ejecutar el sujecto bajo prueba
		MarkupWriter writer = new MarkupWriterImpl();
		componente.beginRender(writer);

		// Comprobar el resultado
		Assert.assertEquals("Hay 0 productos", writer.toString());
	}
}