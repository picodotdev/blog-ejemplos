package io.github.picodotdev.blogbitix.springcloud.client;

import java.util.List;
import java.net.URI;

import com.sun.jersey.api.client.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
@EnableDiscoveryClient
public class Main implements CommandLineRunner {

    @Value("${config.key}")
    String key;

    @Value("${config.password}")
    String password;
    
	@Autowired
	private LoadBalancerClient loadBalancer;
    
	@Override
	public void run(String... args) throws Exception {
		System.out.printf("Valor de propiedad de configuración (%s): %s%n", "config.key", key);
		System.out.printf("Valor de propiedad de configuración (%s): %s%n", "config.password", password);

		for (int i = 0; i < 20; ++i) {
			ServiceInstance instance = loadBalancer.choose("service");
			URI uri = instance.getUri();
			String response = Client.create().resource(uri).get(String.class);

			System.out.printf("Service URI: %s%n", uri);
			System.out.printf("Service response: %s%n", response);
			Thread.sleep(1000);
		}
	}

	public static void main(String[] args) throws Exception {
		SpringApplication application = new SpringApplication(Main.class);
		application.setApplicationContextClass(AnnotationConfigApplicationContext.class);
		SpringApplication.run(Main.class, args);
	}
}
