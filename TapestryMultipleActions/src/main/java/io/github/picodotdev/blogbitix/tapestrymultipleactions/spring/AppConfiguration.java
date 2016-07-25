package io.github.picodotdev.blogbitix.tapestrymultipleactions.spring;

import org.apache.tapestry5.spring.TapestrySpringFilter;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.ErrorPage;
import org.springframework.boot.context.embedded.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

import javax.servlet.DispatcherType;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.SessionTrackingMode;
import java.util.EnumSet;

@Configuration
@ComponentScan({ "io.github.picodotdev.blogbitix.tapestrymultipleactions" })
public class AppConfiguration {

    @Bean
    public ServletContextInitializer initializer() {
        return new ServletContextInitializer() {
            @Override
            public void onStartup(ServletContext servletContext) throws ServletException {
                servletContext.setInitParameter("tapestry.app-package", "io.github.picodotdev.blogbitix.tapestrymultipleactions");
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
}
