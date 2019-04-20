package io.github.picodotdev.plugintapestry.services;

import io.github.picodotdev.plugintapestry.misc.ContextListener;
import io.github.picodotdev.plugintapestry.misc.DateTranslator;
import io.github.picodotdev.plugintapestry.misc.LocalDateTimeTranslator;
import io.github.picodotdev.plugintapestry.misc.PlugInStack;
import io.github.picodotdev.plugintapestry.misc.WildFlyClasspathURLConverter;
import io.github.picodotdev.plugintapestry.services.hibernate.HibernateSessionSourceImpl;
import io.github.picodotdev.plugintapestry.services.workers.CsrfWorker;
import org.apache.shiro.realm.Realm;
import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.beanvalidator.BeanValidatorConfigurer;
import org.apache.tapestry5.hibernate.HibernateSessionSource;
import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.annotations.Local;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.ioc.services.ClasspathURLConverter;
import org.apache.tapestry5.services.BaseURLSource;
import org.apache.tapestry5.services.Core;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.compatibility.Compatibility;
import org.apache.tapestry5.services.compatibility.Trait;
import org.apache.tapestry5.services.javascript.JavaScriptStack;
import org.apache.tapestry5.services.javascript.StackExtension;
import org.apache.tapestry5.services.transform.ComponentClassTransformWorker2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.tynamo.security.SecuritySymbols;

import javax.servlet.ServletContext;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

public class AppModule {

	private static final Logger logger = LoggerFactory.getLogger(AppModule.class);

	public static final String CDN_DOMAIN_PROTOCOL = "cdn.protocol";
	public static final String CDN_DOMAIN_HOST = "cdn.host";
	public static final String CDN_DOMAIN_PORT = "cdn.port";
	public static final String CDN_DOMAIN_PATH = "cdn.path";

	public static void bind(ServiceBinder binder) {
		// Añadir al contenedor de dependencias nuestros servicios, se proporciona la interfaz y la
		// implementación. Si tuviera un constructor con parámetros se inyectarían como
		// dependencias.
		// binder.bind(Sevicio.class, ServicioImpl.class);

		// Servicios de persistencia (definidos en Spring por la necesidad de que Spring gestione
		// las transacciones)
		// binder.bind(ProductoDAO.class, ProductoDAOImpl.class);
	}

	// Servicio que delega en Spring la inicialización de Hibernate, solo obtiene la configuración
	// de Hibernate creada por Spring
	public static HibernateSessionSource buildAppHibernateSessionSource(ApplicationContext context) {
		return new HibernateSessionSourceImpl(context);
	}

	public static BaseURLSource buildAppBaseURLSource(ApplicationContext context, Request request, @Symbol(SymbolConstants.HOSTPORT) int hostport, @Symbol(SymbolConstants.HOSTPORT_SECURE) int hostportSecure) {
		return new BaseURLSource() {
			public String getBaseURL(boolean secure) {
				String protocol = (secure || request.isSecure()) ? "https" : "http";
				String host = request.getServerName();
				int port = (secure || request.isSecure()) ? hostportSecure : hostport;
				return String.format("%s://%s:%d", protocol, host, port);
			}
		};
	}

	public static void contributeServiceOverride(MappedConfiguration<Class, Object> configuration, @Local BaseURLSource baseURLSource, @Local HibernateSessionSource hibernateSessionSource) {
		configuration.add(BaseURLSource.class, baseURLSource);
		configuration.add(HibernateSessionSource.class, hibernateSessionSource);
		// Servicio para usar un CDN lazy, pe. con Amazon CloudFront
		//configuration.addInstance(AssetPathConverter.class, CDNAssetPathConverterImpl.class);

		if (isJBossServer(ContextListener.SERVLET_CONTEXT)) {
			configuration.add(ClasspathURLConverter.class, new WildFlyClasspathURLConverter());
		}
	}
	
//	public void contributeRequestExceptionHandler(MappedConfiguration<Class, Class> configuration) {
//	    configuration.add(SQLException.class, Error500.class);
//	    configuration.add(ValidationException.class, ExceptionReport.class);
//	}

	public static void contributeApplicationDefaults(MappedConfiguration<String, Object> configuration) {
		configuration.add(SymbolConstants.HMAC_PASSPHRASE, UUID.randomUUID().toString());

		configuration.add(SymbolConstants.PRODUCTION_MODE, false);
		configuration.add(SymbolConstants.SUPPORTED_LOCALES, "es,en");

		configuration.add(SecuritySymbols.LOGIN_URL, "/login");
		configuration.add(SecuritySymbols.SUCCESS_URL, "/index");
		configuration.add(SecuritySymbols.UNAUTHORIZED_URL, "/unauthorized");
		configuration.add(SecuritySymbols.REDIRECT_TO_SAVED_URL, "true");
		
		configuration.add(SymbolConstants.SECURE_ENABLED, true);
		configuration.add(SymbolConstants.HOSTPORT, 8080);
		configuration.add(SymbolConstants.HOSTPORT_SECURE, 8443);
		configuration.add(SymbolConstants.ENABLE_PAGELOADING_MASK, false);

		configuration.add(SymbolConstants.APPLICATION_VERSION, "1.0");

		configuration.add(SymbolConstants.JAVASCRIPT_INFRASTRUCTURE_PROVIDER, "jquery");

		configuration.add(CDN_DOMAIN_PROTOCOL, "http");
		configuration.add(CDN_DOMAIN_HOST, "s3-eu-west-1.amazonaws.com");
		configuration.add(CDN_DOMAIN_PORT, "");
		configuration.add(CDN_DOMAIN_PATH, "");
	}

//	public static void contributeSecurityConfiguration(Configuration<SecurityFilterChain> configuration, SecurityFilterChainFactory factory) {
//		configuration.add(factory.createChain("/admin/**").add(factory.authc()).add(factory.ssl()).build());
//	}

	public static void contributeWebSecurityManager(Configuration<Realm> configuration) {
		// Realm básico
//		ExtendedPropertiesRealm realm = new ExtendedPropertiesRealm("classpath:shiro-users.properties");
		
		// Realm con «salted password hashing» y «salt»
		Realm realm = new io.github.picodotdev.plugintapestry.misc.Realm();

		configuration.add(realm);
	}

	public static void contributeTranslatorSource(MappedConfiguration configuration) {
		configuration.add(Date.class, new DateTranslator("yyyy-MM-dd"));
		configuration.add(LocalDateTime.class, new LocalDateTimeTranslator("yyyy-MM-dd"));
	}

	@Core
	@Contribute(JavaScriptStack.class)
	public static void contributeJavaScriptStack(OrderedConfiguration<StackExtension> configuration) {
		configuration.override("requirejs", StackExtension.library("classpath:/META-INF/resources/webjars/requirejs/2.3.5/require.js"));
		configuration.override("jquery-library", StackExtension.library("classpath:/META-INF/resources/webjars/jquery/3.3.1-1/jquery.min.js"));
		configuration.override("underscore-library", StackExtension.library("classpath:/META-INF/resources/webjars/underscore/1.9.1/underscore-min.js"));
	}

	public static void contributeCompatibility(MappedConfiguration<Trait, Boolean> configuration) {
		configuration.add(Trait.INITIALIZERS, Boolean.FALSE);
		configuration.add(Trait.SCRIPTACULOUS, Boolean.FALSE);
	}

	public static void contributeClasspathAssetAliasManager(MappedConfiguration<String, String> configuration) {
        configuration.add("webjars", "META-INF/resources/webjars");
    }

	public static void contributeBeanValidatorSource(OrderedConfiguration<BeanValidatorConfigurer> configuration) {
		configuration.add("AppConfigurer", new BeanValidatorConfigurer() {
			public void configure(javax.validation.Configuration<?> configuration) {
				configuration.ignoreXmlConfiguration();
			}
		});
	}

	public static void contributeJavaScriptStackSource(MappedConfiguration<String, JavaScriptStack> configuration) {
		configuration.addInstance("plugin", PlugInStack.class);
	}

	@Contribute(ComponentClassTransformWorker2.class)
	public static void contributeWorkers(OrderedConfiguration<ComponentClassTransformWorker2> configuration) {
		configuration.addInstance("CSRF", CsrfWorker.class);
	}

	private static boolean isJBossServer(ServletContext context) {
		if (context == null) {
			return false;
		}
		
		String si = context.getServerInfo();

		if (si.contains("WildFly") || si.contains("JBoss")) {
			return true;
		}

		return false;
	}
}
