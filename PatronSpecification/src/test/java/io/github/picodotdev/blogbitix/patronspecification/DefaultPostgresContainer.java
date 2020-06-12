package io.github.picodotdev.blogbitix.testcontainers;

import org.springframework.boot.test.util.TestPropertyValues;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

import org.testcontainers.containers.PostgreSQLContainer;

public class DefaultPostgresContainer extends PostgreSQLContainer<DefaultPostgresContainer> {

    private static final String IMAGE_VERSION = "postgres:12";
    private static DefaultPostgresContainer container;
 
    private DefaultPostgresContainer() {
        super(IMAGE_VERSION);
    }
 
    public static DefaultPostgresContainer getInstance() {
        if (container == null) {
            container = new DefaultPostgresContainer();
        }
        return container;
    }
 
    @Override
    public void start() {
        super.start();
    }
 
    @Override
    public void stop() {
        super.stop();
    }

    public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            DefaultPostgresContainer container = DefaultPostgresContainer.getInstance();
            container.start();
            TestPropertyValues.of(
                    "spring.datasource.url=" + container.getJdbcUrl(),
                    "spring.datasource.username=" + container.getUsername(),
                    "spring.datasource.password=" + container.getPassword()
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }
}