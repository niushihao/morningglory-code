package com.morningglory.io.nio.simple_reactor;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Objects;

/**
 * @author qianniu
 * @date 2020/11/10 1:03 下午
 * @desc
 */
@Slf4j
public class NioSocketClient {

    Selector selector = null;
    SocketChannel socketChannel = null;
    final String host;
    final int port;
    static volatile boolean success = false;

    public NioSocketClient(String host,int port) {
        this.host = host;
        this.port = port;
        try {
            this.selector = Selector.open();
            this.socketChannel = SocketChannel.open();
            socketChannel.connect(new InetSocketAddress(host,port));
            socketChannel.configureBlocking(false);
            socketChannel.register(selector,SelectionKey.OP_READ);
            success = true;
        }catch (Exception e){
            success = false;
            log.error("客户端初始化失败");
        }
    }

    public void start() throws InterruptedException {

        try {
            while (!Thread.interrupted()){
                selector.select();

                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()){
                    SelectionKey key = iterator.next();
                    iterator.remove();
                    dispatch(key);
                }
            }
        }catch (Exception e){
            success = false;
            while (!success){
                NioSocketClient socketClient = new NioSocketClient("127.0.0.1", 9999);
                if(Objects.isNull(socketClient)){
                    continue;
                }
                log.warn("链接失败，尝试重新链接。。。");
                Thread.sleep(2000);
            }
            log.info("重连成功。。。");
        }

    }

    private void dispatch(SelectionKey key) throws IOException {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        if(key.isAcceptable()){
            log.info("isAcceptable");
        }
        else if(key.isConnectable()){
            log.info("isConnectable");
        }else if(key.isReadable()){
            ByteBuffer readBuffer = ByteBuffer.allocate(64);
            int read = socketChannel.read(readBuffer);
            if(read > 0){
                String message = new String(readBuffer.array());
                log.info("MSG FORM SERVER->: {}",message);
                write2Server(socketChannel);
            }else if(read < 0){
                socketChannel.close();
                key.cancel();
            }else {
                socketChannel.register(selector,SelectionKey.OP_READ);
            }

        }else if(key.isWritable()){
            log.info("isWritable");
        }
    }

    private void write2Server(SocketChannel socketChannel) throws IOException {
        //向多路复用器注册可读事件
        socketChannel.register(selector, SelectionKey.OP_READ);
        ByteBuffer byteBuffer = ByteBuffer.wrap("How are you".getBytes());
        //byteBuffer.flip();
        socketChannel.write(byteBuffer);
    }

    public static void main(String[] args) throws IOException, InterruptedException {

        NioSocketClient socketClient = null;
        while (!success){
            socketClient = new NioSocketClient("127.0.0.1",9999);
            log.warn("链接失败，尝试重新链接。。。");
            Thread.sleep(2000);
        }
        log.info("重连成功。。。");
        socketClient.start();

    }
}
