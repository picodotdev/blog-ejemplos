package io.github.picodotdev.blogbitix.javalogging;

public interface Logger {

    void trace(String message);

    void trace(String message, Object... params);

    void info(String message);

    void info(String message, Object... params);

    void warn(String message);

    void warn(String message, Object... params);

    void error(String message);

    void error(String message, Object... params);

    void error(Throwable error);
}
