package io.github.picodotdev.blogbitix.tapestrymultipleactions.components;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;

public class Layout {

	@Parameter(defaultPrefix = BindingConstants.LITERAL)
	@Property
	private String title;
}