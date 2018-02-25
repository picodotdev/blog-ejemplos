package io.github.picodotdev.blogbitix.javaconcurrency.philosophers2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

    private static Logger logger = LoggerFactory.getLogger(io.github.picodotdev.blogbitix.javaconcurrency.philosophers.Main.class);

    public static void main(String[] args) throws Exception {
        logger.info("Setuping dinner...");
        Table table = new Table(5);
        Thread dinner = new Thread(table);

        logger.info("Starting dinner...");
        dinner.start();
        dinner.join();
    }
}
