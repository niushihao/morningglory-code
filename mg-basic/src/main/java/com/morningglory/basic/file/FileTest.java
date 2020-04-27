package com.morningglory.basic.file;

import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.*;

import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

/**
 * @Author: qianniu
 * @Date: 2020-01-15 16:43
 * @Desc: RandomAccessFile使用说明
 * 1.可以用来创建指定大小的文件(两种方式，具体看create方法)
 * 2.可以从任意位置向文件写数据(两种方式,具体看write方法)
 * 3.可以从任意位置从文件读数据(两种方式,具体看read方法)
 * 4.可以支持断点续传，或者多线程上传/下载文件
 */
@Slf4j
public class FileTest {

    static String str = "i am a student!";

    static RandomAccessFile file1;
    static RandomAccessFile file2;

    public static void main(String[] args) throws IOException, InterruptedException {

        // 创建指定大小的文件
        create(1024);

        // 随机写文件
        write(file1,file2);

        // 随机读
        read(file1,file2);

        // 局部修改
        change(file1,file2);

    }

    /**
     * 创建文件并指定大小
     * @param size 文件大小
     * @throws FileNotFoundException
     */
    public static void create(int size) throws IOException {

        file1 = new RandomAccessFile("/Users/nsh/Downloads/111","rw1");
        file2 = new RandomAccessFile("/Users/nsh/Downloads/222","rw");

        // 第一种设置方式
        file1.setLength(size);

        // 第二种设置的方式
        file2.getChannel().map(FileChannel.MapMode.READ_WRITE,0,size);


    }

    /**
     * 向文件写数据,刚创建的对象默认从0向后写
     * 1、通过RandomAccessFile.seek(Long position)方法可以指定从哪个位置开始写
     * 2、通过channel.position(Long position)方法可以指定从哪个位置开始写
     * @param file1
     * @param file2
     * @throws IOException
     */
    private static void write(RandomAccessFile file1, RandomAccessFile file2) throws IOException {


        // 将数据从指定位置写入
        file1.write(str.getBytes());
        log.info("file1.getFilePointer() = {}",file1.getFilePointer());

        // 第二种写入方式
        FileChannel channel = file2.getChannel();
        log.info("channel.point() = {}",channel.position());
        channel.write(ByteBuffer.wrap(str.getBytes()));
        log.info("file2.getFilePointer() = {},channel.point() = {}",file2.getFilePointer(),channel.position());

    }

    /**
     * 从文件读数据
     * 1、通过RandomAccessFile.seek(Long position)方法可以指定从哪个位置开始读
     * 2、通过channel.position(Long position)方法可以指定从哪个位置开始读
     * @param file1
     * @param file2
     * @throws IOException
     */
    public static void read(RandomAccessFile file1, RandomAccessFile file2) throws IOException {

        byte[] readbytes = new byte[str.length()];
        // 指定位置读取
        file1.seek(0);
        int read = file1.read(readbytes);
        log.info("read str = {}",new String(readbytes));

        FileChannel channel = file2.getChannel();
        // 指定位置读
        channel.position(0);
        ByteBuffer allocate = ByteBuffer.allocate(str.length());
        channel.read(allocate);
        log.info("read str = {}",new String(readbytes));

    }

    /**
     * 这个例子和write()基本类似，只是这里指定了开始写的位置
     * 这个例子将最后的 ! 变成 .
     * @param file1
     * @param file2
     */
    private static void change(RandomAccessFile file1, RandomAccessFile file2) throws IOException {

        int position = str.length() - 1;
        // 将指针定位到指定位置
        file1.seek(position);
        // 将 ! 替换为 .
        file1.write(".".getBytes());

        // 将指针定位到指定位置
        file2.getChannel().position(position);
        // 将 ! 替换为 .
        file2.getChannel().write(ByteBuffer.wrap(".".getBytes()));

        // 第三种方式
        MappedByteBuffer map = file2.getChannel().map(FileChannel.MapMode.READ_WRITE, 0, str.length());
        map.put("?".getBytes());
        map.force();
        log.info("-------------after change-----------------");

        // 写完后调用读方法
        read(file1,file2);
    }

}
