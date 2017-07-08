package io.github.picodotdev.blogbitix.tapestry.portlet.pages.preferences;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.portlet.services.PortletRequestGlobals;

import javax.inject.Inject;
import javax.portlet.PortletMode;

public class Index {

    @Property
    private String nombre;

    @Inject
    private PortletRequestGlobals globals;

    void setupRender() {
        nombre = globals.getRenderRequest().getPreferences().getValue("nombre", null);
    }

    String onSuccess() throws Exception {
        globals.getActionRequest().getPreferences().setValue("nombre", nombre);
        globals.getActionRequest().getPreferences().store();
        globals.getActionResponse().setPortletMode(PortletMode.VIEW);
		return "index";
    }
}
