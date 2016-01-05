package io.github.picodotdev.plugintapestry.spring;

import java.io.IOException;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.DispatcherType;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.SessionTrackingMode;
import javax.sql.DataSource;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;
import org.apache.catalina.valves.ValveBase;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.tapestry5.spring.TapestrySpringFilter;
import org.h2.Driver;
import org.hibernate.SessionFactory;
import org.hibernate.dialect.H2Dialect;
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
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import io.github.picodotdev.plugintapestry.services.dao.DefaultHibernateProductoDAO;
import io.github.picodotdev.plugintapestry.services.dao.DefaultJooqProductoDAO;
import io.github.picodotdev.plugintapestry.services.dao.HibernateProductoDAO;
import io.github.picodotdev.plugintapestry.services.dao.JooqProductoDAO;
import io.github.picodotdev.plugintapestry.services.hibernate.ProductoEventAdapter;
import io.github.picodotdev.plugintapestry.services.spring.DummyService;

@Configuration
@ComponentScan({ "io.github.picodotdev.plugintapestry" })
@EnableTransactionManagement
public class AppConfiguration {

    @Bean(destroyMethod = "close")
    public DataSource dataSource() {
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName(Driver.class.getCanonicalName());
        ds.setUrl("jdbc:h2:./misc/database/app");
        ds.setUsername("sa");
        ds.setPassword("sa");
        return ds;
    }

    // Hibernate
    @Bean(name = "sessionFactory")
    public LocalSessionFactoryBean sessionFactoryBean(DataSource dataSource) {
        Map<String, Object> m = new HashMap<>();
        m.put("hibernate.dialect", H2Dialect.class.getCanonicalName());
        m.put("hibernate.hbm2ddl.auto", "create");
        // Debug
        m.put("hibernate.generate_statistics", true);
        m.put("hibernate.show_sql", true);

        Properties properties = new Properties();
        properties.putAll(m);

        //
        LocalSessionFactoryBean sf = new LocalSessionFactoryBean();
        sf.setDataSource(dataSource);
        sf.setPackagesToScan("io.github.picodotdev.plugintapestry.entities");
        sf.setHibernateProperties(properties);
        return sf;
    }

    // jOOQ
    @Bean
    public ConnectionProvider connectionProvider(DataSource dataSource) {
        return new DataSourceConnectionProvider(dataSource);
    }

    @Bean
    public org.jooq.Configuration config(ConnectionProvider connectionProvider) {
        DefaultConfiguration config = new DefaultConfiguration();
        config.set(connectionProvider);
        config.set(SQLDialect.H2);
        return config;
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
                servletContext.setInitParameter("tapestry.app-package", "io.github.picodotdev.plugintapestry");
                servletContext.setInitParameter("tapestry.use-external-spring-context", "true");
                servletContext.addFilter("app", TapestrySpringFilter.class).addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST, DispatcherType.ERROR), false, "/*");
                servletContext.setSessionTrackingModes(EnumSet.of(SessionTrackingMode.COOKIE));
            }
        };
    }

    // Tomcat
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
        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
        connector.setScheme("http");
        connector.setPort(8080);
        
        TomcatEmbeddedServletContainerFactory factory = new TomcatEmbeddedServletContainerFactory();
        factory.addAdditionalTomcatConnectors(connector);
        factory.addContextValves(new ValveBase() {
            @Override
            public void invoke(Request request, Response response) throws IOException, ServletException {
                getNext().invoke(request, response);
            }
        });
        return factory;
    }

    // Servicios
    @Bean
    public ProductoEventAdapter productoEventAdapter() {
        return new ProductoEventAdapter();
    }
    
    @Bean
    public HibernateProductoDAO hibenateProductoDAO(SessionFactory sessionFactory) {
        return new DefaultHibernateProductoDAO(sessionFactory);
    }
    
    @Bean
    public JooqProductoDAO jooqProductoDAO(DSLContext context) {
        return new DefaultJooqProductoDAO(context);
    }

    @Bean
    public DummyService dummyService() {
        return new DummyService();
    }
}
