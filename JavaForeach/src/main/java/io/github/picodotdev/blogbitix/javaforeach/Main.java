package io.github.picodotdev.blogbitix.javaforeach;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.stream.IntStream;

public class Main {

    private static Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        logger.info("for i");
        for (int i = 0; i < 5; i++) {
            logger.info("{}", i);
        }

        logger.info("foreach");
        for (int i : Arrays.asList(0, 1, 2, 3, 4)) {
            logger.info("{}", i);
        }

        logger.info("for counter");
        for (int i : new Counter(0, 5)) {
            logger.info("{}", i);
        }

        logger.info("stream foreach");
        IntStream.range(0, 5).forEach(i -> {
            logger.info("{}", i);
        });
    }
}
