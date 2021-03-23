package com.morningglory.basic.tcp.nio.reactor.scope;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @author qianniu
 * @date 2020/8/18 9:12 下午
 * @desc
 */
@Slf4j
public class Acceptor implements Runnable{
    final Selector sel;
    final ServerSocketChannel serverSocket;
    int next = 0;

    public Acceptor(Selector sel, int port) throws IOException {
        this.sel = sel;
        this.serverSocket = ServerSocketChannel.open();

        // 绑定端口
        serverSocket.socket().bind(new InetSocketAddress(port));

        // 设置成非阻塞模式
        serverSocket.configureBlocking(false);

        // 注册到 选择器 并设置处理 socket 连接事件
        SelectionKey sk = serverSocket.register(sel, SelectionKey.OP_ACCEPT);

        sk.attach(this);
        log.info("mainReactor-" + "Acceptor: Listening on port: {}", port);
    }

    @Override
    public synchronized void run() {
        try {
            // 接收连接，非阻塞模式下，没有连接直接返回 null
            SocketChannel socketChannel = serverSocket.accept();
            if(socketChannel != null){
                // 把提示发到界面
                socketChannel.write(ByteBuffer.wrap("Implementation of Reactor Design Partten by tonwu.net\r\nreactor> ".getBytes()));

                // 将接收的连接注册到从 Reactor 上

                // 发现无法直接注册，一直获取不到锁，这是由于 从 Reactor 目前正阻塞在 select() 方法上，此方法已经
                // 锁定了 publicKeys（已注册的key)，直接注册会造成死锁

                // 如何解决呢，直接调用 wakeup，有可能还没有注册成功又阻塞了。这是一个多线程同步的问题，可以借助队列进行处理
                Reactor subReactor = MultiReactor.getNextReactor();
                socketChannel.register(subReactor.getSelector(),SelectionKey.OP_READ | SelectionKey.OP_WRITE,new BasicHandler(sel,socketChannel));
                subReactor.reigster(new BasicHandler(socketChannel));
//					new MultithreadHandler(subSel, sc);

            }

        }catch (Exception e){

        }
    }
}
