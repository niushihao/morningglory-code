package com.morningglory.basic.concurrent;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;

import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * @Author: qianniu
 * @Date: 2019-03-20 17:27
 * @Desc:
 */
@Slf4j
public class CompletableFutureTest {

    private static Random rand = new Random();
    private static long t = System.currentTimeMillis();
    static int getMoreData(Integer num) {
        System.out.println("begin to start compute");
        try {
            if(num.equals(9)){
                throw new RuntimeException("参数错误");
            }
            Thread.sleep(100);
        } catch (InterruptedException e) {
            System.out.println("执行异常:"+e);
            //throw new RuntimeException(e);
            return -1;
        }catch (Exception e){
            return -1;
        }
        //System.out.println(Thread.currentThread().getName()+" :end to start compute. passed " + (System.currentTimeMillis() - t)/1000 + " seconds");
        return rand.nextInt(100);
    }
    public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {


        CompletableFuture completableFuture = new CompletableFuture();
        completableFuture.whenComplete((v,e) -> {
            log.info("v = {},e = {}",v,e);
        });

        Thread.sleep(3000);
        completableFuture.complete(1);
        List<CompletableFuture<Integer>> futureList = Lists.newArrayList();

        CompletableFuture<Integer> future8 = CompletableFuture.supplyAsync(() -> getMoreData(8)).exceptionally(e -> {
            return -2;
        });
        log.info("future8 = {}",future8.get());

        // exceptionally 可以自己处理异常，比如继续抛出或者返回一个默认值
        CompletableFuture<Integer> future9 = CompletableFuture.supplyAsync(() -> getMoreData(9))
                .exceptionally(e -> {
            log.error(e.getMessage(),e);
            return 9;
        });

        // whenComplete 可以通过 e 是否为null 判断是否发生了异常，但是不能重置返回值
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> getMoreData(9)).whenComplete((v, e) -> {
            if (e != null) {
                log.error(e.getMessage(), e);
                return;
            }
            return;
        });

        CompletableFuture.allOf(future).join();
        System.out.println(future.get());
    }
}
