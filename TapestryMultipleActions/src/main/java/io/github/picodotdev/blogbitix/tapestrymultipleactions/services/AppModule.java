package io.github.picodotdev.blogbitix.tapestrymultipleactions.services;

import io.github.picodotdev.blogbitix.tapestrymultipleactions.entities.Product;
import io.github.picodotdev.blogbitix.tapestrymultipleactions.misc.ProductEncoder;
import io.github.picodotdev.blogbitix.tapestrymultipleactions.misc.ProductTranslator;
import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.beanvalidator.BeanValidatorConfigurer;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.services.TypeCoercer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class AppModule {

    private static final Logger logger = LoggerFactory.getLogger(AppModule.class);

    public static void bind(ServiceBinder binder) {
        binder.bind(ProductRepository.class, InMemoryProductRepository.class);
        binder.bind(AppService.class, AppServiceImpl.class);
    }

    public static void contributeApplicationDefaults(MappedConfiguration<String, Object> configuration) {
        configuration.add(SymbolConstants.HMAC_PASSPHRASE, UUID.randomUUID().toString());

        configuration.add(SymbolConstants.COMPRESS_WHITESPACE, true);
        configuration.add(SymbolConstants.PRODUCTION_MODE, false);
        configuration.add(SymbolConstants.SUPPORTED_LOCALES, "es,en");

        configuration.add(SymbolConstants.APPLICATION_VERSION, "1.0");

        configuration.add(SymbolConstants.JAVASCRIPT_INFRASTRUCTURE_PROVIDER, "jquery");
        configuration.add(SymbolConstants.ENABLE_PAGELOADING_MASK, false);
    }

    public static void contributeBeanValidatorSource(OrderedConfiguration<BeanValidatorConfigurer> configuration) {
        configuration.add("AppConfigurer", new BeanValidatorConfigurer() {
            public void configure(javax.validation.Configuration<?> configuration) {
                configuration.ignoreXmlConfiguration();
            }
        });
    }

    public static void contributeValueEncoderSource(MappedConfiguration configuration, ProductRepository repository, TypeCoercer coercer) {
        configuration.add(Product.class, new ProductEncoder(repository, coercer));
    }

    public static void contributeTranslatorSource(MappedConfiguration configuration, ProductRepository repository, TypeCoercer coercer) {
        configuration.add(Product.class, new ProductTranslator(repository, coercer));
    }
}
