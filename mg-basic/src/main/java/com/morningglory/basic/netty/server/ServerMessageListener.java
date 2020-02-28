package com.morningglory.basic.netty.server;

import com.morningglory.basic.netty.MessageListener;
import com.morningglory.basic.netty.protocol.Message;
import io.netty.channel.ChannelHandlerContext;

/**
 * @Author: qianniu
 * @Date: 2020-01-07 19:15
 * @Desc:
 */
public class ServerMessageListener implements MessageListener {


    @Override
    public void onMessage(Message message, ChannelHandlerContext ctx) {

    }
}
