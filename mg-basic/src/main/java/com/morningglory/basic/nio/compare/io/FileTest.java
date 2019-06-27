package com.morningglory.basic.nio.compare.io;

import java.io.*;

/**
 * @Author: nsh
 * @Date: 2018/7/18
 * @Description:
 */
public class FileTest {

    public static void main(String[] args) throws IOException {

        File file = new File("/Users/nsh/Downloads/nsh.txt");
        File file2 = new File("/Users/nsh/Downloads/nsh1.txt");
        File file3 = new File("/Users/nsh/Downloads/nsh3.txt");



        byte[] bytes = new byte[1024];

        readFromFile(file,bytes);

        writeToFile(file2,bytes);

        String msg = bufferReadFromFile(file);

        bufferWriteToFile(file3,msg);

    }

    /**
     * 使用buffer写
     * @param file3
     */
    private static void bufferWriteToFile(File file3,String msg) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file3))) {

            writer.write(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 使用buffer读
     * @param file
     */
    private static String bufferReadFromFile(File file) {

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

            return reader.readLine();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 向文件写
     * @param file2
     * @param bytes
     */
    private static void writeToFile(File file2, byte[] bytes) {
        try (FileOutputStream out = new FileOutputStream(file2)) {

            out.write(bytes);
            out.close();
            out.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 从文件读
     * @param file
     * @param bytes
     */
    private static void readFromFile(File file, byte[] bytes) {

        try (FileInputStream in = new FileInputStream(file)) {

            int read = in.read();
            System.out.println(read);
            //in.read(bytes);
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}