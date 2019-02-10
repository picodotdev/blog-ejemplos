package io.github.picodotdev.blogbitix.log4j;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;

public class Main {

    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        Product product = new Product(1l, "Intel NUC", "Negro");

        logger.info("Product({}, {})", product.getId(), product.getName());
        logger.info(product);
        logger.info(new SimpleProductMessage(product));
        logger.info(new ProductMessage(product));
        logger.info(new SecuredMessage("Tarjeta de crédito: 1111 1111 1111 1111, DNI: 11111111A", Arrays.asList("(\\d{4} \\d{4} \\d{4} \\d{1})(?=\\d{3})", "(\\d{6})(?=\\d{2}[A-Z])")));
        logger.info("Tarjeta de crédito: 1111 1111 1111 1111, DNI: 11111111A");
    }
}
