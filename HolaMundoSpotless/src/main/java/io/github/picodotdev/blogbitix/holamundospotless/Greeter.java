package io.github.picodotdev.blogbitix.holamundospotless;

import java.util.Objects;

import org.jspecify.annotations.NonNull;

public class Greeter {

    private static String DEFAULT_GREETING = "Hola";

    public String greet(@NonNull final String name) {
        Objects.requireNonNull(name, "name no puede ser null");
        return String.format("%s, %s!", DEFAULT_GREETING, name)
                     .toLowerCase();
    }
}
