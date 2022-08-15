package com.morningglory.rpc.grpc;

import com.morningglory.rpc.grpc.auto.HelloRequest;
import com.morningglory.rpc.grpc.auto.HelloResponse;
import com.morningglory.rpc.grpc.auto.HelloServiceGrpc;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;
import java.util.Objects;

/**
 * @author qianniu
 * @date 2022/8/9
 * @desc
 */
public class ServerDemo {

    private Server server;

    public static void main(String[] args) throws IOException, InterruptedException {

        ServerDemo serverDemo = new ServerDemo();

        serverDemo.start();
    }

    private void start() throws IOException, InterruptedException {

        int port = 50051;
        server = ServerBuilder.forPort(port).addService(new HelloServiceImpl()).build()
                .start();

        if(Objects.nonNull(server)){
            System.out.println("server启动成功");
            server.awaitTermination();
        }

    }

    static class HelloServiceImpl extends HelloServiceGrpc.HelloServiceImplBase{

        @Override
        public void sayHello(com.morningglory.rpc.grpc.auto.HelloRequest request,
                             io.grpc.stub.StreamObserver<com.morningglory.rpc.grpc.auto.HelloResponse> responseObserver){

            HelloResponse reply = HelloResponse.newBuilder().setMessage("hello,"+request.getName()).build();

            // 通知grpc框架,将响应发送到客户端
            responseObserver.onNext(reply);

            // 表示完成调用
            responseObserver.onCompleted();

        }
    }
}
