package io.github.picodotdev.todo.tapestry.pages;

import io.github.picodotdev.todo.rest.Tarea;
import io.github.picodotdev.todo.rest.TareasResource;

import java.util.Collection;

import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.EventContext;
import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.services.HttpError;
import org.apache.tapestry5.services.Request;

import com.google.gson.Gson;

@Import(stylesheet = "context:css/core.css")
public class Index {

	@Inject
	@Symbol(SymbolConstants.TAPESTRY_VERSION)
	@Property
	private String tapestryVersion;

	@Inject
	@Property
	private Request request;

	@Inject
	private ComponentResources resources;

	@Inject
	private TareasResource tareasResource;

	Object onActivate(EventContext context) {
		if (context.getCount() == 0) {
			return null;
		}

		return new HttpError(404, "Resource not found.");
	}

	public String getTareasJSON() {
		Collection<Tarea> tareas = tareasResource.readTareas();
		return new Gson().toJson(tareas);
	}

	public String getLocale() {
		if (request.getLocale().getCountry() == null || request.getLocale().getCountry().trim().isEmpty()) {
			return String.format("%s", request.getLocale().getLanguage());
		} else {
			return String.format("%s-%s", request.getLocale().getLanguage(), request.getLocale().getCountry());
		}
	}
}