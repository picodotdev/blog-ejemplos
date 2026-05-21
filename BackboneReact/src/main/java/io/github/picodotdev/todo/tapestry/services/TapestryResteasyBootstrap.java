package io.github.picodotdev.todo.tapestry.services;

import javax.servlet.ServletContext;

import org.apache.tapestry5.ioc.services.SymbolSource;
import org.jboss.resteasy.plugins.server.servlet.ListenerBootstrap;

public class TapestryResteasyBootstrap extends ListenerBootstrap {

	SymbolSource source;

	public TapestryResteasyBootstrap(ServletContext servletContext, SymbolSource source) {
		super(servletContext);
		this.source = source;
	}

	public String getParameter(String name) {
		String val = null;

		try {
			val = source.valueForSymbol(name);
		} catch (RuntimeException e) {
			// ignore symbol not found
		}

		if (val == null)
			val = super.getParameter(name);

		return val;
	}

	@Override
	public String getInitParameter(String name) {
		return getParameter(name);
	}
}