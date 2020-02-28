package com.morningglory.basic.netty.server;

import com.morningglory.basic.netty.protocol.Message;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author: qianniu
 * @Date: 2020-01-07 19:12
 * @Desc:
 */
public class RpcServer extends AbstractRpcRemotingServer{

    /**
     * Instantiates a new Rpc remoting server.
     *
     * @param messageExecutor
     */
    public RpcServer(ThreadPoolExecutor messageExecutor) {
        super(messageExecutor);
    }

    @Override
    protected void dispatch(Message message, ChannelHandlerContext ctx) {
        Object body = message.getBody();


    }
}
