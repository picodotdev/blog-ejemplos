package io.github.picodotdev.blogbitix.springboot.spring;

import java.io.IOException;
import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.SessionTrackingMode;
import javax.sql.DataSource;
import javax.validation.Validator;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;
import org.apache.catalina.valves.ValveBase;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.tapestry5.spring.TapestrySpringFilter;
import org.jooq.ConnectionProvider;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.jooq.impl.DataSourceConnectionProvider;
import org.jooq.impl.DefaultConfiguration;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.ErrorPage;
import org.springframework.boot.context.embedded.ServletContextInitializer;
import org.springframework.boot.context.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatContextCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.ResourceTransactionManager;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import io.github.picodotdev.blogbitix.springboot.service.AppService;
import io.github.picodotdev.blogbitix.springboot.service.AppServiceImpl;
import io.github.picodotdev.blogbitix.springboot.validator.EmployeeValidator;

@Configuration
@ComponentScan({ "io.github.picodotdev.blogbitix.jooq" })
@EnableTransactionManagement
public class AppConfiguration {

    @Bean(destroyMethod = "close")
    public DataSource dataSource() {
        BasicDataSource ds = new BasicDataSource();
        // ds.setDriverClassName("org.postgresql.Driver");
        // ds.setUrl("jdbc:postgresql://localhost:5432/app");
        // ds.setUsername("sa");
        // ds.setPassword("sa");
        // ds.setDriverClassName("com.mysql.jdbc.Driver");
        // ds.setUrl("jdbc:mysql://localhost:3306/app");
        // ds.setUsername("root");
        // ds.setPassword("");
        ds.setDriverClassName("org.h2.Driver");
        ds.setUrl("jdbc:h2:./misc/database/app");
        ds.setUsername("sa");
        ds.setPassword("sa");
        return ds;
    }

    @Bean
    public ResourceTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public ConnectionProvider connectionProvider(DataSource dataSource) {
        return new DataSourceConnectionProvider(dataSource);
    }

    @Bean
    public org.jooq.Configuration config(ConnectionProvider connectionProvider) {
        DefaultConfiguration config = new DefaultConfiguration();
        config.set(connectionProvider);
        // config.set(SQLDialect.POSTGRES_9_4);
        // config.set(SQLDialect.MYSQL);
        config.set(SQLDialect.H2);
        return config;
    }

    @Bean
    public Validator validator() {
        return new LocalValidatorFactoryBean();
    }

    @Bean
    public DSLContext dsl(org.jooq.Configuration config) {
        return DSL.using(config);
    }

    @Bean
    public ServletContextInitializer initializer() {
        return new ServletContextInitializer() {
            @Override
            public void onStartup(ServletContext servletContext) throws ServletException {
                servletContext.setInitParameter("tapestry.app-package", "io.github.picodotdev.blogbitix.springboot.tapestry");
                servletContext.setInitParameter("tapestry.use-external-spring-context", "true");
                servletContext.addFilter("app", TapestrySpringFilter.class).addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST, DispatcherType.ERROR), false, "/*");
                servletContext.setSessionTrackingModes(EnumSet.of(SessionTrackingMode.COOKIE));
            }
        };
    }

    @Bean
    public EmbeddedServletContainerCustomizer containerCustomizer() {
        return new EmbeddedServletContainerCustomizer() {
            @Override
            public void customize(ConfigurableEmbeddedServletContainer container) {
                ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/error404");
                ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error500");
                container.addErrorPages(error404Page, error500Page);
            }
        };
    }

    @Bean
    public TomcatConnectorCustomizer connectorCustomizer() {
        return new TomcatConnectorCustomizer() {
            @Override
            public void customize(Connector connector) {
            }
        };
    }

    @Bean
    public TomcatContextCustomizer contextCustomizer() {
        return new TomcatContextCustomizer() {
            @Override
            public void customize(Context context) {
            }
        };
    }

    @Bean
    public TomcatEmbeddedServletContainerFactory containerFactory() {
        TomcatEmbeddedServletContainerFactory factory = new TomcatEmbeddedServletContainerFactory();
        factory.addContextValves(new ValveBase() {
            @Override
            public void invoke(Request request, Response response) throws IOException, ServletException {
                getNext().invoke(request, response);
            }
        });
        return factory;
    }

    @Bean
    public AppService appService(DSLContext context, Validator validator, EmployeeValidator employeeValidator) {
        return new AppServiceImpl(context, validator, employeeValidator);
    }
}
