package com.morningglory.basic.nio.compare.io;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.file.*;

/**
 * @Author: nsh
 * @Date: 2018/7/19
 * @Description: 分别是用inputStream.read(),inputStream.read(byte[]),
 * testStream用时：1260
 * testByteBuffer用时：305
 * testByReaderAndWriter用时：3794
 * testByBufferReaderAndWriter用时：3537
 * testByReaderAndWriterLine用时：3662
 * testChannel 200
 * testFileCopy 288
 */
public class SpeedTest {

    public static void main(String[] args) throws IOException {

        String from = "/Users/nsh/Downloads/Spring源码深度解析.pdf";

        String to = "/Users/nsh/Downloads/Spring源码深度解析1.pdf";


        File fromFile = new File(from);
        File toFile = new File(to);

        testFileCopy(fromFile,toFile);

        testByteStream(fromFile,toFile);

        testByteBuffer(fromFile,toFile);

        testByReaderAndWriter(fromFile,toFile);

        testByBufferReaderAndWriter(fromFile,toFile);

        testByReaderAndWriterLine(fromFile,toFile);

        testChannel(fromFile,toFile);
    }

    private static void testFileCopy(File fromFile, File toFile) throws IOException {
        long begin = System.currentTimeMillis();
        // Files.copy底层方法与[testByteStream]相同;Files.move是包装了Files.copy方法
        Files.copy(fromFile.toPath(),toFile.toPath());
        System.out.println("testFileCopy用时："+(System.currentTimeMillis() -begin));
    }

    private static void testChannel(File fromFile, File toFile) throws IOException {

        long begin = System.currentTimeMillis();
        FileInputStream inputStream = new FileInputStream(fromFile);
        FileOutputStream outputStream = new FileOutputStream(toFile);
        FileChannel inChannel = inputStream.getChannel();
        FileChannel outChannel = outputStream.getChannel();
        // transferTo 和 transferFrom效率基本一样
        inChannel.transferTo(0,inChannel.size(),outChannel);
        //outChannel.transferFrom(inChannel,0,inChannel.size());
        System.out.println("testChannel用时："+(System.currentTimeMillis() -begin));
    }

    /**
     * 测试readLine
     * @param fromFile
     * @param toFile
     */
    private static void testByReaderAndWriterLine(File fromFile, File toFile) {

        try (BufferedReader reader = new BufferedReader(new FileReader(fromFile))) {
            BufferedWriter writer = new BufferedWriter(new FileWriter(toFile));
            String str ="";
            //开始时间
            long begin = System.currentTimeMillis();
            while ((str =reader.readLine()) != null){
                writer.write(str);
            }
            reader.close();
            writer.flush();
            //用时
            System.out.println("testByReaderAndWriterLine用时："+(System.currentTimeMillis() -begin));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * reader & writer buffer
     * @param fromFile
     * @param toFile
     */
    private static void testByBufferReaderAndWriter(File fromFile, File toFile) {

        try (FileReader reader = new FileReader(fromFile)) {
            BufferedReader bufReader = new BufferedReader(reader);
            BufferedWriter bufWriter = new BufferedWriter(new FileWriter(toFile));
            char[] chars = new char[1024];
            int len;
            //开始时间
            long begin = System.currentTimeMillis();
            while ((len = bufReader.read(chars)) != -1){
                bufWriter.write(chars);
            }
            bufReader.close();
            bufWriter.flush();
            //用时
            System.out.println("testByBufferReaderAndWriter用时："+(System.currentTimeMillis() -begin));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * reader & writer
     * @param fromFile
     * @param toFile
     */
    private static void testByReaderAndWriter(File fromFile, File toFile) {

        try (FileReader reader = new FileReader(fromFile)) {
            FileWriter writer = new FileWriter(toFile);

            char[] chars = new char[1024];
            int len;
            //开始时间
            long begin = System.currentTimeMillis();
            while ((len = reader.read(chars)) != -1){
                writer.write(chars);
            }
            reader.close();
            writer.flush();
            //用时
            System.out.println("testByReaderAndWriter用时："+(System.currentTimeMillis() -begin));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * buffer字节流
     * @param fromFile
     * @param toFile
     */
    private static void testByteBuffer(File fromFile, File toFile) {

        try (FileInputStream in = new FileInputStream(fromFile)) {

            byte[] bytes = new byte[1024];
            int len;
            BufferedInputStream bufferedIn = new BufferedInputStream(in);

            BufferedOutputStream bufferedOut = new BufferedOutputStream(new FileOutputStream(toFile));
            //开始时间
            long begin = System.currentTimeMillis();
            while ((len = bufferedIn.read(bytes,0,bytes.length)) != -1){
                bufferedOut.write(bytes,0,bytes.length);
            }
            in.close();
            bufferedIn.close();
            bufferedOut.flush();
            //用时
            System.out.println("testByteBuffer用时："+(System.currentTimeMillis() -begin));


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 使用 stream读写文件
     * @param fromFile
     */
    private static void testByteStream(File fromFile,File toFile) {

        try (FileInputStream in = new FileInputStream(fromFile)) {

            FileOutputStream out = new FileOutputStream(toFile);
            byte[] bytes = new byte[1024];
            //开始时间
            long begin = System.currentTimeMillis();
            int len;
            while ((len =in.read(bytes)) != -1){
                out.write(bytes);
            }
            in.close();
            out.flush();
            //用时
            System.out.println("testStream用时："+(System.currentTimeMillis() -begin));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}