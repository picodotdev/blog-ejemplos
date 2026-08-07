package io.github.picodotdev.blogbitix.drools;

import io.micrometer.observation.annotation.Observed;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main implements CommandLineRunner {

    static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Override
    @Observed
    public void run(String... args) {
        System.out.println("Application started");
    }
}
