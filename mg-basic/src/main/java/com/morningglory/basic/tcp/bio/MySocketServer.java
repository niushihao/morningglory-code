package com.morningglory.basic.tcp.bio;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
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
    private static final int MAX_BUFFER_SIZE = 1024;

    private ServerSocket serverSocket;

    private Socket socket;

    private int port;

    private InputStream inputStream;
    public MySocketServer(int port) {
        this.port = port;
    }

    public void runServer() throws IOException {
        this.serverSocket = new ServerSocket(port);
        log.info("serverSocket start");
        this.socket = serverSocket.accept();

        this.inputStream = this.socket.getInputStream();
        byte[] readBytes = new byte[MAX_BUFFER_SIZE];
        int msgLen;
        StringBuilder stringBuilder = new StringBuilder();


        while ((msgLen = inputStream.read(readBytes)) != -1) {
            stringBuilder.append(new String(readBytes,0,msgLen,"UTF-8"));
        }

        log.info("get message from client: {}",stringBuilder);

        inputStream.close();

        socket.close();
        serverSocket.close();
        log.info("socketServer closed");

    }

    public static void main(String[] args) throws IOException {
        MySocketServer socketServer = new MySocketServer(9991);
        socketServer.runServer();
    }


}
