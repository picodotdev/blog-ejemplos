package io.github.picodotdev.blogbitix.springcloud.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

@SpringBootApplication
@EnableDiscoveryClient
public class Main implements CommandLineRunner {

    @Value("${config.key}")
    String key = null;

    @Autowired
    private DiscoveryClient discoveryClient;

	@Override
	public void run(String... args) {
	    System.out.printf("Valor de propiedad de configuración (%s): %s%n", "config.key", key);
		System.out.println("Servicios:");
	    discoveryClient.getServices().forEach(service -> {	        
	        List<ServiceInstance> instances = discoveryClient.getInstances(service);
	        ServiceInstance instance = instances.get(0);
	        System.out.printf("%s (%d): %s:%d %s%n", service, instances.size(), instance.getHost(), instance.getPort(), instance.getUri());
	    });
	}

	public static void main(String[] args) throws Exception {
		SpringApplication application = new SpringApplication(Main.class);
		application.setApplicationContextClass(AnnotationConfigApplicationContext.class);
		SpringApplication.run(Main.class, args);
	}
}
