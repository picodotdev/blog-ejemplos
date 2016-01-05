package io.github.picodotdev.plugintapestry.components;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

/**
 * @tapestrydoc
 */
@Import(stack = { "core", "plugin" })
public class Layout {

	@Parameter(defaultPrefix = BindingConstants.LITERAL)
	@Property
	private String titulo;

	@Property
	private String pagina;

	@Inject
	ComponentResources resources;

	void setupRender() {
		pagina = resources.getPageName();
	}
}