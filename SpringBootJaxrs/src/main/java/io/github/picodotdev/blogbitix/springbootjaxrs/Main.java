package io.github.picodotdev.blogbitix.springbootjaxrs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Main {

	@Bean
	MessageService buillMessageService() {
		return new DefaultMessageService();
	}

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}
}
