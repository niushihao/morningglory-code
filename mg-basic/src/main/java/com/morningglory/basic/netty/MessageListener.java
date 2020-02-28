package com.morningglory.basic.netty;

import com.morningglory.basic.netty.protocol.Message;
import io.netty.channel.ChannelHandlerContext;

/**
 * @Author: qianniu
 * @Date: 2020-01-07 19:19
 * @Desc:
 */
public interface MessageListener {

    void onMessage(Message message, ChannelHandlerContext ctx);
}
