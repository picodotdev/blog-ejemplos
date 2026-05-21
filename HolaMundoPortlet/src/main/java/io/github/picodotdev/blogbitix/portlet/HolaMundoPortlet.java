package io.github.picodotdev.blogbitix.portlet;

import javax.portlet.*;
import java.io.IOException;
import java.util.Arrays;

public class HolaMundoPortlet extends GenericPortlet {

    public void doView(RenderRequest request, RenderResponse response) throws IOException, PortletException {
        PortletPreferences preferences = request.getPreferences();
        String name = preferences.getValue("name", null);
        if (name == null) {
            response.getWriter().write(String.format("¡Hola!"));
        } else {
            response.getWriter().write(String.format("Hola %s", name));
        }
    }

    public void doEdit(RenderRequest request, RenderResponse response) throws IOException, PortletException {
        PortletURL url = response.createActionURL();
        url.setParameter("addName", "addName");

        String template = 
            "<form id=\"%sForm\" action=\"%s\" method=\"post\">\n" +
            "   <label for=\"%sNameInput\">Name:</label>\n" +
            "   <input id=\"%sNameInput\" type=\"text\" name=\"%sname\">\n" +
            "   <input type=\"submit\" name=\"submit\" value=\"Enviar\" title=\"Enviar\">\n" +
            "</form>\n";

        String namespace = response.getNamespace();
        String html = String.format(template, namespace, url.toString(), namespace, namespace, namespace);

        response.getWriter().write(html);
        response.getWriter().close();
    }

    public void processAction(ActionRequest request, ActionResponse response) throws IOException, PortletException {
        String name = request.getParameter("name");
        if (name != null) {
            PortletPreferences preferences = request.getPreferences();
            preferences.setValue("name", name);
            preferences.store();
            response.setPortletMode(PortletMode.VIEW);
        }
    }
}
