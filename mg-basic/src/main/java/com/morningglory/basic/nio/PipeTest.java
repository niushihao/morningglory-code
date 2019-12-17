package com.morningglory.basic.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Pipe;

/**
 * @Author: qianniu
 * @Date: 2019-12-13 18:44
 * @Desc:
 */
public class PipeTest {

    public static void main(String[] args) throws IOException {

        // 获取管道
        Pipe pipe = Pipe.open();


        Pipe.SinkChannel sink = pipe.sink();
        ByteBuffer buf = ByteBuffer.allocate(18);
        buf.put("向管道写数据".getBytes());
        sink.write(buf);
        //2. 将缓冲区中的数据写入管道
        buf.flip();
        sink.write(buf);

        // 读取管道的数据
        buf.flip();
        Pipe.SourceChannel source = pipe.source();
        int len = source.read(buf);
        System.out.println(new String(buf.array(), 0, len));


    }
}
