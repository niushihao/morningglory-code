package com.morningglory.basic.cache.redis.listener;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * 直接继承 MessageListener 然后制定订阅的是 Topic KEYEVENT_EXPIRED_TOPIC = new PatternTopic("__keyevent@*__:expired")
 * 达到key失效后通知的效果
 */
public class RedisMessageListener implements MessageListener {

    private RedisTemplate redisTemplate;

    public RedisMessageListener(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void onMessage(Message message, byte[] bytes) {
        RedisSerializer<?> serializer = redisTemplate.getValueSerializer();
        Object channel = serializer.deserialize(message.getChannel());
        Object body = serializer.deserialize(message.getBody());
        System.out.println("主题: " + channel);
        System.out.println("消息内容: " + String.valueOf(body));
    }
}
