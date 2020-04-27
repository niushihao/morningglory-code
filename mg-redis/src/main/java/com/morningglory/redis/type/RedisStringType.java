package com.morningglory.redis.type;

import com.morningglory.redis.JedisClient;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;

/**
 * @Author: qianniu
 * @Date: 2020-04-08 20:03
 * @Desc:
 */
@Slf4j
public class RedisStringType {

    private final static String NAME_SPACE ="redis:string:";
    private final static String DEFAULT_VALUE = "TEST_STRING";
    private static final String SET_IF_NOT_EXIST = "NX";
    private static final String SET_WITH_EXPIRE_TIME = "EX";
    private static final int TIME_OUT = 1000;
    public static void main(String[] args) {
        Jedis jedis = JedisClient.getClientWithDefaultAuth();

        // 没有超时时间
        String result = jedis.set(NAME_SPACE + "str", DEFAULT_VALUE);
        log.info("set str result = {}",result);
        // 直接设置字节
        result = jedis.set((NAME_SPACE+"str_byte").getBytes(),DEFAULT_VALUE.getBytes());
        log.info("set byte result = {}",result);

        // 当值不存在时返回 'OK',值存在时返回 null
        result = jedis.set(NAME_SPACE + "str_nx", DEFAULT_VALUE, SET_IF_NOT_EXIST);
        log.info("set NX result = {}",result);

        // 分布式锁的是用方式
        result = jedis.set(NAME_SPACE + "str_lock",DEFAULT_VALUE,SET_IF_NOT_EXIST,SET_WITH_EXPIRE_TIME,1000);
        log.info("set lock result = {}",result);

        // 设置过期时间,如果key存在返回1,如果不存在返回 0
        Long expire = jedis.expire(NAME_SPACE + "str", TIME_OUT);
        log.info("expire with exists key result = {}",expire);

        expire = jedis.expire(NAME_SPACE,TIME_OUT);
        log.info("expire without exists key result = {}",expire);

        // 判断单个key是否存在，存在返回true,不存在返回false
        Boolean exists = jedis.exists(NAME_SPACE + "str");
        log.info("exists = {}",exists);

        // 判断多个key是否存在,返回存在的个数,如果传入的多个相同的key 这个方法就有问题了
        Long exists1 = jedis.exists(NAME_SPACE + "str", NAME_SPACE + "str33");
        log.info("exists1 = {}",exists1);

        // key存在返回对应的value,不存在返回null
        String str = jedis.get(NAME_SPACE + "str");
        log.info("get str = {}",str);

    }
}
