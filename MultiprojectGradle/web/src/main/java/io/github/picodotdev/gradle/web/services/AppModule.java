package io.github.picodotdev.gradle.web.services;

import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.beanvalidator.BeanValidatorConfigurer;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.OrderedConfiguration;

public class AppModule {

	public static void contributeApplicationDefaults(MappedConfiguration<String, String> configuration) {
		configuration.add(SymbolConstants.PRODUCTION_MODE, "false");
	}
	
    public static void contributeBeanValidatorSource(OrderedConfiguration<BeanValidatorConfigurer> configuration) {
        configuration.add("AppConfigurer", new BeanValidatorConfigurer() {
            public void configure(javax.validation.Configuration<?> configuration) {
                configuration.ignoreXmlConfiguration();
            }
        });
    }
}
