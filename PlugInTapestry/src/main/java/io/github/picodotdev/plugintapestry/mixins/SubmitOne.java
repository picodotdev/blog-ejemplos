package io.github.picodotdev.plugintapestry.mixins;

import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.InjectContainer;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

public class SubmitOne {

	@Inject
	private JavaScriptSupport support;

	@InjectContainer
	private ClientElement element;

	@Inject
	private ComponentResources resources;

	public void afterRender() {
		JSONObject spec = new JSONObject();
		spec.put("elementId", element.getClientId());

		support.require("app/submitOne").invoke("init").with(spec);
	}
}