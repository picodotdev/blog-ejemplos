package io.github.picodotdev.blogbitix.vertx.helloworld;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Verticle;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;

public class Server extends AbstractVerticle {

    public static void main(String[] args) {
        Verticle verticle = new Server();
        Vertx.vertx().deployVerticle(verticle);
    }

    @Override
    public void start() throws Exception {
        Router router = Router.router(vertx);

        router.route().handler(routingContext -> {
            routingContext.response().putHeader("content-type", "text/html").end("Hello World!");
        });

        vertx.createHttpServer().requestHandler(router::accept).listen(8080);
    }
}
