package com.morningglory.io.nio.simple_reactor;

import lombok.extern.slf4j.Slf4j;

import java.nio.ByteBuffer;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @author qianniu
 * @date 2020/11/10 11:24 上午
 * @desc
 */
@Slf4j
public class Acceptor implements Runnable{

    final ServerSocketChannel serverSocketChannel;
    final Selector selector;

    public Acceptor(ServerSocketChannel serverSocketChannel,  Selector selector) {
        this.serverSocketChannel = serverSocketChannel;
        this.selector = selector;
    }

    @Override
    public void run() {
        try {
            SocketChannel socketChannel = serverSocketChannel.accept();
            if(socketChannel != null){
                socketChannel.write(ByteBuffer.wrap("Reactor Demo by nsh\r\nreactor> ".getBytes()));
                new ChannelHandler(socketChannel,selector);
            }
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }

    }
}
