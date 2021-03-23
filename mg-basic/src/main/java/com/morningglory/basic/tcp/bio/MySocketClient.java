package com.morningglory.basic.tcp.bio;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import sun.nio.ch.IOUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @Author: qianniu
 * @Date: 2019-12-10 13:03
 * @Desc:
 */
@Data
@Slf4j
public class MySocketClient {
    private static final int MAX_BUFFER_SIZE = 1;

    private String serverHost;

    private int port;

    private Socket socket;

    private OutputStream outputStream;

    public MySocketClient(String serverHost, int port) {
        this.serverHost = serverHost;
        this.port = port;
    }

    public void runClient() throws IOException, InterruptedException {
        this.socket = new Socket(serverHost,port);
        log.info("bio connect success");

        byte[] readBytes = new byte[MAX_BUFFER_SIZE];
        int msgLen;

        outputStream = socket.getOutputStream();
        outputStream.write("hi,bio".getBytes());
        socket.shutdownOutput();

        InputStream inputStream = socket.getInputStream();
        StringBuilder stringBuilder = new StringBuilder();
        while ((msgLen = inputStream.read(readBytes)) != -1) {
            stringBuilder.append(new String(readBytes,0,msgLen,"UTF-8"));
        }
        System.out.println("接收到服务端数据 = "+stringBuilder.toString());

        //this.outputStream.close();
        //this.socket.close();
        //log.info("bio closed");
    }

    public static void main(String[] args) throws IOException, InterruptedException {

        MySocketClient client = new MySocketClient("127.0.0.1",9991);
        client.runClient();
    }
}
