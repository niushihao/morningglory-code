package com.morningglory.io.nio.simple_reactor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

/**
 * @author qianniu
 * @date 2020/11/10 11:45 上午
 * @desc
 */
@Slf4j
public class ChannelHandler implements Runnable{

    final SocketChannel socketChannel;
    final Selector selector;
    final SelectionKey selectionKey;
    ByteBuffer input = ByteBuffer.allocate(64);
    ByteBuffer output = ByteBuffer.allocate(64);

    public ChannelHandler(SocketChannel socketChannel, Selector selector) throws IOException {
        this.selector = selector;
        this.socketChannel = socketChannel;
        socketChannel.configureBlocking(false);
        this.selectionKey = socketChannel.register(selector, SelectionKey.OP_READ);
        selectionKey.attach(this);
    }

    @Override
    public void run() {
        try {
            if(!selectionKey.isReadable()){
               log.info("isAcceptable = {},isConnectable={},isWritable={},isValid={}"
                       ,selectionKey.isAcceptable(),selectionKey.isConnectable(),selectionKey.isWritable(),selectionKey.isValid());
               return;
            }
            input.clear();
            int read = socketChannel.read(input);
            // 客户端断开连接返回-1
            if(read == -1){
               socketChannel.close();
               return;
            }
            // 客户端没有发送任何数据
            if(read == 0){
                return;
            }
            input.flip();
            byte[] temp = new byte[input.remaining()];
            input.get(temp);
            String msg = new String(temp,"UTF-8").replace("\r\n","");
            String returnMessage ="ok\r\nreactor> ";
            if( StringUtils.isEmpty(msg)){
                returnMessage = "\rreactor> ";
            }

            if("exit".equals(msg)){
                socketChannel.close();
                return;
            }

            log.info("收到消息:{}",msg);
            // 业务处理


            output.clear();
            output.put(returnMessage.getBytes());
            output.flip();
            Thread.sleep(5000);
            socketChannel.write(output);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
