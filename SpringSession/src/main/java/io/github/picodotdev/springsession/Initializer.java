package io.github.picodotdev.springsession;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.session.web.context.AbstractHttpSessionApplicationInitializer;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class Initializer extends AbstractHttpSessionApplicationInitializer implements WebApplicationInitializer {

    public Initializer() {
        super(Config.class); 
    }
	
	public void onStartup(ServletContext servletContext) throws ServletException {
		super.onStartup(servletContext);
		
		AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
		ctx.register(Config.class);
		ctx.setServletContext(servletContext);

		Dynamic servlet = servletContext.addServlet("dispatcher", new DispatcherServlet(ctx));
		servlet.addMapping("/");
		servlet.setLoadOnStartup(1);
	}
}