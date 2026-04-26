package io.github.picodotdev.blogbitix.zalandologbook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
public class Main {

    static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
