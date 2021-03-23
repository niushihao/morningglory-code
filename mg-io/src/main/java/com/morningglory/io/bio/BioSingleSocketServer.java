package com.morningglory.io.bio;

import lombok.extern.slf4j.Slf4j;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author qianniu
 * @date 2020/10/22 1:57 下午
 * @desc 单线程版的BIO SocketServer,只能与一个Client建立链接
 * 可以使用 telnet localhost 9001 与此服务交互
 *
 */
@Slf4j
public class BioSingleSocketServer {


    private ServerSocket serverSocket;


    private int port;

    public BioSingleSocketServer(int port) {
        this.port = port;
    }

    public void runServer() throws IOException {
        serverSocket = new ServerSocket(port);
        log.info("BioSingleSocketServer 启动成功,等待客户端连接");

        Socket socket = serverSocket.accept();
        log.info("新客户端连接:{}",socket.toString());

        new BioTelnetTask(socket).run();
    }

    public static void main(String[] args) throws IOException {

        BioSingleSocketServer socketServer = new BioSingleSocketServer(9001);
        socketServer.runServer();
    }
}
