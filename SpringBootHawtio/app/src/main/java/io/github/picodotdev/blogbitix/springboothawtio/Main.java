package io.github.picodotdev.blogbitix.springboothawtio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableMBeanExport;

@SpringBootApplication
@EnableMBeanExport
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
