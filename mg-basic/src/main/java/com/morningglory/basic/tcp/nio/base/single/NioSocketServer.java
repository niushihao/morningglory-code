package com.morningglory.basic.tcp.nio.base.single;

import lombok.extern.slf4j.Slf4j;
import sun.nio.ch.IOUtil;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

/**
 * @Author: qianniu
 * @Date: 2019-12-10 13:28
 * @Desc: 单线程监听所有channel 有就绪的就线性处理
 */
@Slf4j
public class NioSocketServer {

    private Selector selector;

    public static void main(String[] args) throws IOException, InterruptedException {

        NioSocketServer server = new NioSocketServer();
        log.info("main-thread-name:{}",Thread.currentThread().getName());
        server.init();
        server.start();
    }

    private void init() throws IOException {
        // 创建选择器
        ServerSocketChannel channel = ServerSocketChannel.open();
        // 设置非阻塞
        channel.configureBlocking(false);
        InetSocketAddress address = new InetSocketAddress(8080);
        channel.bind(address);
        log.info("NIO抢票神器已经成功启动，端口8080");
        this.selector = Selector.open();
        channel.register(selector, SelectionKey.OP_ACCEPT);
        log.info("init-thread-name:{}",Thread.currentThread().getName());
    }

    private void start() throws IOException, InterruptedException {
        try {
            log.info("start-thread-name:{}", Thread.currentThread().getName());
            while (true) {
                // 选择器执行
                this.selector.select();
                Iterator<SelectionKey> iterator = this.selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    boolean writable = key.isWritable();
                    boolean readable = key.isReadable();
                    // 移除，防止重复操作
                    if (key.isAcceptable()) {
                        accept(key);
                    } else if (key.isReadable()) {
                        read(key);
                    } else if (key.isWritable()) {
                        write(key);
                    }
                    iterator.remove();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
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
        String outString = "HTTP/1.1 200 OK\n" + "Content-Type: text/html;charset=UTF-8\n\n" + "success3";
        log.info("响应参数：{}",outString);
        ByteBuffer outBuffer = ByteBuffer.wrap(outString.getBytes());
        SocketChannel channel = (SocketChannel) key.channel();
        channel.write(outBuffer);
        channel.close();
    }
}
