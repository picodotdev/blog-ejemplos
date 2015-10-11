package io.github.picodotdev.blogbitix.springboot.tapestry.components;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

public class Layout {

	@Parameter(defaultPrefix = BindingConstants.LITERAL)
	@Property
	private String title;

	@Property
	private String page;

	@Inject
	ComponentResources resources;

	void setupRender() {
		page = resources.getPageName();
	}
}