package io.github.picodotdev.blogbitix.holamundoapachecamel;

import java.util.UUID;
import java.util.stream.IntStream;

import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main implements ApplicationRunner {

    @Autowired
    private ProducerTemplate producerTemplate;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        IntStream.range(0, 10).forEach(i -> {
            producerTemplate.sendBody("direct:helloworld", UUID.randomUUID());
        });
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
