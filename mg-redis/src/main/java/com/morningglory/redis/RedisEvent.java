package com.morningglory.redis;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

import java.util.List;


/**
 * @Author: qianniu
 * @Date: 2020-04-10 13:31
 * @Desc: 测试redis的事件通知
 * __keyspace@0__:mykey del
 *  - 键-空间信道以消息的方式接收事件名称
 *  - 也可以理解为这个是监听某个或某些key发生的事件（监听[mykey]的[删除]事件）
 *  - onPMessage方法的message = del
 *
 * __keyevent@0__:del mykey
 *  - 键-事件信道以消息的方式接收键名称
 *  - 也可以理解为这个是监听某个事件发生的key（监听[删除]事件中key是[mykey]的事件）
 *  - onPMessage方法的message = mykey
 *
 * 上边是官网给的两个例子,一般只需要指定一个就行,不需要都开启
 *
 * 默认情况下，键空间事件通知是被禁用的，因为这个功能会消耗一些CPU，
 * 可以在redis.conf文件中使用notify-keyspace-events或CONFIG SET命令启用它
 *
 * K     键空间事件，以__keyspace@<db>__为前缀发布消息
 * E     键事件事件，以__keyevent@<db>__为前缀发布消息
 * g     通用命令（非特定类型），例如 DEL、 EXPIRE、 RENAME等
 * $     字符串命令
 * l     列表命令
 * s     集合命令
 * h     哈希命令
 * z     有序集合命令
 * x     键过期事件（每次当一个键过期时生成的事件）
 * e     键淘汰事件（当一个键由于内存超量导致被淘汰时生成的事件）
 * A     参数g$lshzxe的别名，因此"AKE"字符串表示所有的事件
 *
 * K或E至少有一个应该出现在参数字符串中，否则，无论字符串的其他部分是什么，都不会推送事件
 */
@Slf4j
public class RedisEvent {

    // 所有key的所有事件
    private final static String ALL = "__key*__:*";

    // 所有key的过期事件
    private final static String ONLY_EXPIRE = "__keyevent@*__:expired";

    // 指定key的所有事件
    private final static String ONLY_KEY = "__keyspace@*__:mykey";

    private final static String NAME_SPACE ="redis:event:event";
    public static void main(String[] args) {
        Jedis jedis = JedisClient.getClientWithDefaultAuth();

        // 获取redis中 事件通知的配置
        String parameter = "notify-keyspace-events";
        List<String> notify = jedis.configGet(parameter);
        // 如果配置文件没有指定则通过程序指定
        if(notify.get(1).equals("")){
            // 这里设置值监听keyevent
            jedis.configSet(parameter, "EA");
        }

        Runnable runnable = () -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            jedis.psubscribe(new KeyExpiredListener(),ALL);
            log.info("psubscribe success");
        };
        new Thread(runnable).start();


        jedis.set(NAME_SPACE,"123");
        jedis.expire(NAME_SPACE,5);
        log.info("set success");

    }

    public static class KeyExpiredListener extends JedisPubSub {

        @Override
        public void onPSubscribe(String pattern, int subscribedChannels) {
            log.info("onPSubscribe pattern ={},subscribedChannels = {}",pattern,subscribedChannels);
        }

        @Override
        public void onPMessage(String pattern, String channel, String message) {
            log.info("onPMessage pattern ={},channel ={},message ={}",pattern,channel,message);
        }

    }
}
