package com.morningglory.redis;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.BitOP;
import redis.clients.jedis.Jedis;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * @Author: qianniu
 * @Date: 2020-04-09 14:13
 * @Desc: bit用的是字符串类型,他有一套单独的命令 用来设置 [位] 的值
 * https://juejin.im/post/5a7dcad0f265da4e6f17d942
 */
@Slf4j
public class RedisBitMap {

    private final static String NAME_SPACE ="redis:bit:";
    public static void main(String[] args) {
        Jedis jedis = JedisClient.getClientWithDefaultAuth();

        // 设置指定偏移量的值
        Boolean setbit = jedis.setbit(NAME_SPACE + "nsh", 1, true);
        jedis.setbit(NAME_SPACE+"nsh",2,false);
        jedis.setbit(NAME_SPACE+"nsh",3,true);
        jedis.setbit(NAME_SPACE+"nsh1",1,false);
        jedis.setbit(NAME_SPACE+"nsh1",2,true);
        jedis.setbit(NAME_SPACE+"nsh1",3,true);
        log.info("setbit = {}",setbit);

        // 获取指定偏移量的值
        Boolean getbit = jedis.getbit(NAME_SPACE + "nsh", 1);
        log.info("getbit = {}",getbit);

        // 获取被设置为1的数量
        Long bitcount = jedis.bitcount(NAME_SPACE + "nsh");
        log.info("bitcount = {}",bitcount);

        // 获取指定范围被设置为1的数量
        Long bitcountRange = jedis.bitcount(NAME_SPACE + "nsh", 0, 1);
        log.info("bitcountRange = {}",bitcountRange);

        /**
         * 对多个bitMap进行 [与] 操作 ，
         * 两个key offset =1 的位置一个为true一个为false [与] 操作后 返回false
         * 并将结果记录到 destKey 中
         * */
        Long bitopAnd = jedis.bitop(BitOP.AND, NAME_SPACE + "nsh2", NAME_SPACE + "nsh", NAME_SPACE + "nsh1");
        Boolean getbitAfterAnd = jedis.getbit(NAME_SPACE + "nsh2", 1);
        log.info("getbitAfterAnd = {}",getbitAfterAnd);

        /**
         * 对多个bitMap进行 [或] 操作 ，
         * 两个key offset =1 的位置一个为true一个为false [或] 操作后 返回true
         * 并将结果记录到 destKey 中
         * */
        jedis.bitop(BitOP.OR,NAME_SPACE + "nsh2",NAME_SPACE + "nsh", NAME_SPACE + "nsh1");
        Boolean getbitAfterOr = jedis.getbit(NAME_SPACE + "nsh2", 1);
        log.info("getbitAfterOr = {}",getbitAfterOr);

        /**
         * 对一个bitMap指定offset 进行 [非] 操作
         */
        jedis.bitop(BitOP.NOT, NAME_SPACE + "nsh2", NAME_SPACE + "nsh");
        Boolean getbitAfterNot = jedis.getbit(NAME_SPACE + "nsh2", 1);
        log.info("getbitAfterNot = {}",getbitAfterNot);

        // 返回bitMap中 第一个设置为true的偏移量
        Long bitpos = jedis.bitpos(NAME_SPACE + "nsh", true);
        log.info("bitpos = {}",bitpos);

        // 使用bitMap 做用户签到处理
        userSignIn(jedis);

        // 使用bitMap 统计活跃用户
        activeUser(jedis);
    }

    /**
     * 这里以时间作为key,userId作为offset
     * @param jedis
     */
    private static void activeUser(Jedis jedis) {

        initData(jedis);
        log.info("********************活跃统计场景开始***************************");
        // 统计时间范围的活跃用户
        jedis.bitop(BitOP.OR,NAME_SPACE+"active",NAME_SPACE+"2020-01-01",NAME_SPACE+"2020-01-02",NAME_SPACE+"2020-01-03");
        Long active = jedis.bitcount(NAME_SPACE+"active");
        log.info("一号到三号 活跃用户为数 ={}",active);

        // 统计连续三天都活跃的用户数量
        jedis.bitop(BitOP.AND,NAME_SPACE+"active1",NAME_SPACE+"2020-01-01",NAME_SPACE+"2020-01-02",NAME_SPACE+"2020-01-03");
        Long active1 = jedis.bitcount(NAME_SPACE+"active1");
        log.info("一号到三号 一直活跃的用户数 ={}",active1);
        log.info("********************活跃统计场景开始***************************");
    }


    /**
     * 这里是以 userId作为key 时间作为offset
     * @param jedis
     */
    private static void userSignIn(Jedis jedis) {
        log.info("********************签到场景开始***************************");
        Long userId = 1L;
        String cacheKey = "sign"+userId;

        // 开始有签到功能的日志
        long beginDate = LocalDate.of(2020, 1, 1).toEpochDay();

        // 今天的日志
        long nowDate = LocalDate.now().toEpochDay();

        long offset = nowDate - beginDate;
        log.info("beginDate = {},nowDate = {},offset = {}",beginDate,nowDate,offset);

        // 签到,一年一个用户会占用多少空间呢？大约365/8=45.625个字节
        jedis.setbit(cacheKey,offset,true);

        // 查询签到情况
        Boolean getbit = jedis.getbit(cacheKey, offset);
        log.info("用户今天的签到情况为 ={}",getbit);

        // 统计用户的签到次数
        Long bitcount = jedis.bitcount(cacheKey);
        log.info("用户签到次数为 ={}",bitcount);
        log.info("********************签到场景结束***************************");
    }

    // 模拟一段时间内登陆的用户
    private static void initData(Jedis jedis) {
        Map<String, List<Integer>> map = Maps.newHashMap();
        map.put("2020-01-01", Lists.newArrayList(1,5));
        map.put("2020-01-02",Lists.newArrayList(1,2,5,6,8));
        map.put("2020-01-03",Lists.newArrayList(1,3,5,7,9));
        map.forEach((k,v)->{
            v.forEach(t -> {
                // 设置这个日志 这个用户的登陆情况
                jedis.setbit(NAME_SPACE+k,t,true);
            });
        });
    }
}
