package io.github.picodotdev.blogbitix.springcloudconfig.configserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
