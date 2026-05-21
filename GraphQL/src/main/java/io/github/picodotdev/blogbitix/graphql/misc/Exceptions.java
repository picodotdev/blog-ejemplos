package io.github.picodotdev.blogbitix.graphql.misc;

public class Exceptions {

    public static RuntimeException toRuntimeException(Exception exception) {
        return new RuntimeException(exception);
    }
}
