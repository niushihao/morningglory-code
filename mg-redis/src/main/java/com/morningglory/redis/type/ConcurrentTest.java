package com.morningglory.redis.type;

import com.google.common.collect.Lists;
import com.morningglory.redis.JedisClient;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author qianniu
 * @date 2020/11/9 1:01 下午
 * @desc 新建10个客户端，每个
 */
public class ConcurrentTest {

    private static ExecutorService executorService = Executors.newFixedThreadPool(100);
    private static CyclicBarrier cyclicBarrier = new CyclicBarrier(100,() ->{
        System.out.println("init end");
    });
    public static void main(String[] args) throws InterruptedException {

        Jedis jedis = JedisClient.getNewClientWithAuth("123456");
        jedis.set("test_key","test_str");

        for(int i=0;i<100;i++){
            Jedis tempJedis = JedisClient.getNewClientWithAuth("123456");
            int j = i;
            Runnable runnable = () -> {
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
                Long test_key = tempJedis.append("test_key", j + "_");
                System.out.println("test_key = "+test_key);
            };
            executorService.execute(runnable);
        }
        Thread.sleep(2000);

        jedis = JedisClient.getNewClientWithAuth("123456");
        String test_key = jedis.get("test_key");
        System.out.println("test_key="+test_key);
    }
}
