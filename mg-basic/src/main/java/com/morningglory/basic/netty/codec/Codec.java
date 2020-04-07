package com.morningglory.basic.netty.codec;

/**
 * @Author: qianniu
 * @Date: 2020-03-23 16:11
 * @Desc:
 */
public interface Codec {

    /**
     * Encode object to byte[]
     * @param t
     * @param <T>
     * @return
     */
    <T> byte[] encode(T t);

    /**
     * Decode t from byte[]
     * @param bytes
     * @param <T>
     * @return
     */
    <T> T decode(byte[] bytes);
}
