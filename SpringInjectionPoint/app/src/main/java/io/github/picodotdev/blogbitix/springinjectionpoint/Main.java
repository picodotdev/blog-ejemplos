package io.github.picodotdev.blogbitix.springinjectionpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class Main implements CommandLineRunner {

    @Autowired
    private Service service;

    @Override
    public void run(String... args) throws Exception {
        service.echo();
    }

    public static void main(String[] args) {
        new SpringApplicationBuilder(Main.class)
                .listeners(new DefaultApplicationListener())
                .application()
                .run();
    }
}
