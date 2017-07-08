package io.github.picodotdev.blogbitix.tapestry.portlet.services;

import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.ioc.MappedConfiguration;

public class AppPortletModule {

	public static void contributeApplicationDefaults(MappedConfiguration<String, Object> configuration) {
		configuration.add(SymbolConstants.ENABLE_PAGELOADING_MASK, false);
		configuration.add(SymbolConstants.JAVASCRIPT_INFRASTRUCTURE_PROVIDER, "jquery");
		configuration.add(SymbolConstants.BOOTSTRAP_ROOT, "classpath:/META-INF/assets");
		configuration.add(SymbolConstants.HMAC_PASSPHRASE, "A8c7qS59h46J8nA");
	}
}
