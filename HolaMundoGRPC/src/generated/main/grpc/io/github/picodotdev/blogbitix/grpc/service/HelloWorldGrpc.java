package io.github.picodotdev.blogbitix.grpc.service;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.31.1)",
    comments = "Source: HelloWorld.proto")
public final class HelloWorldGrpc {

  private HelloWorldGrpc() {}

  public static final String SERVICE_NAME = "HelloWorld";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<io.github.picodotdev.blogbitix.grpc.service.HelloWorldClass.HelloRequest,
      io.github.picodotdev.blogbitix.grpc.service.HelloWorldClass.HelloResponse> getHelloMessageMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "HelloMessage",
      requestType = io.github.picodotdev.blogbitix.grpc.service.HelloWorldClass.HelloRequest.class,
      responseType = io.github.picodotdev.blogbitix.grpc.service.HelloWorldClass.HelloResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<io.github.picodotdev.blogbitix.grpc.service.HelloWorldClass.HelloRequest,
      io.github.picodotdev.blogbitix.grpc.service.HelloWorldClass.HelloResponse> getHelloMessageMethod() {
    io.grpc.MethodDescriptor<io.github.picodotdev.blogbitix.grpc.service.HelloWorldClass.HelloRequest, io.github.picodotdev.blogbitix.grpc.service.HelloWorldClass.HelloResponse> getHelloMessageMethod;
    if ((getHelloMessageMethod = HelloWorldGrpc.getHelloMessageMethod) == null) {
      synchronized (HelloWorldGrpc.class) {
        if ((getHelloMessageMethod = HelloWorldGrpc.getHelloMessageMethod) == null) {
          HelloWorldGrpc.getHelloMessageMethod = getHelloMessageMethod =
              io.grpc.MethodDescriptor.<io.github.picodotdev.blogbitix.grpc.service.HelloWorldClass.HelloRequest, io.github.picodotdev.blogbitix.grpc.service.HelloWorldClass.HelloResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "HelloMessage"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.picodotdev.blogbitix.grpc.service.HelloWorldClass.HelloRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.picodotdev.blogbitix.grpc.service.HelloWorldClass.HelloResponse.getDefaultInstance()))
              .setSchemaDescriptor(new HelloWorldMethodDescriptorSupplier("HelloMessage"))
              .build();
        }
      }
    }
    return getHelloMessageMethod;
  }

  private static volatile io.grpc.MethodDescriptor<io.github.picodotdev.blogbitix.grpc.service.HelloWorldClass.HelloRequest,
      io.github.picodotdev.blogbitix.grpc.service.HelloWorldClass.HelloResponse> getHelloStreamMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "HelloStream",
      requestType = io.github.picodotdev.blogbitix.grpc.service.HelloWorldClass.HelloRequest.class,
      responseType = io.github.picodotdev.blogbitix.grpc.service.HelloWorldClass.HelloResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<io.github.picodotdev.blogbitix.grpc.service.HelloWorldClass.HelloRequest,
      io.github.picodotdev.blogbitix.grpc.service.HelloWorldClass.HelloResponse> getHelloStreamMethod() {
    io.grpc.MethodDescriptor<io.github.picodotdev.blogbitix.grpc.service.HelloWorldClass.HelloRequest, io.github.picodotdev.blogbitix.grpc.service.HelloWorldClass.HelloResponse> getHelloStreamMethod;
    if ((getHelloStreamMethod = HelloWorldGrpc.getHelloStreamMethod) == null) {
      synchronized (HelloWorldGrpc.class) {
        if ((getHelloStreamMethod = HelloWorldGrpc.getHelloStreamMethod) == null) {
          HelloWorldGrpc.getHelloStreamMethod = getHelloStreamMethod =
              io.grpc.MethodDescriptor.<io.github.picodotdev.blogbitix.grpc.service.HelloWorldClass.HelloRequest, io.github.picodotdev.blogbitix.grpc.service.HelloWorldClass.HelloResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "HelloStream"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.picodotdev.blogbitix.grpc.service.HelloWorldClass.HelloRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.picodotdev.blogbitix.grpc.service.HelloWorldClass.HelloResponse.getDefaultInstance()))
              .setSchemaDescriptor(new HelloWorldMethodDescriptorSupplier("HelloStream"))
              .build();
        }
      }
    }
    return getHelloStreamMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static HelloWorldStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<HelloWorldStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<HelloWorldStub>() {
        @java.lang.Override
        public HelloWorldStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new HelloWorldStub(channel, callOptions);
        }
      };
    return HelloWorldStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static HelloWorldBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<HelloWorldBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<HelloWorldBlockingStub>() {
        @java.lang.Override
        public HelloWorldBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new HelloWorldBlockingStub(channel, callOptions);
        }
      };
    return HelloWorldBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static HelloWorldFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<HelloWorldFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<HelloWorldFutureStub>() {
        @java.lang.Override
        public HelloWorldFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new HelloWorldFutureStub(channel, callOptions);
        }
      };
    return HelloWorldFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class HelloWorldImplBase implements io.grpc.BindableService {

    /**
     */
    public void helloMessage(io.github.picodotdev.blogbitix.grpc.service.HelloWorldClass.HelloRequest request,
        io.grpc.stub.StreamObserver<io.github.picodotdev.blogbitix.grpc.service.HelloWorldClass.HelloResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getHelloMessageMethod(), responseObserver);
    }

    /**
     */
    public void helloStream(io.github.picodotdev.blogbitix.grpc.service.HelloWorldClass.HelloRequest request,
        io.grpc.stub.StreamObserver<io.github.picodotdev.blogbitix.grpc.service.HelloWorldClass.HelloResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getHelloStreamMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getHelloMessageMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                io.github.picodotdev.blogbitix.grpc.service.HelloWorldClass.HelloRequest,
                io.github.picodotdev.blogbitix.grpc.service.HelloWorldClass.HelloResponse>(
                  this, METHODID_HELLO_MESSAGE)))
          .addMethod(
            getHelloStreamMethod(),
            asyncServerStreamingCall(
              new MethodHandlers<
                io.github.picodotdev.blogbitix.grpc.service.HelloWorldClass.HelloRequest,
                io.github.picodotdev.blogbitix.grpc.service.HelloWorldClass.HelloResponse>(
                  this, METHODID_HELLO_STREAM)))
          .build();
    }
  }

  /**
   */
  public static final class HelloWorldStub extends io.grpc.stub.AbstractAsyncStub<HelloWorldStub> {
    private HelloWorldStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected HelloWorldStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new HelloWorldStub(channel, callOptions);
    }

    /**
     */
    public void helloMessage(io.github.picodotdev.blogbitix.grpc.service.HelloWorldClass.HelloRequest request,
        io.grpc.stub.StreamObserver<io.github.picodotdev.blogbitix.grpc.service.HelloWorldClass.HelloResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getHelloMessageMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void helloStream(io.github.picodotdev.blogbitix.grpc.service.HelloWorldClass.HelloRequest request,
        io.grpc.stub.StreamObserver<io.github.picodotdev.blogbitix.grpc.service.HelloWorldClass.HelloResponse> responseObserver) {
      asyncServerStreamingCall(
          getChannel().newCall(getHelloStreamMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class HelloWorldBlockingStub extends io.grpc.stub.AbstractBlockingStub<HelloWorldBlockingStub> {
    private HelloWorldBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected HelloWorldBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new HelloWorldBlockingStub(channel, callOptions);
    }

    /**
     */
    public io.github.picodotdev.blogbitix.grpc.service.HelloWorldClass.HelloResponse helloMessage(io.github.picodotdev.blogbitix.grpc.service.HelloWorldClass.HelloRequest request) {
      return blockingUnaryCall(
          getChannel(), getHelloMessageMethod(), getCallOptions(), request);
    }

    /**
     */
    public java.util.Iterator<io.github.picodotdev.blogbitix.grpc.service.HelloWorldClass.HelloResponse> helloStream(
        io.github.picodotdev.blogbitix.grpc.service.HelloWorldClass.HelloRequest request) {
      return blockingServerStreamingCall(
          getChannel(), getHelloStreamMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class HelloWorldFutureStub extends io.grpc.stub.AbstractFutureStub<HelloWorldFutureStub> {
    private HelloWorldFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected HelloWorldFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new HelloWorldFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<io.github.picodotdev.blogbitix.grpc.service.HelloWorldClass.HelloResponse> helloMessage(
        io.github.picodotdev.blogbitix.grpc.service.HelloWorldClass.HelloRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getHelloMessageMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_HELLO_MESSAGE = 0;
  private static final int METHODID_HELLO_STREAM = 1;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final HelloWorldImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(HelloWorldImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_HELLO_MESSAGE:
          serviceImpl.helloMessage((io.github.picodotdev.blogbitix.grpc.service.HelloWorldClass.HelloRequest) request,
              (io.grpc.stub.StreamObserver<io.github.picodotdev.blogbitix.grpc.service.HelloWorldClass.HelloResponse>) responseObserver);
          break;
        case METHODID_HELLO_STREAM:
          serviceImpl.helloStream((io.github.picodotdev.blogbitix.grpc.service.HelloWorldClass.HelloRequest) request,
              (io.grpc.stub.StreamObserver<io.github.picodotdev.blogbitix.grpc.service.HelloWorldClass.HelloResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class HelloWorldBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    HelloWorldBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return io.github.picodotdev.blogbitix.grpc.service.HelloWorldClass.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("HelloWorld");
    }
  }

  private static final class HelloWorldFileDescriptorSupplier
      extends HelloWorldBaseDescriptorSupplier {
    HelloWorldFileDescriptorSupplier() {}
  }

  private static final class HelloWorldMethodDescriptorSupplier
      extends HelloWorldBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    HelloWorldMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (HelloWorldGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new HelloWorldFileDescriptorSupplier())
              .addMethod(getHelloMessageMethod())
              .addMethod(getHelloStreamMethod())
              .build();
        }
      }
    }
    return result;
  }
}
