package com.morningglory.redis.type;

import com.google.common.collect.Maps;
import com.morningglory.redis.JedisClient;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Map;

/**
 * @Author: qianniu
 * @Date: 2020-04-08 20:38
 * @Desc:
 * https://www.cnblogs.com/Laymen/p/6122108.html
 */
@Slf4j
public class RedisHashType {

    private final static String NAME_SPACE ="redis:hash:";
    private final static String DEFAULT_VALUE = "TEST_HASH";
    private static final int TIME_OUT = 1000;
    public static void main(String[] args) {

        Jedis jedis = JedisClient.getClientWithDefaultAuth();
        // 设置 属性对应的值,如果属性不存在 则返回1 如果属性存则覆盖旧值 返回0
        Long id = jedis.hset(NAME_SPACE+"hset", "id", DEFAULT_VALUE);
        log.info("hset id result = {}",id);
        Long name = jedis.hset(NAME_SPACE+"hset", "name", DEFAULT_VALUE);
        jedis.hset(NAME_SPACE+"hset", "sex", DEFAULT_VALUE);
        log.info("hset name result = {}",id);

        // 批量设置
        Map<String,String> map = Maps.newHashMap();
        map.put("id","1");
        map.put("name","晓明");
        map.put("sex","man");
        String hmset = jedis.hmset(NAME_SPACE + "hmset", map);
        log.info("hmset  result = {}",id);

        // 批量获取
        List<String> hmget = jedis.hmget(NAME_SPACE + "hmset", "id", "name");
        log.info("hmget = {}",hmget);

        Map<String, String> hgetAll = jedis.hgetAll(NAME_SPACE + "hmset");
        log.info("hgetAll = {}",hgetAll);

        // 当filed 不存在时才设置
        jedis.hsetnx(NAME_SPACE+"hsetnx","id","1");
        jedis.hsetnx(NAME_SPACE+"hsetnx","name",DEFAULT_VALUE);
        jedis.hsetnx(NAME_SPACE+"hsetnx","sex",DEFAULT_VALUE);
        
        // 设置过期时间,不能设置单个属性的过期时间
        Long expire = jedis.expire(NAME_SPACE + "hset", TIME_OUT);
        log.info("expire = {}",expire);

        // 获取指定属性的值,key或者filed不存在都返回null
        String idInfo = jedis.hget(NAME_SPACE + "hset", "id");
        log.info("hget id = {}",idInfo);

        // 判断某个属性是否存在
        Boolean exists = jedis.hexists(NAME_SPACE + "hset", "id");
        log.info("hexists id = {}",exists);

        // 删除hash中的属性,如果属性都删完了,这个key也会被删除
        Long hdel = jedis.hdel(NAME_SPACE + "hset", "id", "name");
        log.info("hdel = {}",hdel);


    }
}
