package io.github.picodotdev.blogbitix.java8.helloworld;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class Main {

    private static Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        logger.info("Arguments: {}", Arrays.asList(args).stream().collect(Collectors.joining(", ")));
    }
}
