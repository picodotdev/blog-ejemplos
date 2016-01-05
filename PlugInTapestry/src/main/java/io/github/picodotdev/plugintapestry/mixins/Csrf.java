package io.github.picodotdev.plugintapestry.mixins;

import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.InjectContainer;
import org.apache.tapestry5.annotations.MixinAfter;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.ActionLink;
import org.apache.tapestry5.corelib.components.BeanEditForm;
import org.apache.tapestry5.corelib.components.EventLink;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.dom.Element;
import org.apache.tapestry5.dom.Node;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.runtime.Component;
import org.apache.tapestry5.services.Request;

import io.github.picodotdev.plugintapestry.services.sso.Sid;

@MixinAfter
public class Csrf {

	@SessionState(create = false)
	private Sid sid;

	@Inject
	private Request request;

	@Inject
	private ComponentResources resources;

	@InjectContainer
	private Component container;

	void beginRender(MarkupWriter writer) {
		if (container instanceof EventLink || container instanceof ActionLink) {
			buildSid();

			Element element = writer.getElement();
			String href = element.getAttribute("href");
			String character = (href.indexOf('?') == -1) ? "?" : "&";
			element.forceAttributes("href", String.format("%s%st:sid=%s", href, character, sid.getSid()));
		}
	}

	void afterRenderTemplate(MarkupWriter writer) {
		if (container instanceof BeanEditForm) {
			Element form = null;
			for (Node node : writer.getElement().getChildren()) {
				if (node instanceof Element) {
					Element element = (Element) node;
					if (element.getName().equals("form")) {
						form = element;
						break;
					}
				}
			}
			if (form != null) {
				buildSid();

				Element e = form.element("input", "type", "hidden", "name", "t:sid", "value", sid.getSid());
				e.moveToTop(form);
			}
		}
	}

	void beforeRenderBody(MarkupWriter writer) {
		if (container instanceof Form) {
			buildSid();

			Element form = (Element) writer.getElement();
			form.element("input", "type", "hidden", "name", "t:sid", "value", sid.getSid());
		} else if (container instanceof BeanEditForm) {
			buildSid();

			Element form = (Element) writer.getElement();
			form.element("input", "type", "hidden", "name", "t:sid", "value", sid.getSid());
		}
	}

	private void buildSid() {
		if (sid == null) {
			sid = Sid.newInstance();
		}
	}
}