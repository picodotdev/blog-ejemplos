package io.github.picodotdev.blogbitix.javalogging;

import java.io.InputStream;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.LogManager;

public class Main {

    public static void main(String[] args) throws Exception {
        if (Arrays.asList(args).contains("--config")) {
            InputStream configuration = Main.class.getResourceAsStream("/jul.properties");
            LogManager.getLogManager().readConfiguration(configuration);
        }
        Logger logger = Logger.getLogger(Main.class.getName());

        logger.log(Level.FINE, "Hello World!");
        logger.log(Level.INFO, "Hello World!");
        logger.log(Level.WARNING, "Hello World!");
        logger.log(Level.SEVERE, "Hello World!", new Exception());
    }
}