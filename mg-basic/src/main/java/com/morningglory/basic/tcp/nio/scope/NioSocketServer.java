package com.morningglory.basic.tcp.nio.scope;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author: qianniu
 * @Date: 2019-12-12 14:05
 * @Desc: 单线程监听所有channel 有就绪的就扔到线程池执行
 */
@Slf4j
public class NioSocketServer {

    private Selector selector;

    private ThreadPoolExecutor executor = new ThreadPoolExecutor(5,10,0, TimeUnit.SECONDS,new LinkedBlockingDeque(100));

    public void init() throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        InetSocketAddress address = new InetSocketAddress(8080);
        serverSocketChannel.bind(address);
        this.selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        log.info("init-thread-name:{}",Thread.currentThread().getName());
    }

    public void start() throws IOException {
        while (true){
            selector.select();
            Runnable runnable = () -> {
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                log.info("iterator.size ={}",selector.selectedKeys().size());
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    log.info("isAcceptable() ={},isReadable() ={},isWritable() ={}",key.isAcceptable(),key.isReadable(),key.isWritable());
                    try {
                        // 移除，防止重复操作
                        iterator.remove();
                        if (key.isAcceptable()) {
                            accept(key);
                        } else if (key.isReadable()) {
                            read(key);
                        } else if (key.isWritable()) {
                            write(key);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            };
            log.info("线程池增加任务,当前线程数：{},任务数：{}", executor.getActiveCount(), executor.getTaskCount());
            executor.execute(runnable);
        }
    }

    private void accept(SelectionKey key) throws IOException {
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
        SocketChannel channel = serverSocketChannel.accept();
        channel.configureBlocking(false);
        channel.register(this.selector,SelectionKey.OP_READ);
        log.info("accept-thread-name:{}",Thread.currentThread().getName());
    }

    private void read(SelectionKey key) throws IOException, InterruptedException {
        log.info("read-thread-name:{}",Thread.currentThread().getName());
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        SocketChannel channel = (SocketChannel) key.channel();
        channel.read(buffer);
        String request = new String(buffer.array()).trim();
        channel.register(this.selector,SelectionKey.OP_WRITE);
        log.info("请求参数：{}",request);
        Thread.sleep(1000);
    }

    private void write(SelectionKey key) throws IOException {
        log.info("write-thread-name:{}",Thread.currentThread().getName());
        String outString = "HTTP/1.1 200 OK\n" + "Content-Type: text/html;charset=UTF-8\n\n" + "success";
        log.info("响应参数：{}",outString);
        ByteBuffer outBuffer = ByteBuffer.wrap(outString.getBytes());
        SocketChannel channel = (SocketChannel) key.channel();
        channel.write(outBuffer);
        channel.close();
    }

    public static void main(String[] args) throws IOException {
        NioSocketServer server = new NioSocketServer();
        server.init();
        server.start();
    }
}
