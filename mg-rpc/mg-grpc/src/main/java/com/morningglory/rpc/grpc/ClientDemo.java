package com.morningglory.rpc.grpc;

import com.morningglory.rpc.grpc.auto.HelloRequest;
import com.morningglory.rpc.grpc.auto.HelloResponse;
import com.morningglory.rpc.grpc.auto.HelloServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

/**
 * @author qianniu
 * @date 2022/8/9
 * @desc
 */
public class ClientDemo {

    public static void main(String[] args) {

        ClientDemo clientDemo = new ClientDemo();
        clientDemo.remoteCall("nsh");
    }

    public void remoteCall(String name){
        HelloRequest request = HelloRequest.newBuilder().setName(name).build();
        HelloResponse response;

        // 至于访问地址创建通道
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051).usePlaintext().build();

        // 利用通道创建一个桩(stub)
        HelloServiceGrpc.HelloServiceBlockingStub blockingStub = HelloServiceGrpc.newBlockingStub(channel);

        HelloResponse helloResponse = blockingStub.sayHello(request);

        System.out.println("服务端返回结果:"+helloResponse.getMessage());
    }
}
