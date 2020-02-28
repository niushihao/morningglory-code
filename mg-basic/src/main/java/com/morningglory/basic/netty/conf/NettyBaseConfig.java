package com.morningglory.basic.netty.conf;

/**
 * @Author: qianniu
 * @Date: 2020-01-06 16:22
 * @Desc:
 */
public class NettyBaseConfig {

    /**
     * 是否允许批量发送消息
     */
    public static final boolean ENABLE_CLIENT_BATCH_SEND_REQUEST = true;

    /**
     * 最大重试次数
     */
    public static final int MAX_NOT_WRITEABLE_RETRY = 2000;
}
