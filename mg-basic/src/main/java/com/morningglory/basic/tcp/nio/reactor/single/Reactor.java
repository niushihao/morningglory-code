package com.morningglory.basic.tcp.nio.reactor.single;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * @Author: qianniu
 * @Date: 2020-01-08 13:14
 * @Desc:
 */
@Slf4j
public class Reactor implements Runnable{

    final Selector selector;
    final ServerSocketChannel serverSocketChannel;

    public Reactor(int port) throws IOException {
        selector = Selector.open();
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(port));
        // 设置非阻塞
        serverSocketChannel.configureBlocking(false);

        // 分布处理，第一步accept事件
        SelectionKey key = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        key.attach(new Acceptor());
        log.info("Reactor 启动成功");
    }

    public static void main(String[] args) throws IOException {
        new Thread(new Reactor(9999)).start();
    }

    @Override
    public void run() {

        try {
            while (!Thread.interrupted()){
                selector.select();
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()){
                    SelectionKey key = iterator.next();
                    iterator.remove();
                    dispatch(key);
                }
            }

        }catch (Exception e){

        }
    }

    /**
     * 分发事件
     * @param key
     */
    private void dispatch(SelectionKey key) {
        Runnable runnable = (Runnable) key.attachment();
        if(runnable != null){
            runnable.run();
        }
    }


    public class Acceptor implements Runnable{

        @Override
        public void run() {
            try {
                SocketChannel socketChannel = serverSocketChannel.accept();
                if(socketChannel != null){
                    socketChannel.write(ByteBuffer.wrap("Implementation of Reactor Design Partten by nsh\r\nreactor> ".getBytes()));
                    new Handler(socketChannel,selector);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
