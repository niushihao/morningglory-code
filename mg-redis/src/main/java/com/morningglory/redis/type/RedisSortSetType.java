package com.morningglory.redis.type;

import com.google.common.collect.Maps;
import com.morningglory.redis.JedisClient;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;

import java.util.Map;
import java.util.Set;

/**
 * @Author: qianniu
 * @Date: 2020-04-09 13:40
 * @Desc:
 */
@Slf4j
public class RedisSortSetType {

    private final static String NAME_SPACE ="redis:zset:";
    private final static String DEFAULT_VALUE = "TEST_ZSET";
    private static final int TIME_OUT = 10;

    public static void main(String[] args) {
        Jedis jedis = JedisClient.getClientWithDefaultAuth();

        // 向集合添加值
        Map<String,Double> map = Maps.newHashMap();
        map.put("晓明",10.0);
        map.put("小红",8.7);
        map.put("小白",9.0);
        Long zadd = jedis.zadd(NAME_SPACE + "zadd", map);
        log.info("zadd = {}",zadd);

        // 返回集合中的个数
        Long zcard = jedis.zcard(NAME_SPACE + "zadd");
        log.info("zcard = {}",zcard);

        // 返回集合中分数在指定范围的数量
        Long zcount = jedis.zcount(NAME_SPACE + "zadd", 9.0, 10.0);
        log.info("zcount = {}",zcount);

        // 向指定元素 增量设置分值,返回最新的分值
        Double zincrby = jedis.zincrby(NAME_SPACE + "zadd", 5.0, "小白");
        log.info("zincrby = {}",zincrby);

        // 返回集合中指定范围的数据,按分数升序排
        Set<String> zrange = jedis.zrange(NAME_SPACE + "zadd", 0, 2);
        log.info("zrange = {}",zrange);

        // 返回集合中指定分数范围的数据
        Set<String> zrangeByScore = jedis.zrangeByScore(NAME_SPACE + "zadd", 5.0, 10.0);
        log.info("zrangeByScore = {}",zrangeByScore);

        // 返回元素在集合中的排名(也就是索引位置)
        Long zrank = jedis.zrank(NAME_SPACE + "zadd", "晓明");
        log.info("zrank = {}",zrank);

        // 移除集合中的元素,类似的还有 zremrangeByRank、zremrangeByScore
        Long zrem = jedis.zrem(NAME_SPACE + "zadd", "晓明");
        log.info("zrem = {}",zrem);



    }
}
