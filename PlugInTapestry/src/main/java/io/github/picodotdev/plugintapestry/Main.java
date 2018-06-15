package io.github.picodotdev.plugintapestry;

import io.github.picodotdev.plugintapestry.spring.AppConfiguration;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

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
