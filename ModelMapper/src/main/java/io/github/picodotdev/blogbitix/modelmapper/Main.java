package io.github.picodotdev.blogbitix.modelmapper;

import io.github.picodotdev.blogbitix.modelmapper.classes.Address;
import io.github.picodotdev.blogbitix.modelmapper.classes.Customer;
import io.github.picodotdev.blogbitix.modelmapper.classes.Order;
import io.github.picodotdev.blogbitix.modelmapper.classes.OrderDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Main implements CommandLineRunner {

	@Autowired
	private ModelMapper modelMapper;

	@Bean
	ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Override
	public void run(String... args) throws Exception {
		Customer customer = new Customer("Francisco", "Ibáñez");
		Address billigAddress = new Address("c\\ Rue del Percebe, 13", "Madrid");
		Order order = new Order(customer, billigAddress);

		OrderDTO orderDTO = modelMapper.map(order, OrderDTO.class);

		System.out.printf("Customer First Name: %s%n", orderDTO.getCustomerFirstName());
		System.out.printf("Customer Last Name: %s%n", orderDTO.getCustomerLastName());
		System.out.printf("Billing Address Street: %s%n", orderDTO.getBillingAddressStreet());
		System.out.printf("Billing Address City: %s%n", orderDTO.getBillingAddressCity());
	}

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}
}
