package com.morningglory.basic.netty.conf;

import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.epoll.Epoll;
import io.netty.util.NettyRuntime;

/**
 * @Author: qianniu
 * @Date: 2020-01-06 19:20
 * @Desc:
 */
public class NettyServerConfig extends NettyBaseConfig{

    /**boss线程数*/
    public static final int DEFAULT_BOSS_THREAD_SIZE = 1;
    /**worker线程数*/
    public static final int DEFAULT_WORKER_THREAD_SIZE = NettyRuntime.availableProcessors() * 2;
    /**默认监听端口*/
    public static final int DEFAULT_LISTEN_PORT = 8091;

    public static final int SOBACKLOGSIZE = 1024;

    public static final int SERVER_SOCKET_SEND_BUFSIZE = 153600;

    public static final int SERVER_SOCKET_RESVBUF_SIZE = 153600;

    public static final int WRITE_BUFFER_HIGH_WATERMARK = 67108864;

    public static final int WRITE_BUFFER_LOW_WATERMARK = 1048576;

    public static final boolean ENABLESERVERPOOLEDBYTEBUFALLOCATOR = true;

    public static final String DEFAULT_BOSS_THREAD_PREFIX = "NettyBoss";
    public static final String EPOLL_WORKER_THREAD_PREFIX = "NettyServerEPollWorker";
    public static final String NIO_WORKER_THREAD_PREFIX = "NettyServerNIOWorker";
    public static final String DEFAULT_EXECUTOR_THREAD_PREFIX = "NettyServerBizHandler";

    public static final boolean ENABLEEPOLL = Epoll.isAvailable();

    public static final PooledByteBufAllocator DIRECT_BYTE_BUF_ALLOCATOR =
            new PooledByteBufAllocator(
                    true,
                    DEFAULT_WORKER_THREAD_SIZE,
                    DEFAULT_WORKER_THREAD_SIZE,
                    2048 * 64,
                    10,
                    512,
                    256,
                    64,
                    true,
                    0
            );

}
