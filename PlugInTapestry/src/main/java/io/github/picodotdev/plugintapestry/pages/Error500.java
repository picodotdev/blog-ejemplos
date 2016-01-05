package io.github.picodotdev.plugintapestry.pages;

import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.services.Request;

/**
 * @tapestrydoc
 */
@SuppressWarnings("unused")
public class Error500 {

	@Property
	@Inject
	private Request request;

	@Property
	@Inject
	@Symbol(SymbolConstants.PRODUCTION_MODE)
	private boolean productionMode;
}