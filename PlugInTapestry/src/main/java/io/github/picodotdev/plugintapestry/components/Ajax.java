package io.github.picodotdev.plugintapestry.components;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONArray;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

/**
 * @tapestrydoc
 */
public class Ajax {

	@Parameter(defaultPrefix = BindingConstants.LITERAL)
	private String selector;

	@Environmental
	private JavaScriptSupport support;

	@Inject
	private ComponentResources componentResources;

	Object onGetColores() {
		return new JSONArray("Rojo", "Verde", "Azul", "Negro");
	}

	protected void afterRender(MarkupWriter writer) {
		String link = componentResources.createEventLink("getColores").toAbsoluteURI();

		JSONObject spec = new JSONObject();
		spec.put("selector", selector);
		spec.put("link", link);

		support.require("app/ajax").invoke("init").with(spec);
	}
}