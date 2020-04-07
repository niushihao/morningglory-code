package com.morningglory.basic.netty.server;

import com.morningglory.basic.netty.protocol.Message;
import io.netty.channel.Channel;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Author: qianniu
 * @Date: 2020-03-20 13:31
 * @Desc:
 */
public interface ServerMessageSender {

    /**
     * 发送响应消息
     * @param message   请求数据
     * @param channel   通道
     * @param data      响应数据
     */
    void sendResponse(Message message, Channel channel,Object data);

    /**
     * 同步发送请求 使用默认超时时间
     * @param channel 通道
     * @param data    请求数据
     * @return
     */
    Object sendSyncRequest(Channel channel,Object data) throws IOException, TimeoutException;;

    /**
     * 同步发送请求
     * @param channel 通道
     * @param data    请求数据
     * @param timeout 超时时间
     * @return
     */
    Object sendSyncRequest(Channel channel,Object data,long timeout) throws IOException, TimeoutException;

    /**
     * 异步发送请求
     * @param channel
     * @param data
     * @return
     * @throws IOException
     * @throws TimeoutException
     */
    Object sendASyncRequest(Channel channel,Object data) throws IOException, TimeoutException;
}
