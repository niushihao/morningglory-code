package com.morningglory.basic.netty.protocol;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: qianniu
 * @Date: 2020-01-06 15:14
 * @Desc:
 */
@Data
public class Message {

    private int id;

    private byte messageType;

    private byte codec;

    private byte compressor;

    private Map<String, String> headMap = new HashMap<>();

    private Object body;
}
