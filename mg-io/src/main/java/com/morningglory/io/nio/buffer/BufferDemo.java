package com.morningglory.io.nio.buffer;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * @author qianniu
 * @date 2020/10/23 2:46 下午
 * @desc
 * 罗列Buffer常用API
 */
@Slf4j
public class BufferDemo {

    private static ByteBuffer byteBuffer;
    private static FileChannel fileChannel;

    static {
        try {
            fileChannel = FileChannel.open(Paths.get("/Users/nsh/Downloads/1112"), StandardOpenOption.READ,StandardOpenOption.WRITE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {

        // 创建Buffer
        byteBuffer = createBuffer();

        // 向Buffer写
        writeBuffer();

        // 从Buffer读
        readBuffer();

        // 切割,只保留position后的数据,返回一个新Buffer对象
        slice();

        // 复制并返回一个新Buffer对象
        duplicate();

        // 当buffer从写模式切换到读模式后，向再次切换到写模式可以使用此方法，之前没有读完的数据会保留
        compact();

        // 当buffer从写模式切换到读模式后，向再次切换到写模式可以使用此方法，之前没有读完的数据不会保留
        clear();


    }

    private static void duplicate() {
        log.info("***********DUPLICATE*****************");
        ByteBuffer allocate = ByteBuffer.allocate(10);
        // 复制一个相同的buffer
        ByteBuffer duplicate = allocate.duplicate();
        log.info(duplicate.toString());
    }

    private static void clear() {
        log.info("***********CLEAR*****************");
        ByteBuffer allocate = ByteBuffer.allocate(10);
        allocate.put("!".getBytes());
        allocate.flip();
        allocate.clear();
        log.info(allocate.toString());
    }


    private static void compact() {
        log.info("***********COMPACT*****************");
        ByteBuffer allocate = ByteBuffer.allocate(10);
        allocate.put("!".getBytes());
        allocate.flip();
        allocate.compact();
        log.info(allocate.toString());
    }

    private static void slice() {
        log.info("***********SLICE*****************");
        ByteBuffer allocate = ByteBuffer.allocate(10);
        allocate.put("!".getBytes());
        ByteBuffer slice = allocate.slice();
        log.info(slice.toString());
    }

    private static void readBuffer() throws IOException {
        log.info("***********READ*****************");

        // 切换为读模式
        byteBuffer.flip();

        // 直接调用get读取，同样会更新position字段
        byteBuffer.get();
        log.info(byteBuffer.toString());

        // 向文件写入
        fileChannel.write(byteBuffer);
        log.info(byteBuffer.toString());

        // 指定索引读取，不会更新position字段
        byteBuffer.get(0);
        log.info(byteBuffer.toString());

    }

    private static void writeBuffer() throws Exception {
        log.info("***********WRITE*****************");

        // 直接调用put写入，同样会更新position字段
        byteBuffer.put("!".getBytes());
        log.info(byteBuffer.toString());

        // 从文件写入
        fileChannel.read(byteBuffer);
        log.info(byteBuffer.toString());

        // 指定索引写入，不会更新position字段
        byteBuffer.put(20, (byte) 0);
        log.info(byteBuffer.toString());
    }

    private static ByteBuffer createBuffer() {
        log.info("***********CREATE*****************");
        // 创建Buffer
        ByteBuffer allocate = ByteBuffer.allocate(48);
        log.info(allocate.toString());

        ByteBuffer wrap = ByteBuffer.wrap(new byte[10]);
        log.info(wrap.toString());
        return allocate;
    }
}
