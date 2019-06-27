package com.morningglory.basic.cache.redis.serializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.util.Assert;

import java.nio.charset.Charset;

/**
 * @Author: qianniu
 * @Date: 2019-04-30 16:06
 * @Desc:
 */
public class StringRedisSerializer implements RedisSerializer<Object> {
    private final Charset charset;

    private final String target = "\"";

    private final String replacement = "";

    public StringRedisSerializer() {
        this(Charset.forName("UTF8"));
    }

    public StringRedisSerializer(Charset charset) {
        Assert.notNull(charset, "Charset must not be null!");
        this.charset = charset;
    }

    @Override
    public String deserialize(byte[] bytes) {
        return (bytes == null ? null : new String(bytes, charset));
    }

    @Override
    public byte[] serialize(Object object) {
        String string = JSON.toJSONString(object);
        if (string == null) {
            return null;
        }
        string = string.replace(target, replacement);
        return string.getBytes(charset);
    }

    public static void main(String[] args) {

        StringRedisSerializer serializer = new StringRedisSerializer();
        String str ="ISSUE";
        byte[] serialize = serializer.serialize(str);

        String deserialize = serializer.deserialize(serialize);

        System.out.println(deserialize);


        GenericFastJsonRedisSerializer fastJsonRedisSerializer = new GenericFastJsonRedisSerializer();
        byte[] issues = fastJsonRedisSerializer.serialize("ISSUE");
        Object deserialize1 = fastJsonRedisSerializer.deserialize(issues);
        System.out.println("deserialize1 = "+deserialize1);

    }

}
