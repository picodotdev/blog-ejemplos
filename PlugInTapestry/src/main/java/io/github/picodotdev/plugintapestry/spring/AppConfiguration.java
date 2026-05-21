package io.github.picodotdev.plugintapestry.spring;

import io.github.picodotdev.plugintapestry.misc.AppFilter;
import io.github.picodotdev.plugintapestry.misc.JooqExecuteListener;
import io.github.picodotdev.plugintapestry.services.dao.DefaultHibernateProductoDAO;
import io.github.picodotdev.plugintapestry.services.dao.DefaultJooqProductoDAO;
import io.github.picodotdev.plugintapestry.services.dao.HibernateProductoDAO;
import io.github.picodotdev.plugintapestry.services.dao.JooqProductoDAO;
import io.github.picodotdev.plugintapestry.services.hibernate.ProductoEventAdapter;
import io.github.picodotdev.plugintapestry.services.spring.DummyService;
import org.apache.catalina.connector.Connector;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.coyote.http11.Http11NioProtocol;
import org.apache.tapestry5.spring.TapestrySpringFilter;
import org.h2.Driver;
import org.hibernate.SessionFactory;
import org.hibernate.dialect.H2Dialect;
import org.jooq.ConnectionProvider;
import org.jooq.DSLContext;
import org.jooq.ExecuteContext;
import org.jooq.ExecuteListener;
import org.jooq.ExecuteListenerProvider;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.jooq.impl.DataSourceConnectionProvider;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.DefaultExecuteListener;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.servlet.DispatcherType;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.SessionTrackingMode;
import javax.sql.DataSource;
import java.time.Duration;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

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
        m.put("hibernate.hbm2ddl.auto", "validate");
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
    public ExecuteListenerProvider executeListenerProvider() {
        return new ExecuteListenerProvider() {
            @Override
            public ExecuteListener provide() {
                return new JooqExecuteListener();
            }
        };
    }

    @Bean
    public org.jooq.Configuration config(ConnectionProvider connectionProvider, ExecuteListenerProvider executeListenerProvider) {
        DefaultConfiguration config = new DefaultConfiguration();
        config.set(connectionProvider);
        config.set(SQLDialect.H2);
        config.set(executeListenerProvider);
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
                servletContext.addFilter("filter", AppFilter.class).addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST, DispatcherType.ERROR), false, "/*");
                servletContext.addFilter("app", TapestrySpringFilter.class).addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST, DispatcherType.ERROR), false, "/*");
                servletContext.setSessionTrackingModes(EnumSet.of(SessionTrackingMode.COOKIE));
            }
        };
    }

    // Tomcat
    @Bean
    public ConfigurableServletWebServerFactory webServerFactory() {
        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
        Http11NioProtocol protocol = (Http11NioProtocol) connector.getProtocolHandler();
        connector.setScheme("http");
        connector.setSecure(false);
        connector.setPort(8080);

        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
        factory.addAdditionalTomcatConnectors(connector);
        factory.getSession().setTimeout(Duration.ofMinutes(10));
        factory.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/error404"));
        factory.addErrorPages(new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error500"));
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
