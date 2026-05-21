package io.github.picodotdev.blogbitix.javapact;

import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Main {

    @Autowired
    private ServletWebServerApplicationContext webServerContext;

    @Bean
    OkHttpClient buildOkHttpClient() {
        return new OkHttpClient.Builder().build();
    }

    @Bean
    Service buildService(OkHttpClient client) {
        return new ServiceClient(client, String.format("http://localhost:%s/", webServerContext.getWebServer().getPort()));
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class);
    }
}
