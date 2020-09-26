package io.github.picodotdev.blogbitix.grpc;

public class Main {

    public static void main(String[] args) throws Exception {
        startServer();

        Thread.sleep(2000);

        startClient();

        System.exit(0);
    }

    private static void startServer() throws Exception {
        HelloWorldServer server = new HelloWorldServer(8980);
        server.start();
    }

    private static void startClient() {
        HelloWorldClient client = new HelloWorldClient("localhost", 8980);
        System.out.println(client.getHelloMessage("gRPC"));
        client.getHelloStream("gRPC").forEach(message -> {
            System.out.println(message);
        });
    }
}
