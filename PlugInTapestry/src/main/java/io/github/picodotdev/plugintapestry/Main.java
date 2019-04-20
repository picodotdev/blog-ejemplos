package io.github.picodotdev.plugintapestry;

import io.github.picodotdev.plugintapestry.spring.AppConfiguration;
import org.apache.catalina.Context;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.embedded.tomcat.TomcatContextCustomizer;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

@SpringBootApplication
public class Main extends SpringBootServletInitializer implements CommandLineRunner {

	private static final Logger logger = LogManager.getLogger(Main.class);

	@Override
	public void run(String... args) {
		StringBuilder banner = new StringBuilder();
		banner.append("   ___  __          ____   ______                  __          \n");
		banner.append("  / _ \\/ /_ _____ _/  _/__/_  __/__ ____  ___ ___ / /_______ __\n");
		banner.append(" / ___/ / // / _ `// // _ \\/ / / _ `/ _ \\/ -_|_-</ __/ __/ // /\n");
		banner.append("/_/  /_/\\_,_/\\_, /___/_//_/_/  \\_,_/ .__/\\__/___/\\__/_/  \\_, / \n");
		banner.append("            /___/                 /_/                   /___/");
		logger.info("\n" + banner.toString());
		logger.info("Application running");
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
