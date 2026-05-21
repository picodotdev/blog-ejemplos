package io.github.picodotdev.blogbitix.log4j;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

import java.util.Arrays;

public class Main {

    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        Main.logReplace();
        Main.changeLogLevelDynamically();
    }

    private static void logReplace() {
        Product product = new Product(1l, "Intel NUC", "Negro");

        logger.info("Product({}, {})", product.getId(), product.getName());
        logger.info(product);
        logger.info(new SimpleProductMessage(product));
        logger.info(new ProductMessage(product));
        logger.info(new SecuredMessage("Tarjeta de crédito: 1111 1111 1111 1111, DNI: 11111111A", Arrays.asList("(\\d{4} \\d{4} \\d{4} \\d{1})(?=\\d{3})", "(\\d{6})(?=\\d{2}[A-Z])")));
        logger.info("Tarjeta de crédito: 1111 1111 1111 1111, DNI: 11111111A");
    }

    private static void changeLogLevelDynamically() {
        Configurator.setLevel(logger.getName(), Level.ERROR);
        logger.info("info trace");
        logger.error("error trace");

        logger.error("");

        Configurator.setLevel(logger.getName(), Level.INFO);
        logger.info("info trace");
        logger.error("error trace");
    }
}
