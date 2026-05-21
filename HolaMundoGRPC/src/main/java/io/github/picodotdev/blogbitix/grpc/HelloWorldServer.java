package io.github.picodotdev.blogbitix.grpc;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class HelloWorldServer {

    private int port;
    private Server server;

    public HelloWorldServer(int port) {
        this.port = port;
        this.server = ServerBuilder.forPort(port).addService(new HelloWorldService()).build();
    }

    public void start() throws IOException {
        server.start();
        addShutdownHook();
        System.out.printf("Server started, listening on %d%n", port);
    }

    public void stop() throws InterruptedException {
        if (server != null) {
            server.shutdown().awaitTermination(30, TimeUnit.SECONDS);
        }
    }

    public void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    private void addShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                try {
                    HelloWorldServer.this.stop();
                } catch (InterruptedException e) {
                    e.printStackTrace(System.err);
                }
            }
        });
    }
}
