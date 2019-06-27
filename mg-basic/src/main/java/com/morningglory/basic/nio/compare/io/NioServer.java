package com.morningglory.basic.nio.compare.io;

import sun.nio.ch.NativeThread;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * @Author: nsh
 * @Date: 2018/7/8
 * @Description:
 */
public class NioServer {

    //通道管理器
    private Selector selector;

    /**
     * 获取一个ServerSocket通道，并对改通道做一些初始化工作
     * @param port
     * @throws IOException
     */
    public void initServer(int port) throws IOException{
        //获得一个ServerSocket通道
        ServerSocketChannel socketChannel = ServerSocketChannel.open();
        //设置通道为非阻塞
        socketChannel.configureBlocking(false);
        //将改通道对应的serverSocket绑定到port端口
        socketChannel.socket().bind(new InetSocketAddress(port));
        //获得一个通道管理器
        this.selector = Selector.open();
        //将通道管理器和该通道绑定,并为该通道注册SelectionKey.OP_ACCEPT事件，
        //当事件到达时，selector.select()会返回，如果该事件没有事件到达会一直阻塞
        socketChannel.register(selector, SelectionKey.OP_ACCEPT);
    }

    /**
     * 采用轮训的方式监听selector上是否有需要处理的事件，如果有则进行处理
     * @throws IOException
     */
    public void listen() throws IOException{

        while (true){
            //当注册事件到达时，方法返回，否则一直阻塞
            System.out.println("before select:"+Thread.currentThread().getName());
            selector.select();
            System.out.println("after select:"+Thread.currentThread().getName());
            //获得selector中选中项的迭代器，选中项为注册的事件
            Iterator<?> iterable = this.selector.selectedKeys().iterator();
            SelectionKey key = (SelectionKey)iterable.next();
            //删除已选的key，以防重复处理
            iterable.remove();

            handler(key);
        }

    }

    /**
     * 处理请求
     * @param key
     * @throws IOException
     */
    private void handler(SelectionKey key) throws IOException{
        //客户端请求连接事件
        if(key.isAcceptable()){
            handlerAccept(key);
        }else if(key.isReadable()){
            handelerRead(key);
        }
    }

    /**
     * 处理读的事件
     * @param key
     */
    private void handelerRead(SelectionKey key) throws IOException{
        //服务器可读取的消息：得到事件发生的Socket通道
        SocketChannel channel = (SocketChannel) key.channel();
        //channel.register();
        //创建读取的缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        int read = channel.read(buffer);
        System.out.println("ThreadName ="+Thread.currentThread().getName());
        if(read > 0){
            byte[] data = buffer.array();
            String msg = new String(data).trim();
            System.out.println("服务器端收到信息："+msg);
            System.out.println("ThreadName ="+Thread.currentThread().getName());
            //回写数据
            ByteBuffer outBuffer = ByteBuffer.wrap("好的".getBytes());
            channel.write(outBuffer);
            System.out.println("服务器端返回信息："+"好的");
        }else{
            System.out.println("客户端关闭。");
            key.cancel();
        }
    }

    /**
     * 处理连接请求
     * @param key
     */
    private void handlerAccept(SelectionKey key) throws IOException{
        ServerSocketChannel server = (ServerSocketChannel)key.channel();
        //获得和客户端连接的通道
        SocketChannel channel = server.accept();
        //设置成非阻塞
        channel.configureBlocking(false);

        //在这里可以给客户端发信息
        System.out.println("新的客户端连接");
        //在和客户端连接成功后，为了可以接收到客户端的信息，需要给通道设置读的权限
        channel.register(this.selector,SelectionKey.OP_READ);
    }

    public static void main(String[] args) throws IOException {
        NioServer server = new NioServer();
        System.out.println(Thread.currentThread().getName());
        System.out.println(NativeThread.current());
        server.initServer(8000);
        server.listen();
    }
}