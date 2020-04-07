package com.morningglory.basic.netty.server;

import com.morningglory.basic.netty.MessageListener;
import com.morningglory.basic.netty.protocol.Constans;
import com.morningglory.basic.netty.protocol.Message;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: qianniu
 * @Date: 2020-01-07 19:15
 * @Desc:
 */
@Slf4j
public class ServerMessageListener implements MessageListener {


    @Override
    public void onMessage(Message message, ChannelHandlerContext ctx,ServerMessageSender sender) {

        //TODO 业务处理
        Message response = new Message();
        response.setMessageType(Constans.MSGTYPE_RESPONSE);
        response.setBody("success");
        sender.sendResponse(message,ctx.channel(),response);
    }
}
