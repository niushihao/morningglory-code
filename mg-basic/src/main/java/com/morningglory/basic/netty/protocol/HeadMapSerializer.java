package com.morningglory.basic.netty.protocol;

import io.netty.buffer.ByteBuf;
import org.apache.commons.lang3.StringUtils;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: qianniu
 * @Date: 2020-03-24 09:50
 * @Desc:
 */
public class HeadMapSerializer {
    private static final HeadMapSerializer INSTANCE = new HeadMapSerializer();

    private HeadMapSerializer(){}

    public static HeadMapSerializer getInstance() {
        return INSTANCE;
    }

    /**
     * encode head map
     *
     * @param map header map
     * @param out ByteBuf
     * @return length of head map bytes
     */
    public int encode(Map<String, String> map, ByteBuf out) {
        if (map == null || map.isEmpty() || out == null) {
            return 0;
        }
        int start = out.writerIndex();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (key != null) {
                writeString(out, key);
                writeString(out, value);
            }
        }
        return out.writerIndex() - start;
    }

    /**
     * decode head map
     *
     * @param in ByteBuf
     * @param length of head map bytes
     * @return header map
     */
    public Map<String, String> decode(ByteBuf in, int length) {
        Map<String, String> map = new HashMap<String, String>();
        if (in == null || in.readableBytes() == 0 || length == 0) {
            return map;
        }
        int tick = in.readerIndex();
        while (in.readerIndex() - tick < length) {
            String key = readString(in);
            String value = readString(in);
            map.put(key, value);
        }

        return map;
    }

    /**
     * Write string
     *
     * @param out ByteBuf
     * @param str String
     */
    protected void writeString(ByteBuf out, String str) {
        if (str == null) {
            out.writeShort(-1);
        } else if (str.isEmpty()) {
            out.writeShort(0);
        } else {
            byte[] bs = str.getBytes(Charset.forName("UTF-8"));
            out.writeShort(bs.length);
            out.writeBytes(bs);
        }
    }
    /**
     * Read string
     *
     * @param in ByteBuf
     * @return String
     */
    protected String readString(ByteBuf in) {
        int length = in.readShort();
        if (length < 0) {
            return null;
        } else if (length == 0) {
            return StringUtils.EMPTY;
        } else {
            byte[] value = new byte[length];
            in.readBytes(value);
            return new String(value, Charset.forName("UTF-8"));
        }
    }
}
