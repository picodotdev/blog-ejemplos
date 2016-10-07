package io.github.picodotdev.keycloak.services;

import org.apache.shiro.realm.Realm;
import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.services.HttpServletRequestFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tynamo.security.SecuritySymbols;

import java.util.UUID;

public class AppModule {

	private static final Logger logger = LoggerFactory.getLogger(AppModule.class);

	public static void bind(ServiceBinder binder) {
		// binder.bind(Sevicio.class, ServicioImpl.class);
	}

	public static void contributeApplicationDefaults(MappedConfiguration<String, Object> configuration) {
		configuration.add(SymbolConstants.HMAC_PASSPHRASE, UUID.randomUUID().toString());

		configuration.add(SymbolConstants.PRODUCTION_MODE, false);
		configuration.add(SymbolConstants.SUPPORTED_LOCALES, "es");

		configuration.add(SymbolConstants.SECURE_ENABLED, true);
		configuration.add(SymbolConstants.HOSTPORT, 8080);
		configuration.add(SymbolConstants.HOSTPORT_SECURE, 8443);
		configuration.add(SymbolConstants.ENABLE_PAGELOADING_MASK, false);

		configuration.add(SymbolConstants.JAVASCRIPT_INFRASTRUCTURE_PROVIDER, "jquery");

		configuration.add(SymbolConstants.APPLICATION_VERSION, "1.0");

		configuration.add(SecuritySymbols.UNAUTHORIZED_URL, "/error401");
	}

	public static void contributeHttpServletRequestHandler(OrderedConfiguration<HttpServletRequestFilter> configuration) {
		configuration.addInstance("Keycloak", KeycloakFilter.class, "after:SecurityConfiguration");
	}

//	public static void contributeSecurityConfiguration(Configuration<SecurityFilterChain> configuration, SecurityFilterChainFactory factory) {
//	}

	public static void contributeWebSecurityManager(Configuration<Realm> configuration) {
		configuration.add(new AppRealm());
	}
}
