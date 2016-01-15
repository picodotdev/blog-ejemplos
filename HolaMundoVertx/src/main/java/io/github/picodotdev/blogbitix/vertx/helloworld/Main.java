package io.github.picodotdev.blogbitix.vertx.helloworld;

import io.vertx.core.Vertx;

public class Main {

    public static void main(String[] args) {
        Vertx.vertx().createHttpServer().requestHandler(req -> req.response().end("Hello World!")).listen(8080, handler -> {
            if (handler.succeeded()) {
                System.out.println("Application starter, listening on http://localhost:8080/");
            } else {
                System.err.println("Failed to start application");
            }
        });
    }
}
