package io.github.picodotdev.blogbitix.springcloud.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
@EnableDiscoveryClient
@EnableCircuitBreaker
@EnableHystrixDashboard
public class Main implements CommandLineRunner {

	@Autowired
	private DefaultConfiguration configuration;

	@Autowired
	private ClientService service;
    
	@Override
	public void run(String... args) throws Exception {
		System.out.printf("Valor de propiedad de configuración (%s): %s%n", "config.key", configuration.getKey());
		System.out.printf("Valor de propiedad de configuración (%s): %s%n", "config.password", configuration.getPassword());

		for (int i = 0; i < 20000; ++i) {
			String response = service.get();
			System.out.printf("Service response: %s%n", response);
			Thread.sleep(100);
		}
	}

	public static void main(String[] args) throws Exception {
		SpringApplication application = new SpringApplication(Main.class);
		application.setApplicationContextClass(AnnotationConfigApplicationContext.class);
		SpringApplication.run(Main.class, args);
	}
}
