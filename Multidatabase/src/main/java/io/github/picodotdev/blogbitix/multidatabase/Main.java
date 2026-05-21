package io.github.picodotdev.blogbitix.multidatabase;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import io.github.picodotdev.blogbitix.multidatabase.jooq.inventory.tables.pojos.Item;
import io.github.picodotdev.blogbitix.multidatabase.jooq.inventory.tables.records.ItemRecord;
import io.github.picodotdev.blogbitix.multidatabase.jooq.purchases.tables.records.PurchaseRecord;
import io.github.picodotdev.blogbitix.multidatabase.service.InventoryService;
import io.github.picodotdev.blogbitix.multidatabase.service.PurchasesService;

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class })
public class Main implements CommandLineRunner {

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private PurchasesService purchasesService;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Insertando datos...");        
        ItemRecord item = inventoryService.create(new Item(null, LocalDateTime.now(), "Libro", "Un gran libro", 10l, new BigDecimal("7.99")));
        PurchaseRecord purchase = purchasesService.create(item, 2);        

        System.out.printf("Número productos: %d%n", inventoryService.count());
        System.out.printf("Número compras: %d%n", purchasesService.count());
    }

    public static void main(String[] args) throws Exception {
        SpringApplication application = new SpringApplication(Main.class);
        application.setApplicationContextClass(AnnotationConfigApplicationContext.class);
        SpringApplication.run(Main.class, args);
    }
}
