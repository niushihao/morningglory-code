package com.morningglory.job.elastic;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author qianniu
 * @date 2021/3/19 10:21 上午
 * @desc
 */
@Slf4j
public class Demo {

    private static final Map<String, Queue<Integer>> queues = Maps.newConcurrentMap();

    public static void main(String[] args) {

        queues.put("project",new LinkedBlockingQueue<>());

        Runnable tack = () -> {
            while (true){
                Integer project = Optional.ofNullable(queues.get("project")).map(Queue::poll).orElse(null);
                log.info("project = {},size = {}",project,queues.get("project").size());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        };

        Runnable put = () -> {
            int i = 0;
            while (true){
                queues.get("project").add(i++);
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };


        new Thread(tack,"demo1").start();
        new Thread(tack,"demo2").start();
        new Thread(put,"put").start();

        while (queues.size() >0){

        }

    }
}
