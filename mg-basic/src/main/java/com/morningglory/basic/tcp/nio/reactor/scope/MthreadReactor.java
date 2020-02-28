package com.morningglory.basic.tcp.nio.reactor.scope;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * @Author: qianniu
 * @Date: 2020-01-08 22:09
 * @Desc:
 */
@Slf4j
public class MthreadReactor implements Runnable{

    // subReactors集合, 一个selector代表一个subReactor
    Selector[] selectors=new Selector[1];
    int next = 0;
    final ServerSocketChannel serverSocket;

    /**
     * Reactor初始化
      * @param port
     * @return
     * @throws IOException
     */
    public MthreadReactor(int port) throws IOException {
        selectors[0]=Selector.open();
        //selectors[1]= Selector.open();
        serverSocket = ServerSocketChannel.open();
        serverSocket.socket().bind(new InetSocketAddress(port));
        //非阻塞
        serverSocket.configureBlocking(false);


        //分步处理,第一步,接收accept事件
        SelectionKey sk1 = serverSocket.register(selectors[0], SelectionKey.OP_ACCEPT);
        sk1.attach(new Acceptor());
    }

    public static void main(String[] args) throws IOException {
        new Thread(new MthreadReactor(9999)).start();
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {

                for (int i = 0; i <1 ; i++) {
                    selectors[i].select();
                    Iterator<SelectionKey> it  =  selectors[i].selectedKeys().iterator();
                    if(!it.hasNext()){
                        log.info("我被免费唤醒一次");
                    }

                    while (it.hasNext()) {
                        //Reactor负责dispatch收到的事件
                        SelectionKey next = it.next();
                        if(next.isAcceptable()){
                            log.info("我被链接唤醒");
                        }else if(next.isReadable()){
                            log.info("我被读就绪唤醒");
                        }else if(next.isWritable()){
                            log.info("我被写就绪唤醒");
                        }
                        it.remove();
                        dispatch((SelectionKey) (next));

                    }
                }

            }
        } catch (IOException ex) { }
    }

    void dispatch(SelectionKey k) {
        Runnable r = (Runnable) (k.attachment());
        //调用之前注册的callback对象
        if (r != null) {
            r.run();
        }
    }


    class Acceptor implements Runnable{
        @Override
        public synchronized void run(){
            try {
                // 主selector负责accept
                SocketChannel socketChannel =
                        serverSocket.accept();
                if (socketChannel != null) {
                    // 选个subReactor去负责接收到的connection
                    new MthreadHandler(socketChannel,selectors[next]);
                }
                if (++next == selectors.length){
                    next = 0;
                }
            }catch (Exception e){

            }

        }
    }
}
