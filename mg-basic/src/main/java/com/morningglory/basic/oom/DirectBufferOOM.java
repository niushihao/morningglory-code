package com.morningglory.basic.oom;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * @author qianniu
 * @date 2020/12/29 12:25 上午
 * @desc
 * -Xmx128m -XX:MaxDirectMemorySize=100M
 */
public class DirectBufferOOM {

    public static void main(String[] args) {
        final int _1M = 1024 * 1024 * 1;
        List<ByteBuffer> buffers = new ArrayList<>();
        int count = 1;
        while (true) {
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(_1M);
            buffers.add(byteBuffer);
            System.out.println(count++);
        }

    }
}
