package io.github.picodotdev.blogbitix.cache;

import java.time.LocalDateTime;
import java.util.UUID;

import static spark.Spark.get;

public class Main {

    public static void main(String[] args) {
        get("/nocache", (request, response) -> {
            return getMessage();
        });
        get("/cache", (request, response) -> {
            response.header("Cache-Control", "max-age=60");
            response.header("Etag", UUID.randomUUID().toString());
            return getMessage();
        });
    }

    private static String getMessage() {
        return String.format("¡Hola mundo! (%s)", LocalDateTime.now().toString());
    }
}
