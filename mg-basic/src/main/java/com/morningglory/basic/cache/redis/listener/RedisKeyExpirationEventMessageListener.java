package com.morningglory.basic.cache.redis.listener;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

import java.nio.charset.StandardCharsets;

/**
 * 直接继承KeyExpirationEventMessageListener 达到key失效被通知的效果
 * 但是有单继承的限制
 */
public class RedisKeyExpirationEventMessageListener extends KeyExpirationEventMessageListener {


    public RedisKeyExpirationEventMessageListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    @Override
    public void onMessage(Message message, byte[] bytes) {
        String channel = new String(message.getChannel(), StandardCharsets.UTF_8);
        //过期的key
        String key = new String(message.getBody(),StandardCharsets.UTF_8);

        System.out.println("redis key 过期：pattern= "+new String(bytes) +",channel= "+channel +",key ="+key);

    }
}
