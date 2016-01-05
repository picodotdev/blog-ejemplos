package io.github.picodotdev.plugintapestry.components;

import org.apache.tapestry5.dom.Document;
import org.jaxen.JaxenException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.formos.tapestry.testify.core.ForComponents;
import com.formos.tapestry.xpath.TapestryXPath;

import io.github.picodotdev.plugintapestry.services.dao.JooqProductoDAO;
import io.github.picodotdev.plugintapestry.test.AbstractTest;

public class NumeroProductosXPathTesterTest extends AbstractTest {

	// @ForComponents("nombre")
	// private String nombre;

	@ForComponents
	private JooqProductoDAO dao;

	@Before
	public void before() {
		dao = Mockito.mock(JooqProductoDAO.class);
		Mockito.when(dao.countAll()).thenReturn(0l);
	}

	@Test
	public void ceroProductos() throws JaxenException {
		Document doc = tester.renderPage("test/NumeroProductosTest");
		String text = TapestryXPath.xpath("id('componente')").selectSingleElement(doc).getChildMarkup();
		Assert.assertEquals("Hay 0 productos", text);
	}
}