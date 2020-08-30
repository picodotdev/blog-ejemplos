package io.github.picodotdev.blogbitix.grpc;

import io.github.picodotdev.blogbitix.grpc.service.HelloWorldClass;
import io.github.picodotdev.blogbitix.grpc.service.HelloWorldGrpc;
import io.grpc.stub.StreamObserver;

import java.text.MessageFormat;
import java.util.stream.IntStream;

public class HelloWorldService extends HelloWorldGrpc.HelloWorldImplBase {
    @Override
    public void helloMessage(HelloWorldClass.HelloRequest request, StreamObserver<HelloWorldClass.HelloResponse> responseObserver) {
        HelloWorldClass.HelloResponse response = HelloWorldClass.HelloResponse.newBuilder().setMessage(MessageFormat.format("Hello {0}!", request.getName())).build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void helloStream(HelloWorldClass.HelloRequest request, StreamObserver<HelloWorldClass.HelloResponse> responseObserver) {
        IntStream.range(1, 6).forEach(i -> {
            HelloWorldClass.HelloResponse response = HelloWorldClass.HelloResponse.newBuilder().setMessage(MessageFormat.format("Hello {0} {1}!", request.getName(), i)).build();
            responseObserver.onNext(response);

            sleep(3000);
        });
        responseObserver.onCompleted();
    }

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
