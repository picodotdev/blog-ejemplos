package io.github.picodotdev.blogbitix.springinjectionpoint;

import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class Service {

    private Logger logger;

    public Service(Logger logger) {
        this.logger = logger;
    }

    public void echo() {
        logger.info("Hello World!");
    }
}
