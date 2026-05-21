package io.github.picodotdev.blogbitix.javalogging;

import org.apache.logging.log4j.LogManager;

public class Log4j2Logger implements Logger {

    private final org.apache.logging.log4j.Logger logger;

    private Log4j2Logger(Class clazz) {
        this.logger = LogManager.getLogger(clazz);
    }

    public static Logger getLogger(Class clazz) {
        return new Log4j2Logger(clazz);
    }

    @Override
    public void trace(String message) {
        trace(message, new Object[0]);
    }

    @Override
    public void trace(String message, Object... params) {
        logger.trace(adapt(message), params);
    }

    @Override
    public void info(String message) {
        info(message, new Object[0]);
    }

    @Override
    public void info(String message, Object... params) {
        logger.info(adapt(message), params);
    }

    @Override
    public void warn(String message) {
        info(message, new Object[0]);
    }

    @Override
    public void warn(String message, Object... params) {
        logger.warn(adapt(message), params);
    }

    @Override
    public void error(String message) {
        error(message, new Object[0]);
    }

    @Override
    public void error(String message, Object... params) {
        logger.error(adapt(message), params);
    }

    @Override
    public void error(Throwable error) {
        logger.error(error.getMessage(), error);
    }

    private String adapt(String message) {
        return message;
    }
}
