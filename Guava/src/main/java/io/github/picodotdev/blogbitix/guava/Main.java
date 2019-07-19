package io.github.picodotdev.blogbitix.guava;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import com.google.common.eventbus.EventBus;

@SpringBootApplication
public class Main {

	@Bean
	public EventBus eventBus() {
		EventBus eventbus = new EventBus();
		eventbus.register(new EventListener());
		return eventbus;
	}

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}
}
