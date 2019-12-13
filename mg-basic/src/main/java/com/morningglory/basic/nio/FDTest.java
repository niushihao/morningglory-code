package com.morningglory.basic.nio;

import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @Author: qianniu
 * @Date: 2019-12-11 22:43
 * @Desc:
 */
public class FDTest {

    public static void main(String[] args) throws IOException {

        // 与System.out.print('A') 等效
        FileOutputStream out = new FileOutputStream(FileDescriptor.out);
        out.write('A');
        out.close();
    }
}
