package com.morningglory.io.bio;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @author qianniu
 * @date 2020/10/23 10:06 上午
 * @desc
 */
@Slf4j
public class BioTelnetTask implements Runnable{

    private static final int MAX_BUFFER_SIZE = 64;

    private Socket socket;

    private String PREFIX = "\r\nnsh>";
    private String WELCOMEMESSAGE = "Welcome BioSocketServer Console!!!"+PREFIX;

    public BioTelnetTask(Socket socket) {
        this.socket = socket;
    }

    @SneakyThrows
    @Override
    public void run() {
        log.info("BioSocketServer 启动成功,等待客户端连接");
        // 读取客户端信息
        InputStream inputStream = socket.getInputStream();
        // 向客户端写信息
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write(WELCOMEMESSAGE.getBytes());
        byte[] readBytes = new byte[MAX_BUFFER_SIZE];
        int msgLen;
        while ((msgLen = inputStream.read(readBytes)) != -1) {
            String temp = new String(readBytes, 0, msgLen, "UTF-8");

            // 命令行敲回车作为结束标志
            if("\r\n".equals(temp)){
                outputStream.write(PREFIX.getBytes());
                continue;
            }

            // 把换行符去掉，避免下边都需要判断
            temp = temp.replace("\r\n","");

            if("clear".equals(temp)){
                StringBuilder builder = new StringBuilder();
                for(int i=0;i<100;i++){
                    builder.append("\r\n");
                }
                builder.append(PREFIX);
                outputStream.write(builder.toString().getBytes());
                continue;
            }

            if("exit".equals(temp)){
                outputStream.write("Bey !!!\r\n".getBytes());
                socket.shutdownOutput();
                //socket.close();
                continue;
            }

            // TODO 这里可能会内存溢出,需要借鉴Buffer设置possion等值进行读取
            readBytes = new byte[MAX_BUFFER_SIZE];

            // 向客户端回写
            String response = "收到消息为:"+temp+PREFIX;
            outputStream.write(response.getBytes());

        }
    }
}
