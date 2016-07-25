package io.github.picodotdev.blogbitix.tapestrymultipleactions;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import io.github.picodotdev.blogbitix.tapestrymultipleactions.spring.AppConfiguration;

@SpringBootApplication
public class Main extends SpringBootServletInitializer implements CommandLineRunner {

	@Override
	public void run(String... args) {
 	}
	
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(AppConfiguration.class);
    }

	public static void main(String[] args) throws Exception {
		SpringApplication application = new SpringApplication(Main.class);
		application.setApplicationContextClass(AnnotationConfigWebApplicationContext.class);
		SpringApplication.run(Main.class, args);
	}
}
