package com.morningglory.redis;

import redis.clients.jedis.Jedis;

import java.util.List;

/**
 * @Author: qianniu
 * @Date: 2020-04-08 19:54
 * @Desc:
 */
public class JedisClient {

    private static Jedis CLIENT  = new Jedis("127.0.0.1", 6379, 400000);

    public static Jedis getClientWithAuth(String password){
        CLIENT.auth(password);
        return CLIENT;
    }

    public static Jedis getNewClientWithAuth(String password){
        Jedis CLIENT  = new Jedis("127.0.0.1", 6379, 400000);
        CLIENT.auth(password);
        return CLIENT;
    }

    public static Jedis getClientWithDefaultAuth(){
        CLIENT.auth("123456");
        return CLIENT;
    }

    public static void configEvent(Jedis jedis){
        // 获取redis中 事件通知的配置
        String parameter = "notify-keyspace-events";
        List<String> notify = jedis.configGet(parameter);
        // 如果配置文件没有指定则通过程序指定
        if(notify.get(1).equals("")){
            jedis.configSet(parameter, "KEA");
        }
    }

}
