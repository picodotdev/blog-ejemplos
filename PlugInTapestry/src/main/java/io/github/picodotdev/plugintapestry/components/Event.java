package io.github.picodotdev.plugintapestry.components;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.PublishEvent;
import org.apache.tapestry5.corelib.components.Any;
import org.apache.tapestry5.json.JSONArray;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

/**
 * @tapestrydoc
 */
public class Event {

	@Parameter(defaultPrefix = BindingConstants.LITERAL)
	private String selector;

	@Component
	private Any span;

	@Environmental
	private JavaScriptSupport support;

	@PublishEvent
	Object onGetColores() {
		return new JSONArray("Rojo", "Verde", "Azul", "Negro");
	}

	protected void afterRender(MarkupWriter writer) {
		JSONObject spec = new JSONObject();
		spec.put("selector", "#" + span.getClientId());

		support.require("app/event").invoke("init").with(spec);
	}
}