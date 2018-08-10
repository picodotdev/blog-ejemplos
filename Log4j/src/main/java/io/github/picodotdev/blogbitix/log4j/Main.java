package io.github.picodotdev.blogbitix.log4j;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {

    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        Product product = new Product(1l, "Intel NUC", "Negro");

        logger.info("Product({}, {})", product.getId(), product.getName());
        logger.info(product);
        logger.info(new SimpleProductMessage(product));
        logger.info(new ProductMessage(product));
    }
}
