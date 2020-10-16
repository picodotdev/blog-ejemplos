package io.github.picodotdev.blogbitix.eventbus;

import io.github.picodotdev.blogbitix.eventbus.application.inventory.OrderCreatedCommand;
import io.github.picodotdev.blogbitix.eventbus.application.order.CreateOrderCommand;
import io.github.picodotdev.blogbitix.eventbus.application.order.GetOrderQuery;
import io.github.picodotdev.blogbitix.eventbus.domain.inventory.Product;
import io.github.picodotdev.blogbitix.eventbus.domain.inventory.ProductRepository;
import io.github.picodotdev.blogbitix.eventbus.domain.order.Item;
import io.github.picodotdev.blogbitix.eventbus.domain.order.Order;
import io.github.picodotdev.blogbitix.eventbus.domain.order.OrderId;
import io.github.picodotdev.blogbitix.eventbus.domain.order.OrderRepository;
import io.github.picodotdev.blogbitix.eventbus.domain.order.OrderService;
import io.github.picodotdev.blogbitix.eventbus.domain.shared.commandbus.CommandBus;
import io.github.picodotdev.blogbitix.eventbus.domain.shared.querybus.QueryBus;
import io.github.picodotdev.blogbitix.eventbus.infrastructure.ConsoleEventBus;
import io.github.picodotdev.blogbitix.eventbus.infrastructure.MemoryOrderRepository;
import io.github.picodotdev.blogbitix.eventbus.infrastructure.SpringCommandBus;
import io.github.picodotdev.blogbitix.eventbus.infrastructure.SpringQueryBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;
import java.util.List;

@SpringBootApplication
public class Main implements CommandLineRunner {

    @Autowired
    private QueryBus queryBus;

    @Autowired
    private CommandBus commandBus;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public void run(String... args) throws Exception {
        Product product = productRepository.findAll().stream().findFirst().orElse(null);
        System.out.println("Stock: " + product.getStock());

        OrderId orderId = orderRepository.generateId();
        commandBus.handle(new CreateOrderCommand(orderId, List.of(new Item(product.getId(), product.getPrice(), 2, new BigDecimal("0.21")))));

        Order order = queryBus.handle(new GetOrderQuery(orderId));
        System.out.printf("OrderId: %s, Items: %s%n", orderId, order.getItems().size());

        System.out.println("Stock: " + product.getStock());
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Main.class, args);
    }
}
