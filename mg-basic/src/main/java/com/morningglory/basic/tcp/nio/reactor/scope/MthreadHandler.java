package com.morningglory.basic.tcp.nio.reactor.scope;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * @Author: qianniu
 * @Date: 2020-01-08 16:17
 * @Desc: 多线程版处理器
 */
@Slf4j
public class MthreadHandler implements Runnable{

    ExecutorService executor = Executors.newFixedThreadPool(10);
    final SocketChannel socketChannel;
    final SelectionKey selectionKey;
    ByteBuffer input = ByteBuffer.allocate(1024);
    ByteBuffer output = ByteBuffer.allocate(1024);
    static final int READING = 0, SENDING = 1, PROCESSING = 2;
    int state = READING;

    public MthreadHandler(SocketChannel socketChannel, Selector selector) throws IOException {
        this.socketChannel = socketChannel;
        this.socketChannel.configureBlocking(false);
        this.selectionKey = socketChannel.register(selector,SelectionKey.OP_READ);
        log.info("interestOps = {},readyOps = {}",selectionKey.interestOps(),selectionKey.readyOps());
        this.selectionKey.attach(this);
        this.selectionKey.interestOps(SelectionKey.OP_READ);
        // 唤醒阻塞中的selector,如果当前没有被阻塞则拥有唤醒下次阻塞的时候
        //selector.wakeup();
    }

    @Override
    public void run() {
        try{

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
        state = PROCESSING;
        log.info("收到消息:{}",new String(input.array()));
        executor.execute(new Processer());

        /*try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
    }


    class Processer implements Runnable{

        @Override
        public void run() {
            processAndHandOff();
        }

        synchronized void processAndHandOff() {
            log.info("do something begin");
            state = SENDING;
            // or rebind attachment
            /**
             * interestOps与regist不同
             * 通过interestOps设置时，如果Selecter.select()先于interestOps(x)执行，不会唤醒阻塞中的select;
             * 通过regist设置时，即使Selecter.select()先于interestOps(x)执行，也会唤醒阻塞中的select
             * 所以为了防止无法唤醒，可以在下边加一个wakeup()，手动唤醒
             */
            selectionKey.interestOps(SelectionKey.OP_WRITE);
            selectionKey.selector().wakeup();

            log.info("do something end");
        }
    }
}
