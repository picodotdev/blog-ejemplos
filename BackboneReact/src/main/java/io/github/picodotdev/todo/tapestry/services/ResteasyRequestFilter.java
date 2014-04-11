package io.github.picodotdev.todo.tapestry.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.ext.Provider;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.ioc.services.SymbolSource;
import org.apache.tapestry5.services.ApplicationGlobals;
import org.apache.tapestry5.services.HttpServletRequestFilter;
import org.apache.tapestry5.services.HttpServletRequestHandler;
import org.jboss.resteasy.core.Dispatcher;
import org.jboss.resteasy.core.ResourceMethodRegistry;
import org.jboss.resteasy.core.SynchronousDispatcher;
import org.jboss.resteasy.jsapi.JSAPIWriter;
import org.jboss.resteasy.jsapi.ServiceRegistry;
import org.jboss.resteasy.plugins.server.servlet.HttpRequestFactory;
import org.jboss.resteasy.plugins.server.servlet.HttpResponseFactory;
import org.jboss.resteasy.plugins.server.servlet.HttpServletInputMessage;
import org.jboss.resteasy.plugins.server.servlet.HttpServletResponseWrapper;
import org.jboss.resteasy.plugins.server.servlet.ListenerBootstrap;
import org.jboss.resteasy.plugins.server.servlet.ServletContainerDispatcher;
import org.jboss.resteasy.specimpl.UriInfoImpl;
import org.jboss.resteasy.spi.HttpRequest;
import org.jboss.resteasy.spi.HttpResponse;
import org.jboss.resteasy.spi.Registry;
import org.jboss.resteasy.spi.ResteasyProviderFactory;
import org.jboss.resteasy.util.GetRestful;

public class ResteasyRequestFilter implements HttpServletRequestFilter, HttpRequestFactory, HttpResponseFactory {

	private ServletContainerDispatcher servletContainerDispatcher;
	private Dispatcher dispatcher;
	private ResteasyProviderFactory providerFactory;
	private JSAPIWriter apiWriter;
	private ServiceRegistry serviceRegistry;

	private Pattern restFilterPattern;
	private Pattern jsapiFilterPattern;

	public ResteasyRequestFilter(@Inject @Symbol(ResteasySymbols.MAPPING_PREFIX) String restFilterPath,
			@Inject @Symbol(ResteasySymbols.MAPPING_PREFIX_JSAPI) String jsapiFilterPath, ApplicationGlobals globals, SymbolSource source, Application application)
			throws ServletException {

		// Inicializar los patrones de las rutas de las peticiones de los servicios
		this.restFilterPattern = Pattern.compile(restFilterPath + "/.*", Pattern.CASE_INSENSITIVE);
		this.jsapiFilterPattern = Pattern.compile(jsapiFilterPath, Pattern.CASE_INSENSITIVE);

		// Utilidad para obtener propiedades de configuración
		ListenerBootstrap bootstrap = new TapestryResteasyBootstrap(globals.getServletContext(), source);

		// Inicializar el contenedor de servicios REST
		this.servletContainerDispatcher = new ServletContainerDispatcher();
		this.servletContainerDispatcher.init(globals.getServletContext(), bootstrap, this, this);
		this.dispatcher = servletContainerDispatcher.getDispatcher();
		this.providerFactory = servletContainerDispatcher.getDispatcher().getProviderFactory();

		// Añadir los servicios de la aplicación al registro de servicios de RESTeasy
		processApplication(application);

		ResourceMethodRegistry registry = (ResourceMethodRegistry) globals.getServletContext().getAttribute(Registry.class.getName());
		this.serviceRegistry = new ServiceRegistry(null, registry, providerFactory, null);
		
		// Utilidad que generará el javascript con los clientes de los servicios
		this.apiWriter = new JSAPIWriter(restFilterPath);
	}

	@Override
	public boolean service(HttpServletRequest request, HttpServletResponse response, HttpServletRequestHandler handler) throws IOException {
		// Ruta solicitada
		String path = request.getServletPath();
		String pathInfo = request.getPathInfo();
		if (pathInfo != null)
			path += pathInfo;

		// Comprobar si la ruta solicitada cumple con el patrón de RESTEasy
		if (jsapiFilterPattern.matcher(path).matches()) {
			// Petición de clientes javascript
			String uri = request.getRequestURL().toString();
			uri = uri.substring(0, uri.length() - request.getServletPath().length());
			response.setContentType("text/javascript");
			apiWriter.writeJavaScript(uri, request, response, serviceRegistry);
			return true;				
		} else if (restFilterPattern.matcher(path).matches()) {
			// Petición a un servicio REST
			servletContainerDispatcher.service(request.getMethod(), request, response, true);
			return true;
		}

		// La petición no es para un servicio REST, la petición es para otro filtro
		return handler.service(request, response);
	}

	@Override
	public HttpRequest createResteasyHttpRequest(String httpMethod, HttpServletRequest request, HttpHeaders headers, UriInfoImpl uriInfo, HttpResponse theResponse,
			HttpServletResponse response) {
		return createHttpRequest(httpMethod, request, headers, uriInfo, theResponse);
	}

	@Override
	public HttpResponse createResteasyHttpResponse(HttpServletResponse response) {
		return createServletResponse(response);
	}

	protected HttpRequest createHttpRequest(String httpMethod, HttpServletRequest request, HttpHeaders headers, UriInfoImpl uriInfo, HttpResponse theResponse) {
		return new HttpServletInputMessage(request, theResponse, headers, uriInfo, httpMethod.toUpperCase(), (SynchronousDispatcher) dispatcher);
	}

	protected HttpResponse createServletResponse(HttpServletResponse response) {
		return new HttpServletResponseWrapper(response, providerFactory);
	}

	private void processApplication(Application application) {
		List<Class> actualResourceClasses = new ArrayList<Class>();
		List<Class> actualProviderClasses = new ArrayList<Class>();
		List resources = new ArrayList();
		List providers = new ArrayList();
		if (application.getClasses() != null) {
			for (Class clazz : application.getClasses()) {
				if (GetRestful.isRootResource(clazz)) {
					actualResourceClasses.add(clazz);
				} else if (clazz.isAnnotationPresent(Provider.class)) {
					actualProviderClasses.add(clazz);
				} else {
					throw new RuntimeException("Application.getClasses() returned unknown class type: " + clazz.getName());
				}
			}
		}
		if (application.getSingletons() != null) {
			for (Object obj : application.getSingletons()) {
				if (GetRestful.isRootResource(obj.getClass())) {
					resources.add(obj);
				} else if (obj.getClass().isAnnotationPresent(Provider.class)) {
					providers.add(obj);
				} else {
					throw new RuntimeException("Application.getSingletons() returned unknown class type: " + obj.getClass().getName());
				}
			}
		}
		for (Class clazz : actualProviderClasses)
			providerFactory.registerProvider(clazz);
		for (Object obj : providers)
			providerFactory.registerProviderInstance(obj);
		for (Class clazz : actualResourceClasses)
			dispatcher.getRegistry().addPerRequestResource(clazz);
		for (Object obj : resources)
			dispatcher.getRegistry().addSingletonResource(obj);
	}
}