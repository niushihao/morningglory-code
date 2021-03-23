package com.morningglory.io.nio.simple_reactor;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;

/**
 * @author qianniu
 * @date 2020/10/22 1:57 下午
 * @desc
 */
@Slf4j
public class NioSocketServer {

    final Selector selector;
    final ServerSocketChannel serverSocketChannel;

    public NioSocketServer(int port) throws IOException {
        selector = Selector.open();
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(port));
        // 设置非阻塞
        serverSocketChannel.configureBlocking(false);

        // 分布处理，第一步accept事件
        SelectionKey key = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        key.attach(new Acceptor(serverSocketChannel,selector));
        log.info("Reactor 启动成功");
    }

    public void start() throws IOException {
        while (!Thread.interrupted()){
            int select = selector.select();
            log.info("select = {}",select);
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()){
                SelectionKey key = iterator.next();
                if(!key.isValid()){
                    log.error("key is unValid");
                    key.cancel();
                }
                iterator.remove();
                dispatch(key);
            }
        }
    }

    private void dispatch(SelectionKey key) {
        Runnable runnable = (Runnable) key.attachment();
        if(runnable != null){
            runnable.run();
        }
    }


    public static void main(String[] args) throws IOException {
        NioSocketServer socketServer = new NioSocketServer(9999);
        socketServer.start();
    }
}
