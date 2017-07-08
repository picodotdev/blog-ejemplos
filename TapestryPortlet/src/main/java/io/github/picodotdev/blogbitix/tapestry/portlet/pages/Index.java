package io.github.picodotdev.blogbitix.tapestry.portlet.pages;

import org.apache.tapestry5.portlet.services.PortletRequestGlobals;

import javax.inject.Inject;

public class Index {

    @Inject
    private PortletRequestGlobals globals;

    public String getMensaje() {
        if (globals.getRenderRequest() == null) {
            return "¡Hola mundo!";
        }
        String nombre = globals.getRenderRequest().getPreferences().getValue("nombre", null);
        if (nombre == null) {
            return "¡Hola mundo!";
        } else {
            return String.format("¡Hola %s!", nombre);
        }
    }

    public boolean isAutenticado() {
        if (globals.getRenderRequest() == null) {
            return false;
        }
        return globals.getRenderRequest().getUserPrincipal() != null;
    }
}
