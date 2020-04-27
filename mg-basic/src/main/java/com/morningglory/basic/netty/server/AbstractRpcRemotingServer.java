package com.morningglory.basic.netty.server;

import com.morningglory.basic.netty.AbstractRpcRemoting;
import com.morningglory.basic.netty.conf.NettyServerConfig;
import com.morningglory.basic.netty.protocol.ProtocolV1Decoder;
import com.morningglory.basic.netty.protocol.ProtocolV1Encoder;
import com.morningglory.basic.concurrent.thread.NamedThreadFactory;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @Author: qianniu
 * @Date: 2020-01-06 19:14
 * @Desc:
 */
@Slf4j
public abstract class AbstractRpcRemotingServer extends AbstractRpcRemoting implements RemotingService {

    private final ServerBootstrap serverBootstrap;
    private final EventLoopGroup eventLoopGroupWorker;
    private final EventLoopGroup eventLoopGroupBoss;
    private int listenPort;
    private final AtomicBoolean initialized = new AtomicBoolean(false);

    /**
     * Instantiates a new Rpc remoting server.
     *
     */
    public AbstractRpcRemotingServer(ThreadPoolExecutor messageExecutor, ChannelHandler... handlers) {
        super(messageExecutor);
        this.serverBootstrap = new ServerBootstrap();
        if(NettyServerConfig.ENABLEEPOLL){
            this.eventLoopGroupBoss = new EpollEventLoopGroup(NettyServerConfig.DEFAULT_BOSS_THREAD_SIZE
                    ,new NamedThreadFactory(NettyServerConfig.DEFAULT_BOSS_THREAD_PREFIX
                    , NettyServerConfig.DEFAULT_BOSS_THREAD_SIZE));
            this.eventLoopGroupWorker = new EpollEventLoopGroup(NettyServerConfig.DEFAULT_WORKER_THREAD_SIZE,new NamedThreadFactory(NettyServerConfig.EPOLL_WORKER_THREAD_PREFIX,
                    NettyServerConfig.DEFAULT_BOSS_THREAD_SIZE));
        }else {
            this.eventLoopGroupBoss = new NioEventLoopGroup(NettyServerConfig.DEFAULT_BOSS_THREAD_SIZE,new NamedThreadFactory(NettyServerConfig.DEFAULT_BOSS_THREAD_PREFIX
                    , NettyServerConfig.DEFAULT_BOSS_THREAD_SIZE));
            this.eventLoopGroupWorker = new NioEventLoopGroup(NettyServerConfig.DEFAULT_WORKER_THREAD_SIZE,new NamedThreadFactory(NettyServerConfig.DEFAULT_BOSS_THREAD_PREFIX
                    , NettyServerConfig.DEFAULT_BOSS_THREAD_SIZE));
        }

        if (null != handlers) {
            channelHandlers = handlers;
        }

        setListenPort(NettyServerConfig.DEFAULT_LISTEN_PORT);
    }

    @Override
    public void start() {
        this.serverBootstrap.group(this.eventLoopGroupBoss,this.eventLoopGroupWorker)
                .channel(NioServerSocketChannel.class)
                // 服务端是串行处理客户端请求的，并发时会放入阻塞队列，SO_BACKLOG是设置阻塞队列的大小
                .option(ChannelOption.SO_BACKLOG,NettyServerConfig.SOBACKLOGSIZE)
                // 表示可以重复使用本地地址和端口
                .option(ChannelOption.SO_REUSEADDR, true)
                // 如果两小时内没有数据通讯,TCP会自动发送一个活动探测数据报文
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                // 不使用延时发送(输入一次发送一次),与TCP_CORK相反(将多个小的数据包组装为更大的帧然后进行发送)
                .childOption(ChannelOption.TCP_NODELAY, true)
                // 设置发送缓冲区大小
                .childOption(ChannelOption.SO_SNDBUF, NettyServerConfig.SERVER_SOCKET_SEND_BUFSIZE)
                // 设置接收缓冲区大小
                .childOption(ChannelOption.SO_RCVBUF, NettyServerConfig.SERVER_SOCKET_RESVBUF_SIZE)
                .childOption(ChannelOption.WRITE_BUFFER_WATER_MARK,
                        new WriteBufferWaterMark(NettyServerConfig.WRITE_BUFFER_LOW_WATERMARK,
                                NettyServerConfig.WRITE_BUFFER_HIGH_WATERMARK))
                .localAddress(listenPort)
                .childHandler(new ChannelInitializer<SocketChannel>(){

                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(
                                // netty的心跳实现,0表示忽略,当空闲时间超过设置的时间后会触发回调userEventTriggered
                                new IdleStateHandler(15,0,0))
                                .addLast(new ProtocolV1Decoder())
                                .addLast(new ProtocolV1Encoder());
                        if (null != channelHandlers) {
                            addChannelPipelineLast(ch, channelHandlers);
                        }
                    }
                });

        if(NettyServerConfig.ENABLESERVERPOOLEDBYTEBUFALLOCATOR){
            this.serverBootstrap.childOption(ChannelOption.ALLOCATOR, NettyServerConfig.DIRECT_BYTE_BUF_ALLOCATOR);
        }

        try {
            ChannelFuture future = this.serverBootstrap.bind(listenPort).sync();
            log.info("Server started ... ");
            //TODO 可以在此增加注册的机制
            initialized.set(true);
            future.channel().closeFuture().sync();
        } catch (Exception exx) {
            throw new RuntimeException(exx);
        }

    }

    @Override
    public void shutdown() {
        initialized.set(false);
        log.info("Shutting server down. ");
        this.eventLoopGroupBoss.shutdownGracefully();
        this.eventLoopGroupWorker.shutdownGracefully();
    }

    @Override
    public void destroyChannel(String serverAddress, Channel channel) {

        log.info("will destroy channel:{},address:{}", channel, serverAddress);
        channel.disconnect();
        channel.close();
    }



    /**
     * Sets listen port.
     *
     * @param listenPort the listen port
     */
    public void setListenPort(int listenPort) {

        if (listenPort <= 0) {
            throw new IllegalArgumentException("listen port: " + listenPort + " is invalid!");
        }
        this.listenPort = listenPort;
    }

    /**
     * Gets listen port.
     *
     * @return the listen port
     */
    public int getListenPort() {
        return listenPort;
    }
}
