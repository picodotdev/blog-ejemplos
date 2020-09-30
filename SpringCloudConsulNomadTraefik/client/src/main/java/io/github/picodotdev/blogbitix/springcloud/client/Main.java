package io.github.picodotdev.blogbitix.springcloud.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
public class Main implements CommandLineRunner {

	@Autowired
	private DefaultConfiguration configuration;

	@Autowired
	private ProxyService proxyService;

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.printf("Valor de propiedad de configuración (%s): %s%n", "config.key", configuration.getKey());
		System.out.printf("Valor de propiedad de configuración (%s): %s%n", "config.password", configuration.getPassword());

		for (int i = 0; i < 20000; ++i) {
			String response = get();
			System.out.printf("Service response: %s%n", response);
			Thread.sleep(1000);
		}
	}

	private String get() {
		return proxyService.get();
	}

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(Main.class);
		application.setApplicationContextClass(AnnotationConfigApplicationContext.class);
		SpringApplication.run(Main.class, args);
	}
}
