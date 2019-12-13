package com.morningglory.basic.tcp.nio.scope;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

/**
 * @Author: qianniu
 * @Date: 2019-12-12 14:05
 * @Desc:
 */
public class NioSocketServer {

    private Selector selector;

    public void init() throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress address = new InetSocketAddress(8080);
        serverSocketChannel.bind(address);
        this.selector = Selector.open();
    }

    public void start(){

    }
}
