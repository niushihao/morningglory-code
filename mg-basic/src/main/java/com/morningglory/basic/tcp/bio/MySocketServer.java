package com.morningglory.basic.tcp.bio;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Author: qianniu
 * @Date: 2019-12-10 13:03
 * @Desc:
 */
@Data
@Slf4j
public class MySocketServer {
    private static final int MAX_BUFFER_SIZE = 1;

    private ServerSocket serverSocket;

    private Socket socket;

    private int port;

    private InputStream inputStream;
    public MySocketServer(int port) {
        this.port = port;
    }

    public void runServer() throws IOException {
        this.serverSocket = new ServerSocket(this.port);


        System.out.println("base socket server started.");
        // 10s内如果没有客户端连接进来，就是触发超时异常，必须在accept()方法前设置
        //serverSocket.setSoTimeout(10000);
        this.socket = serverSocket.accept();

        // 如果在read方法阻塞超过1s就是触发异常
        //this.socket.setSoTimeout(1000);
        this.inputStream = this.socket.getInputStream();

        byte[] readBytes = new byte[MAX_BUFFER_SIZE];

        int msgLen;
        StringBuilder stringBuilder = new StringBuilder();

        while ((msgLen = inputStream.read(readBytes)) != -1) {
            stringBuilder.append(new String(readBytes,0,msgLen,"UTF-8"));
        }

        System.out.println("get message from client: " + stringBuilder);

        OutputStream outputStream = socket.getOutputStream();
        outputStream.write("ok".getBytes());


        inputStream.close();
        socket.close();
        serverSocket.close();

    }

    public static void main(String[] args) throws IOException {
        MySocketServer socketServer = new MySocketServer(9991);
        socketServer.runServer();
    }


}
