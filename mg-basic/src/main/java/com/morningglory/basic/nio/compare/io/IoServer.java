package com.morningglory.basic.nio.compare.io;


import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

/**
 * @Author: nsh
 * @Date: 2018/7/8
 * @Description:
 */
public class IoServer {


    public static void main(String[] args) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 3, 10000L, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(10));

        try (ServerSocket server = new ServerSocket(10101,10)) {
            System.out.println("服务器启动。。。");
            //conn();
            while (true){
                //获取一个套接字
                final Socket socket = server.accept();
                System.out.println("来了一个新客户端。");
                //业务处理(单线程)
                handler(socket);

                //线程池处理
                /*Runnable runnable = () -> {
                    handler(bio);
                };
                executor.execute(runnable);*/
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void conn() throws InterruptedException {
        CountDownLatch count = new CountDownLatch(1);
        String ip ="127.1.0.1";
        int port = 10101;
        Thread[] threads = new Thread[10];
        for(int i=0;i<10;i++){
            Thread t1 = new Thread(()->{
                try {
                    Socket socket = new Socket(ip,port);
                    socket.getOutputStream().write("123".getBytes());
                    count.countDown();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            threads[i] = t1;
        }

        for(int i =0;i<10;i++){
            threads[i].start();
        }


    }


    private static void handler(Socket socket) {

        try{
            byte[] bytes = new byte[1024];
            InputStream inputStream = socket.getInputStream();
            while (true){
                System.out.println("开始处理");
                //读取数据(阻塞)
                int read = inputStream.read(bytes);
                if(read != -1){
                    System.out.println(new String(bytes,0,read));
                }else{
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                System.out.println("socket关闭。");
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}