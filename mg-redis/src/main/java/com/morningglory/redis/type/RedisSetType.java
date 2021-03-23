package com.morningglory.redis.type;

import com.morningglory.redis.JedisClient;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;

import java.util.Set;

/**
 * @Author: qianniu
 * @Date: 2020-04-09 12:59
 * @Desc:
 * https://www.cnblogs.com/Laymen/p/6122935.html
 */
@Slf4j
public class RedisSetType {

    private final static String NAME_SPACE ="redis:set:";
    private final static String DEFAULT_VALUE = "TEST_SET";
    private static final int TIME_OUT = 10;

    public static void main(String[] args) {
        Jedis jedis = JedisClient.getClientWithDefaultAuth();

        // 将一个或多个值放入对应的key中,返回影响的行数
        Long sadd = jedis.sadd(NAME_SPACE + "sadd", "1", "2", "3","4");
        Long sadd1 = jedis.sadd(NAME_SPACE + "sadd1", "1", "2");
        log.info("sadd = {}",sadd);

        // 返回集合中元素的个数
        Long scard = jedis.scard(NAME_SPACE + "sadd");
        log.info("scard = {}",scard);

        // 返回多个集合的差集
        Set<String> sdiff = jedis.sdiff(NAME_SPACE + "sadd", NAME_SPACE + "sadd1");
        log.info("sdiff = {}",sdiff);

        // 返回多个集合的交集
        Set<String> sinter = jedis.sinter(NAME_SPACE + "sadd", NAME_SPACE + "sadd1");
        log.info("sinter = {}",sinter);

        // 返回多个集合的并集
        Set<String> sunion = jedis.sunion(NAME_SPACE + "sadd", NAME_SPACE + "sadd1");
        log.info("sunion = {}",sunion);

        // 判断集合中是否包含此元素
        Boolean sismember = jedis.sismember(NAME_SPACE + "sadd", "1");
        log.info("sismember = {}",sismember);

        // 返回集合中的所有元素
        Set<String> smembers = jedis.smembers(NAME_SPACE + "sadd");
        log.info("smembers = {}",smembers);

        // 将src集合的数据移动到 dst集合中
        Long smove = jedis.smove(NAME_SPACE + "sadd", NAME_SPACE + "sadd1", "1");
        log.info("smove = {}",smove);

        // 随机返回集合中的一个元素
        String srandmember = jedis.srandmember(NAME_SPACE + "sadd");
        log.info("srandmember = {}",srandmember);

        // 随机删除并返回一个元素
        String spop = jedis.spop(NAME_SPACE + "sadd");
        log.info("spop = {}",spop);

        // 删除集合中指定的元素
        Long srem = jedis.srem(NAME_SPACE + "sadd", "1");
        log.info("srem = {}",srem);

        jedis.sadd(NAME_SPACE + "sadd", "5", "2", "4","3","6");
    }
}
