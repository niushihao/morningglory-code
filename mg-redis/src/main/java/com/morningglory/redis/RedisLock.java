package com.morningglory.redis;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;

import java.util.*;

/**
 * @Author: qianniu
 * @Date: 2020-04-08 10:27
 * @Desc: redis实现分布式锁
 *
 * 1.利用setnx+expire命令 (错误的做法)
 *  - 如果setnx成功,但是设置expire失败,那么这个key将永远存在,即永远获取不到这把锁
 * 2.使用Lua脚本（包含setnx和expire两条指令）
 * 3.set key value [EX seconds][PX milliseconds][NX|XX]
 *  - EX seconds: 设定过期时间，单位为秒
 *  - PX milliseconds: 设定过期时间，单位为毫秒
 *  - NX: 仅当key不存在时设置值
 *  - XX: 仅当key存在时设置值
 *
 * 2和3在主从模式下也可能有问题,比如向master申请锁成功后,还没同步到salver就宕机了，这是salver选举成新的master
 * ,另一个请求也会在新的master获取这把锁,就会造成两个线程都获取了锁
 * 另外 value最好使用唯一值,在删除时需要判断value是否一致,因为可能出现[线程1]获取到资源后因为超时失效机制,导致key自动失效了,
 * 此时[线程2]获取了锁,如果这时[线程1]执行完了,直接删除的话会把[线程2]的锁也删掉,所以不能简单的直接删除
 *
 * 所以他们又搞了一个更严格的分布式锁的方式,比如集群有N个点,则会向N个点同时发出获取锁的请求,大于半数的获取成功才认为成功,否则失败
 * https://juejin.im/post/5cc165816fb9a03202221dd5
 */
@Slf4j
public class RedisLock {

    private final static String LOCK_KEY1 ="lock1";
    private final static String LOCK_KEY2 ="lock2";
    private final static String LOCK_KEY3 ="lock3";
    private final static int LOCK_TIME = 100;
    private static final String RELEASE_LOCK_SCRIPT = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
    public static void main(String[] args) {

        Jedis jedis = JedisClient.getClientWithDefaultAuth();

        // 利用setnx+expire命令 (错误的做法)
        tryLockWithSetnx(jedis);
        
        // 使用Lua脚本（包含setnx和expire两条指令）
        tryLockWithLua(jedis);

        // set key value [EX seconds][PX milliseconds][NX|XX]
        tryLockWithSet(jedis);

        // 不管上边用哪种方式加锁，删除时都应该判断值是否相等,这里因为值不相等所有的锁不会被删除
        releaseLock(jedis);
    }

    private static void releaseLock(Jedis jedis) {
        Object result = jedis.eval(RELEASE_LOCK_SCRIPT, Collections.singletonList(LOCK_KEY1), Collections.singletonList(UUID.randomUUID().toString()));
        if(result.equals(1L)){
            log.info("releaseLock success");
        }else {
            log.info("releaseLock fail");
        }
    }

    private static void tryLockWithSet(Jedis jedis) {
        String result = jedis.set(LOCK_KEY3, UUID.randomUUID().toString(), "NX", "EX", LOCK_TIME);
        if("OK".equals(result)){
            log.info("tryLockWithSet success");
        }else {
            log.info("tryLockWithSet fail");
        }

    }

    private static void tryLockWithLua(Jedis jedis) {
        String s = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";

        String lua_scripts = "if redis.call('setnx',KEYS[1],ARGV[1]) == 1 then " +
                "redis.call('expire',KEYS[1],ARGV[2]) return 1 else return 0 end";
        List<String> keys = new ArrayList<>();
        List<String> values = new ArrayList<>();
        keys.add(LOCK_KEY2);
        values.add(UUID.randomUUID().toString());
        values.add(String.valueOf(LOCK_TIME));
        Object result = jedis.eval(lua_scripts, keys, values);
        // result == 1 说明设置成功
        if(result.equals(1L)){
            log.info("tryLockWithLua success");
        }else {
            log.info("tryLockWithLua fail");
        }
    }

    private static void tryLockWithSetnx(Jedis jedis) {
        // result == 1 说明设置成功
        Long result = jedis.setnx(LOCK_KEY1, UUID.randomUUID().toString());
        if(result == 1){
            jedis.expire(LOCK_KEY1,LOCK_TIME);
            log.info("tryLockWithSetnx success");
        }else {
            log.info("tryLockWithSetnx fail");
        }
    }
}
