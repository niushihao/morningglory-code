package com.morningglory.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @Author: qianniu
 * @Date: 2020-04-10 13:22
 * @Desc:
 */
public class JedisPoolTest {

    public static void main(String[] args) {

        JedisPool pool = new JedisPool();
        Jedis resource = pool.getResource();

    }
}
