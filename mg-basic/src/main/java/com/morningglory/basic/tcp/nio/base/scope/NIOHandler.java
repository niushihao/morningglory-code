package com.morningglory.basic.tcp.nio.base.scope;

import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author: qianniu
 * @Date: 2020-01-08 09:46
 * @Desc:
 */
@Slf4j
public class NIOHandler {
    /**
     * 构造线程池
     */
    private static ThreadPoolExecutor executorService = new ThreadPoolExecutor(10,10,0,TimeUnit.SECONDS,new ArrayBlockingQueue<>(100));

    public static void read(SelectionKey key){
        executorService.execute(() -> {
            try {
                SocketChannel channel = (SocketChannel) key.channel();
                // 从通道读数据
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                int read = 0;
                while (true){
                    buffer.clear();
                    read = channel.read(buffer);
                    if(read <= 0){
                        break;
                    }
                    buffer.flip();
                    while (buffer.hasRemaining()){
                        outputStream.write(buffer.get());
                    }

                }
                log.info("服务器收到的数据:{}",new String(outputStream.toByteArray()));

                // 将数据添加到key中
                key.attach(outputStream);
                // 将写事件添加到队列
                NIOServerSocket.addWriteQueue(key);

                // 不能通过这种方式注册，因为Selector对象(SelectorImpl)中的publicKeys 在main线程获得了锁
                // channel.register(selector,SelectionKey.OP_WRITE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public static void write(SelectionKey key){
        executorService.execute(() -> {
            try {
                // 写操作
                SocketChannel channel = (SocketChannel) key.channel();
                // 拿到客户端传递的数据
                ByteArrayOutputStream outputStream = (ByteArrayOutputStream) key.attachment();
                log.info("客户端发来的数据为：{}",new String(outputStream.toByteArray()));
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                String message = "你好，我是服务器";
                buffer.put(message.getBytes());
                buffer.flip();
                channel.write(buffer);
                channel.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        });
    }


}
