package com.morningglory.basic.tcp.nio.base.scope;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * @Author: qianniu
 * @Date: 2020-01-08 09:18
 * @Desc:
 */
@Slf4j
public class NIOServerSocket {

    private static List<SelectionKey> writeQueue = Lists.newArrayList();
    private static Selector selector;

    public static void addWriteQueue(SelectionKey selectionKey){
        synchronized (writeQueue){
            writeQueue.add(selectionKey);
            selector.wakeup();
        }
    }

    public static void main(String[] args) throws IOException {

        // 创建ServerSocketChannel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        // 绑定端口
        serverSocketChannel.bind(new InetSocketAddress(9999));
        // 设置非阻塞
        serverSocketChannel.configureBlocking(false);
        // 创建通道选择器
        selector = Selector.open();

        serverSocketChannel.register(selector,SelectionKey.OP_ACCEPT);
        log.info("服务器正在监听9999端口");
        while (true){
            int select = selector.select();
            if(select > 0){
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                // 遍历所有keys
                while (iterator.hasNext()){
                    // 获取当前的key
                    SelectionKey key = iterator.next();
                    // 调用iterator.remote 并不是删除当前I/O通道，标识当前I/O通道已被处理
                    iterator.remove();
                    // 判断时间类型，做对应的处理
                    if(key.isAcceptable()){
                        ServerSocketChannel channel = (ServerSocketChannel) key.channel();
                        SocketChannel socketChannel = channel.accept();
                        log.info("处理请求:{}",socketChannel.getRemoteAddress());
                        // 设置非阻塞
                        socketChannel.configureBlocking(false);
                        // 注册到选择器
                        socketChannel.register(selector,SelectionKey.OP_READ);
                    }else if(key.isReadable()){
                        log.info("读事件");
                        // 取消读事件的监听
                        key.cancel();
                        // 调用读操作工具类
                        NIOHandler.read(key);
                    }else if(key.isWritable()){
                        log.info("写事件");
                        // 取消写事件的监听
                        key.cancel();
                        // 调用写操作工具类
                        NIOHandler.write(key);
                    }

                }
            }else {
                synchronized (writeQueue){
                    while (writeQueue.size() > 0){
                        SelectionKey key = writeQueue.remove(0);
                        // 注册写事件
                        SocketChannel socketChannel = (SocketChannel) key.channel();
                        Object attachment = key.attachment();
                        log.info("注册写事件");
                        socketChannel.register(selector,SelectionKey.OP_WRITE,attachment);
                    }
                }
            }

        }


    }
}
