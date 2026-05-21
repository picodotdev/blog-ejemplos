package io.github.picodotdev.blogbitix.asyncapicloudevents;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
