package io.github.picodotdev.blogbitix.springcloudconfig.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main implements CommandLineRunner {

    @Value("${app.properties.classpath}")
    private String classpath;

    @Value("${app.properties.external}")
    private String external;

    @Value("${app.properties.argument}")
    private String argument;

    @Value("${app.properties.environment}")
    private String environment;

    @Value("${app.properties.cloud}")
    private String cloud;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Application classpath property: " + classpath);
        System.out.println("Application external property: " + external);
        System.out.println("Application argument property: " + argument);
        System.out.println("Application environment property: " + environment);
        System.out.println("Application cloud property: " + cloud);
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
