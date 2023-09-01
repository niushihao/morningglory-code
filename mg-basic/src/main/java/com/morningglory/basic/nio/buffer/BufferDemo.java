package com.morningglory.basic.nio.buffer;

import lombok.extern.slf4j.Slf4j;

import java.nio.ByteBuffer;

/**
 * @author qianniu
 * @date 2023/8/22
 * @desc
 */
@Slf4j
public class BufferDemo {

    public static void main(String[] args) {

        ByteBuffer allocate = ByteBuffer.allocate(10);
        allocate.put((byte) 1);

        allocate.flip();
        ByteBuffer slice = allocate.slice();
        allocate.get();

        System.out.println(slice.get());
    }
}
