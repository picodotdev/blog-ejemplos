package io.github.picodotdev.blogbitix.grpc;

import io.github.picodotdev.blogbitix.grpc.service.HelloWorldClass;
import io.github.picodotdev.blogbitix.grpc.service.HelloWorldGrpc;
import io.grpc.Channel;
import io.grpc.ManagedChannelBuilder;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class HelloWorldClient {

    private final HelloWorldGrpc.HelloWorldBlockingStub blockingStub;

    public HelloWorldClient(String host, int port) {
        this(ManagedChannelBuilder.forAddress(host, port).usePlaintext().build());
    }

    public HelloWorldClient(Channel channel) {
        blockingStub = HelloWorldGrpc.newBlockingStub(channel);
    }

    public String getHelloMessage(String name) {
        HelloWorldClass.HelloResponse response = blockingStub.helloMessage(HelloWorldClass.HelloRequest.newBuilder().setName(name).build());
        return response.getMessage();
    }

    public Stream<String> getHelloStream(String name) {
        Iterator<HelloWorldClass.HelloResponse> response = blockingStub.helloStream(HelloWorldClass.HelloRequest.newBuilder().setName(name).build());
        Spliterator<HelloWorldClass.HelloResponse> splitIterator = Spliterators.spliteratorUnknownSize(response, Spliterator.ORDERED);
        Stream<String> stream = StreamSupport.stream(splitIterator, false).map(HelloWorldClass.HelloResponse::getMessage);
        return stream;
    }
}
