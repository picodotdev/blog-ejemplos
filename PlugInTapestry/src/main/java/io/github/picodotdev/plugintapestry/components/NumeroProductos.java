package io.github.picodotdev.plugintapestry.components;

import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.BeginRender;
import org.apache.tapestry5.ioc.annotations.Inject;

import io.github.picodotdev.plugintapestry.services.dao.JooqProductoDAO;

/**
 * @tapestrydoc
 */
public class NumeroProductos {

	@Inject
	JooqProductoDAO dao;

	@BeginRender
	boolean beginRender(MarkupWriter writer) {
		long numero = dao.countAll();
		writer.write(String.format("Hay %d productos", numero));
		return false;
	}
}