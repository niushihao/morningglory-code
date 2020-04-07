package com.morningglory.basic.netty.server;

import com.morningglory.basic.netty.conf.NettyServerConfig;
import com.morningglory.basic.netty.protocol.Message;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

import java.io.IOException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeoutException;

/**
 * @Author: qianniu
 * @Date: 2020-01-07 19:12
 * @Desc:
 */
public class RpcServer extends AbstractRpcRemotingServer implements ServerMessageSender{

    protected ServerMessageListener serverMessageListener;
    /**
     * Instantiates a new Rpc remoting server.
     *
     * @param messageExecutor
     */
    public RpcServer(ThreadPoolExecutor messageExecutor) {
        super(messageExecutor);
    }

    @Override
    public void init(){
        super.init();
        setChannelHandlers(this);
        ServerMessageListener listener = new ServerMessageListener();
        this.setServerMessageListener(listener);
        super.start();

    }

    @Override
    protected void dispatch(Message message, ChannelHandlerContext ctx) {
        Object body = message.getBody();
        serverMessageListener.onMessage(message,ctx,this);

    }

    @Override
    public void sendResponse(Message message, Channel channel, Object data) {
        super.sendResponse(message,channel,data);
    }

    @Override
    public Object sendSyncRequest(Channel channel, Object data) throws IOException, TimeoutException {
        return sendSyncRequest(channel,data, NettyServerConfig.RPC_REQUEST_TIMEOUT);
    }

    @Override
    public Object sendSyncRequest(Channel channel, Object data, long timeout) throws IOException, TimeoutException {
        return sendAsyncRequestWithResponse(null, channel, data, timeout);
    }

    @Override
    public Object sendASyncRequest(Channel channel, Object data) throws IOException, TimeoutException {
        return sendAsyncRequestWithoutResponse(channel,data);
    }

    public ServerMessageListener getServerMessageListener() {
        return serverMessageListener;
    }

    public void setServerMessageListener(ServerMessageListener serverMessageListener) {
        this.serverMessageListener = serverMessageListener;
    }
}
