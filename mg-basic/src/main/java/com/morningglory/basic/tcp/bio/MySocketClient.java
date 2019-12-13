package com.morningglory.basic.tcp.bio;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import sun.nio.ch.IOUtil;

import java.io.IOException;
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
    private static final int MAX_BUFFER_SIZE = 1024;

    private String serverHost;

    private int port;

    private Socket socket;

    private OutputStream outputStream;

    public MySocketClient(String serverHost, int port) {
        this.serverHost = serverHost;
        this.port = port;
    }

    public void runClient() throws IOException {
        this.socket = new Socket(serverHost,port);
        log.info("bio connect success");
        outputStream = socket.getOutputStream();
        outputStream.write("hi,bio".getBytes());

        this.outputStream.close();
        //this.socket.close();
        log.info("bio closed");
    }

    public static void main(String[] args) throws IOException {

        MySocketClient client = new MySocketClient("127.0.0.1",9991);
        client.runClient();
    }
}
