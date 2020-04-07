package com.morningglory.basic.netty.protocol;

import java.io.Serializable;

/**
 * @Author: qianniu
 * @Date: 2020-03-24 09:44
 * @Desc:
 */
public class HeartbeatMessage implements Serializable {
    private boolean ping = true;

    /**
     * The constant PING.
     */
    public static final HeartbeatMessage PING = new HeartbeatMessage(true);
    /**
     * The constant PONG.
     */
    public static final HeartbeatMessage PONG = new HeartbeatMessage(false);

    private HeartbeatMessage(boolean ping) {
        this.ping = ping;
    }

    @Override
    public String toString() {
        return this.ping ? "services ping" : "services pong";
    }

    public boolean isPing() {
        return ping;
    }

    public void setPing(boolean ping) {
        this.ping = ping;
    }
}
