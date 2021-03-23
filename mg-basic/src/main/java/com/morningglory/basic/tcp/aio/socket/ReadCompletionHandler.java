package com.morningglory.basic.tcp.aio.socket;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.Date;

/**
 * @author qianniu
 * @date 2020/11/9 9:41 下午
 * @desc
 */
@Slf4j
public class ReadCompletionHandler implements CompletionHandler<Integer, ByteBuffer> {

    private AsynchronousSocketChannel channel;

    public ReadCompletionHandler(AsynchronousSocketChannel channel) {
        this.channel = channel;
    }

    @Override
    public void completed(Integer result, ByteBuffer attachment) {
        attachment.flip();
        byte[] body = new byte[attachment.remaining()];
        attachment.get(body);
        try {
            String req = new String(body, "UTF-8");
            log.info("The time server receive order :{}",req);
            String currentTime = "QUERY TIME ORDER".equals(req) ? new Date().toString() : "BAD ORDER";
            doWrite(currentTime);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    private void doWrite(String currentTime) {
        if(StringUtils.isEmpty(currentTime)){
            return;
        }
        byte[] bytes = currentTime.getBytes();
        ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
        writeBuffer.put(bytes);
        writeBuffer.flip();
        channel.write(writeBuffer, writeBuffer, new CompletionHandler<Integer, ByteBuffer>() {
            @Override
            public void completed(Integer result, ByteBuffer buffer) {
                //
                if(buffer.hasRemaining()){
                    channel.write(buffer,buffer,this);
                }
            }

            @SneakyThrows
            @Override
            public void failed(Throwable exc, ByteBuffer attachment) {
                channel.close();
            }
        });

    }

    @SneakyThrows
    @Override
    public void failed(Throwable exc, ByteBuffer attachment) {
        channel.close();
    }
}
