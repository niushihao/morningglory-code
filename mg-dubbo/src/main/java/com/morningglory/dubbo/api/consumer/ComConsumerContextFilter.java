package com.morningglory.dubbo.api.consumer;

import org.apache.dubbo.common.io.Bytes;

/**
 * @author qianniu
 * @date 2020/7/22 2:48 下午
 * @desc
 */
public class ComConsumerContextFilter {

    protected static final short MAGIC = (short) 0xdabe;
    protected static final byte MAGIC_HIGH = Bytes.short2bytes(MAGIC)[0];
    protected static final byte MAGIC_LOW = Bytes.short2bytes(MAGIC)[1];
    public static void main(String[] args) {

        System.out.println(MAGIC);
        System.out.println(MAGIC_HIGH);
        System.out.println(MAGIC_LOW);
    }
}
