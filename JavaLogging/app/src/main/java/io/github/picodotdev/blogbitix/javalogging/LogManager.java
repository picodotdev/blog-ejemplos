package io.github.picodotdev.blogbitix.javalogging;

public class LogManager {

    private static LoggerSupplier supplier;

    public static void configure(LoggerSupplier supplier) {
        LogManager.supplier = supplier;
    }

    public static Logger getLogger(Class clazz) {
        return supplier.get(clazz);
    }

    public interface LoggerSupplier {

        Logger get(Class clazz);
    }
}
