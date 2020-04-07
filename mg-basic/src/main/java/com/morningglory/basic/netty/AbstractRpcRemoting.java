package com.morningglory.basic.netty;

import com.morningglory.basic.netty.conf.NettyBaseConfig;
import com.morningglory.basic.netty.conf.NettyClientConfig;
import com.morningglory.basic.netty.protocol.Constans;
import com.morningglory.basic.netty.protocol.Message;
import com.morningglory.basic.netty.protocol.MessageFuture;
import com.morningglory.basic.thread.NamedThreadFactory;
import io.netty.channel.*;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.net.SocketAddress;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: qianniu
 * @Date: 2020-01-06 14:54
 * @Desc:
 */
@Slf4j
public abstract class AbstractRpcRemoting extends ChannelDuplexHandler implements Disposable{

    private final Object lock = new Object();

    /**
     * The Merge lock.
     */
    protected final Object mergeLock = new Object();

    /**
     * The Message executor.
     */
    protected final ThreadPoolExecutor messageExecutor;

    /**
     * The Channel handlers.
     */
    protected ChannelHandler[] channelHandlers;

    /**
     * Id generator of this remoting
     */
    private final AtomicInteger idGenerator = new AtomicInteger(0);

    /**
     * The Futures.
     */
    protected final ConcurrentHashMap<Integer, MessageFuture> futures = new ConcurrentHashMap<>();

    /**
     * The Basket map.
     */
    protected final ConcurrentHashMap<String, BlockingQueue<Message>> basketMap = new ConcurrentHashMap<>();

    /**
     * The Is sending.
     */
    protected volatile boolean isSending = false;

    /**
     * The Timer executor.
     */
    protected final ScheduledExecutorService timerExecutor = new ScheduledThreadPoolExecutor(1,
            new NamedThreadFactory("timeoutChecker", 1, true));

    private static final int TIMEOUT_CHECK_INTERNAL = 3000;

    public AbstractRpcRemoting(ThreadPoolExecutor messageExecutor) {
        this.messageExecutor = messageExecutor;
    }

    /**
     * 每3s遍历futures 并删除超时的消息
     */
    public void init(){
        log.info("AbstractRpcRemoting init");
        timerExecutor.scheduleAtFixedRate(() -> {
            for(Map.Entry<Integer, MessageFuture> entry : futures.entrySet()){
               if(entry.getValue().isTimeout()){
                   futures.remove(entry.getKey());
                   entry.getValue().setResultMessage(null);
                   log.debug("timeout clear future: {}", entry.getValue().getRequestMessage().getBody());
               }
            }
        },TIMEOUT_CHECK_INTERNAL,TIMEOUT_CHECK_INTERNAL,TimeUnit.MILLISECONDS);
    }
    @Override
    public void destroy() {
        messageExecutor.shutdown();
        timerExecutor.shutdown();
        log.info("AbstractRpcRemoting destroy");
    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) {
        synchronized (lock) {
            if (ctx.channel().isWritable()) {
                lock.notifyAll();
            }
        }
        ctx.fireChannelWritabilityChanged();
    }

    @Override
    public void channelRead(final ChannelHandlerContext ctx, Object msg) throws Exception{
        if(!(msg instanceof Message)){
            return;
        }

        Message message = (Message) msg;
        // 收到的是请求消息
        if(Constans.MSGTYPE_RESQUEST == message.getMessageType() || Constans.MSGTYPE_RESQUEST_ONEWAY == message.getMessageType()){
            log.debug(String.format("%s msgId:%s, body:%s", this, message.getId(), message.getBody()));
            this.messageExecutor.execute(() -> {
                dispatch(message, ctx);
            });

        }else{
            MessageFuture messageFuture = futures.remove(message.getId());
            log.debug(String.format("%s msgId:%s, future :%s, body:%s", this, message.getId(), messageFuture,
                            message.getBody()));
            if(messageFuture != null){
                messageFuture.setResultMessage(message.getBody());
            }else{
                this.messageExecutor.execute(() -> {
                    dispatch(message, ctx);
                });
            }
        }
    }

    @Override
    public void close(ChannelHandlerContext ctx, ChannelPromise future) throws Exception {
        log.info(ctx + " will closed");
        super.close(ctx, future);
    }

    /**
     * Add channel pipeline last.
     *
     * @param channel  the channel
     * @param handlers the handlers
     */
    protected void addChannelPipelineLast(Channel channel, ChannelHandler... handlers) {
        if (null != channel && null != handlers) {
            channel.pipeline().addLast(handlers);
        }
    }

    /**
     * Sets channel handlers.
     *
     * @param handlers the handlers
     */
    protected void setChannelHandlers(ChannelHandler... handlers) {
        this.channelHandlers = handlers;
    }

    /**
     * 异步发送消息并同步等待响应结果
     * @param address
     * @param channel
     * @param msg
     * @param timeout
     * @return
     * @throws TimeoutException
     */
    protected Object sendAsyncRequestWithResponse(String address, Channel channel, Object msg, long timeout) throws TimeoutException{
        if(timeout <= 0){
            throw new RuntimeException("必须设置超时时间");
        }
        return sendAsyncRequest(address, channel, msg, timeout);
    }

    /**
     * 异步发送消息，不关系响应结果
     * @param channel
     * @param msg       要发送的消息
     * @return
     * @throws TimeoutException
     */
    protected Object sendAsyncRequestWithoutResponse(Channel channel, Object msg) throws
            TimeoutException {
        return sendAsyncRequest(null, channel, msg, 0);
    }

    /**
     * 异步发送响应消息
     * @param request   请求消息
     * @param channel   通道
     * @param msg       返回消息
     */
    protected void sendResponse(Message request,Channel channel,Object msg){
        Message responseMsg = new Message();
        responseMsg.setMessageType(Constans.MSGTYPE_RESPONSE);
        responseMsg.setCodec(request.getCodec());
        responseMsg.setCompressor(request.getCompressor());
        responseMsg.setBody(msg);
        responseMsg.setId(request.getId());

        channelWritableCheck(channel,msg);
        log.info("send response:" + msg + ",channel:" + channel);
        channel.writeAndFlush(responseMsg);
    }

    protected abstract void dispatch(Message message, ChannelHandlerContext ctx);


    public abstract void destroyChannel(String address,Channel channel);

    public void destroyChannel(Channel channel){
        SocketAddress socketAddress = channel.remoteAddress();
        String address = socketAddress.toString();
        if (socketAddress.toString().indexOf(NettyClientConfig.SOCKET_ADDRESS_START_CHAR) == 0) {
            address = socketAddress.toString().substring(NettyClientConfig.SOCKET_ADDRESS_START_CHAR.length());
        }
        destroyChannel(address,channel);
    }

    private Object sendAsyncRequest(String address, Channel channel, Object msg, long timeout) throws TimeoutException {

        if (channel == null) {
            log.warn("sendAsyncRequestWithResponse nothing, caused by null channel.");
            return null;
        }

        final Message message = new Message();
        message.setId(idGenerator.incrementAndGet());
        message.setMessageType(Constans.MSGTYPE_RESQUEST_ONEWAY);
        message.setBody(msg);

        final MessageFuture messageFuture = new MessageFuture();
        messageFuture.setRequestMessage(message);
        messageFuture.setTimeout(timeout);
        futures.put(message.getId(),messageFuture);

        // 如果指定了地址，尝试相同地址的数据批量发送
        if(StringUtils.isNotBlank(address)){
            if(NettyBaseConfig.ENABLE_CLIENT_BATCH_SEND_REQUEST){
                ConcurrentHashMap<String, BlockingQueue<Message>> map = basketMap;
                BlockingQueue<Message> basket = map.get(address);
                if(basket == null){
                    map.putIfAbsent(address, new LinkedBlockingQueue<>());
                    basket = map.get(address);
                }
                basket.offer(message);
                log.debug("offer message: {}", message.getBody());

                if(!isSending){
                    synchronized (mergeLock) {
                        mergeLock.notifyAll();
                    }
                }
            }
        }else{
            sendSingleRequest(channel, msg, message);
            log.info("send this msg[{}] by single send.", msg);
        }

        // 如果指定了超时时间，需要校验再次时间内是否发送完成
        if (timeout > 0) {
            try {
                return messageFuture.get(timeout, TimeUnit.MILLISECONDS);
            } catch (Exception exx) {
                log.error("wait response error:{},ip:{},request:{}", exx.getMessage(), address, msg);
                throw new TimeoutException("");
            }
        } else {
            return null;
        }

    }

    private void sendSingleRequest(Channel channel, Object body, Message message) {
        ChannelFuture future;
        channelWritableCheck(channel, body);
        future = channel.writeAndFlush(message);
        future.addListener(new GenericFutureListener<Future<? super Void>>() {
            @Override
            public void operationComplete(Future<? super Void> future) throws Exception {
                if(!future.isSuccess()){
                    MessageFuture messageFuture = futures.remove(message.getId());
                    if(messageFuture != null){
                        messageFuture.setResultMessage(future.cause());
                    }
                }
            }
        });
    }

    private void channelWritableCheck(Channel channel, Object body) {
        int tryTimes = 0;
        synchronized (lock){
            while (!channel.isWritable()){
                try {
                    tryTimes++;
                    if(tryTimes > NettyBaseConfig.MAX_NOT_WRITEABLE_RETRY){
                        destroyChannel(channel);
                        throw new RuntimeException("body:" + ((body == null) ? "null" : body.toString())+";"
                                +"channelIsNotWritable");
                    }
                }catch (Exception exx) {
                    log.error(exx.getMessage());
                }
            }
        }
    }
}
