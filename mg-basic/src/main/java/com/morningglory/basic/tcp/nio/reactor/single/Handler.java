package com.morningglory.basic.tcp.nio.reactor.single;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

/**
 * @Author: qianniu
 * @Date: 2020-01-08 13:28
 * @Desc: 单线程本除了器，和reactor处于同一个线程
 */
@Slf4j
public class Handler implements Runnable{

    final SocketChannel socketChannel;
    final SelectionKey selectionKey;
    ByteBuffer input = ByteBuffer.allocate(1024);
    ByteBuffer output = ByteBuffer.allocate(1024);
    static final int READING = 0, SENDING = 1;
    int state = READING;

    public Handler(SocketChannel socketChannel, Selector selector) throws IOException {
        this.socketChannel = socketChannel;
        this.socketChannel.configureBlocking(false);
        this.selectionKey = socketChannel.register(selector,SelectionKey.OP_READ);
        log.info("interestOps = {},readyOps = {}",selectionKey.interestOps(),selectionKey.readyOps());
        this.selectionKey.attach(this);
    }

    @Override
    public void run() {

        try {
            if(state == READING){
                read();
            }else if(state == SENDING){
                send();
            }
        }catch (Exception e){
            
        }
        
    }

    private void send() throws IOException {
        String message ="我是服务器,我在发送的时候发的;";
        output.clear();
        output.put(message.getBytes());
        output.flip();
        socketChannel.write(output);
        log.info("发送消息:{}",new String(output.array()));
        selectionKey.cancel();
    }

    private void read() throws IOException {
        socketChannel.read(input);
        log.info("收到消息:{}",new String(input.array()));

        // 接受消息时也可以发消息
        String message ="我是服务器,我在接受的时候发的;\n";
        output.clear();
        output.put(message.getBytes());
        output.flip();
        socketChannel.write(output);
        state = SENDING;


        selectionKey.interestOps(SelectionKey.OP_WRITE);
        log.info("interestOps = {},readyOps = {}",selectionKey.interestOps(),selectionKey.readyOps());
    }
}
