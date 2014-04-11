package io.github.picodotdev.todo.tapestry.services;

import io.github.picodotdev.todo.rest.Application;
import io.github.picodotdev.todo.rest.TareasResource;
import io.github.picodotdev.todo.rest.TareasResourceImpl;

import java.util.Collection;

import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.InjectService;
import org.apache.tapestry5.services.HttpServletRequestFilter;

public class AppModule {

	// Servicios autoconstruidos por Tapestry, Tapestry se encarga de inyectar
	// las dependencias que necesiten los servicios de forma automática, ademas
	// se encarga del «live class reloading» cuando se hagan modificaciones en
	// la clase
	public static void bind(ServiceBinder binder) {
		binder.bind(TareasResource.class, TareasResourceImpl.class);

		// Filtro de integración con RESTEasy
		binder.bind(HttpServletRequestFilter.class, ResteasyRequestFilter.class).withId("ResteasyRequestFilter");
	}

	public static void contributeApplicationDefaults(MappedConfiguration<String, String> configuration) {
		String production = "false";
		configuration.add(SymbolConstants.PRODUCTION_MODE, production);
		configuration.add(SymbolConstants.COMPRESS_WHITESPACE, production);
		configuration.add(SymbolConstants.COMBINE_SCRIPTS, production);
		configuration.add(SymbolConstants.MINIFICATION_ENABLED, production);
		configuration.add(SymbolConstants.COMPACT_JSON, production);
		configuration.add(SymbolConstants.SUPPORTED_LOCALES, "es");

		// Contribuciones que definirán las rutas en las que el filtro de
		// RESTEasy atenderá las peticiones REST
		configuration.add(ResteasySymbols.MAPPING_PREFIX, "/rest");
		configuration.add(ResteasySymbols.MAPPING_PREFIX_JSAPI, "/rest-jsapi.js");
	}

	// Añadir el filtro de RESTEasy al pipeline de Tapestry
	public static void contributeHttpServletRequestHandler(OrderedConfiguration<HttpServletRequestFilter> configuration,
			@InjectService("ResteasyRequestFilter") HttpServletRequestFilter resteasyRequestFilter) {
		configuration.add("ResteasyRequestFilter", resteasyRequestFilter, "after:IgnoredPaths", "before:GZIP");
	}

	public static void contributeApplication(Configuration<Object> singletons, TareasResource todosResource) {
		// Contribuir a la configuración del servicio Application con los
		// servicios REST
		singletons.add(todosResource);
	}

	// Otra forma de definir un servicio en vez de en el método, la ventaja de
	// este es que puede recibir pármametros con valores provenientes de los
	// métodos contribute, la colección de singletos proviene de
	// contributeApplication
	public static Application buildApplication(Collection<Object> singletons) {
		return new Application(singletons);
	}
}