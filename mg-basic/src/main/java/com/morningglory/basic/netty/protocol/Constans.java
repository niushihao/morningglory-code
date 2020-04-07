package com.morningglory.basic.netty.protocol;

/**
 * @Author: qianniu
 * @Date: 2020-01-06 16:01
 * @Desc:
 */
public class Constans {

    /**
     * Message type: Request
     */
    public static final byte MSGTYPE_RESQUEST = 0;
    /**
     * Message type: Response
     */
    public static final byte MSGTYPE_RESPONSE = 1;
    /**
     * Message type: Request which no need response
     */
    public static final byte MSGTYPE_RESQUEST_ONEWAY = 2;
    /**
     * Message type: Heartbeat Request
     */
    public static final byte MSGTYPE_HEARTBEAT_REQUEST = 3;
    /**
     * Message type: Heartbeat Response
     */
    public static final byte MSGTYPE_HEARTBEAT_RESPONSE = 4;

    /**
     * HEAD_LENGTH of protocol v1
     */
    public static final int V1_HEAD_LENGTH = 16;

    /**
     * Magic code
     */
    public static final byte[] MAGIC_CODE_BYTES = {(byte) 0xda, (byte) 0xda};

    /**
     * Protocol version
     */
    public static final byte VERSION = 1;

    /**
     * Max frame length
     */
    public static final int MAX_FRAME_LENGTH = 1024 * 1024;
}
