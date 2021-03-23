package com.morningglory.io.bio;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author qianniu
 * @date 2020/10/23 9:52 上午
 * @desc
 */
@Slf4j
public class BioScopeSocketServer {

    private ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    private static int PORT = 9002;

    public BioScopeSocketServer(int PORT) {
        this.PORT = PORT;
    }

    public void runServer() throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        log.info("BioScopeSocketServer 启动成功,等待客户端连接");
        while (true){
            Socket socket = serverSocket.accept();
            log.info("新客户端连接:{}",socket.toString());
            Runnable task = new BioTelnetTask(socket);
            executorService.execute(task);
        }


    }


    public static void main(String[] args) throws IOException {
        BioScopeSocketServer socketServer = new BioScopeSocketServer(PORT);
        socketServer.runServer();
    }
}
