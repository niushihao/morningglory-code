package com.morningglory.basic.cache.redis;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.morningglory.basic.pojo.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.*;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

import javax.annotation.Resource;
import java.util.Set;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT, classes = {RedisConfig.class})
@TestPropertySource("classpath:application.yml")
public class RedisTest {

    //reset

    @Autowired
    RedisTemplate redisTemplate;

    @Resource(name = "getJedisPool")
    Jedis jedis;

    //@Resource(name = "getDevJedisPool")
    Jedis devJedis;

    //@Resource(name = "getTestJedisTemplte")
    RedisTemplate testRedisTemplte;

    /*@Autowired
    RedisKeyExpirationEventMessageListener redisKeyExpirationEventMessageListener;
*/
    /*@Autowired
    JedisConnectionFactory jedisConnectionFactory;*/

    @Test
    public void testSentinel(){
        System.out.println("Sentinel");
        redisTemplate.opsForValue().set("test","test");
        Object o = redisTemplate.opsForValue().get("");
        redisTemplate.convertAndSend("testChannel","123");
        System.out.println(redisTemplate.opsForValue().get("test"));

    }

    @Test
    public void testJedis(){
        System.out.println("Jedis");
        jedis.set("jedis","jedis");
        jedis.setex("111",5,"testEx");
    }

    @Test
    public void pub() throws InterruptedException {
        System.out.println("pubAndsub begin");
        jedis.publish("testChannel","first");
        Thread.sleep(5000);

        jedis.publish("testChannel","secend");
        Thread.sleep(5000);

        Long publish = jedis.publish("testChannel", "thread");
        Thread.sleep(5000);
        System.out.println("pubAndsub end");
    }

    @Test
    public void sub() throws InterruptedException {
        System.out.println("sub begin");
        JedisPubSub sub = new JedisPubSub() {
            @Override
            public void onMessage(String channel, String message) {
                System.out.println("onMessage "+channel+" ,message="+message);
                jedis.set("sub","sub");
            }

            @Override
            public void onPMessage(String pattern, String channel, String message){
                System.out.println("pattern "+pattern+" ,onMessage "+channel+" ,message="+message);
            }
        };

       Thread t = new Thread(
               () -> {
                   try {
                       jedis.subscribe(sub,"testChannel");
                       System.out.println("sub end");
                   }finally {
                       jedis.close();
                   }
               }
       );

       t.start();
    }

    @Test
    public void listener() throws InterruptedException {

       /* RedisMessageListener listener = new RedisMessageListener(redisTemplate);

        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(jedisConnectionFactory);
        container.addMessageListener(listener,new PatternTopic("__keyevent@*__:expired"));
        container.afterPropertiesSet();
        container.start();


        Thread.sleep(1000000);*/
    }

    @Test
    public void lock(){
         redisTemplate.execute((RedisCallback<Boolean>) redisConnection -> {
            Jedis jedis = (Jedis) redisConnection.getNativeConnection();
            String result = jedis.set("lock", "_L_", "NX", "PX", 2000);
            System.out.println("*******************result ="+result);
            if ("OK".equals(result)) {
                return Boolean.TRUE;
            }
            return Boolean.FALSE;
        });
    }

    /**
     * dice 开发环境
     * @throws InterruptedException
     */
    @Test
    public void devJedis() throws InterruptedException {
        Runnable runnable = () -> {
            String setex = devJedis.setex("test_nsh" + Math.random(), 10, "test");
            System.out.println("setex =" + setex);
        };



            for (int i = 0; i < 50; i++) {
                Thread.sleep(500);
                Thread t = new Thread(runnable);
                t.start();
            }

    }

    /**
     * 测试环境
     */
    @Test
    public void testEnvJedis(){
        testRedisTemplte.opsForValue().set("test_nsh","test_value",5);
     }

    /**
     * list API
     */
    @Test
    public void testListAPI(){
         ListOperations list = redisTemplate.opsForList();
         // 列表尾部插入
         list.rightPush("list","list1");
         list.rightPush("list","list2");
         list.rightPushAll("list", Lists.newArrayList("list3","list4","list5"));

         // 返回指定列表对应索引的值
         System.out.println(list.index("list",1));

         // 向list指定位置插入
         list.set("list",0,"0");

         //当 key不存在时报错
         //list.set("list1",0,"0");

         //获取列表的长度
         list.size("list");

         //获取指定区间的数据
         System.out.println(list.range("list",0,-1));
    }

    /**
     * map API
     */
    @Test
    public void testHash(){

        HashOperations hash = redisTemplate.opsForHash();
        hash.putIfAbsent("hash","1", JSON.toJSONString(new User()));
        hash.putIfAbsent("hash","2", JSON.toJSONString(new User()));

        //获取map
        System.out.println(hash.entries("hash"));
    }


    @Test
    public void testZset(){
        ZSetOperations zSetOperations = redisTemplate.opsForZSet();
        zSetOperations.add("db","redis",1);
        zSetOperations.add("db","mysql",2);

        Set db = zSetOperations.range("db", 1, 10);
        System.out.println(db);
    }



}
