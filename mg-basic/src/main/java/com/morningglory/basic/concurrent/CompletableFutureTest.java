package com.morningglory.basic.concurrent;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
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
            Thread.sleep(1000);
        } catch (Exception e) {
            System.out.println("执行异常:"+e.getMessage());
            throw new RuntimeException(e);
        }
        //System.out.println(Thread.currentThread().getName()+" :end to start compute. passed " + (System.currentTimeMillis() - t)/1000 + " seconds");
        return rand.nextInt(100);
    }
    public static void main(String[] args) throws InterruptedException, ExecutionException {


        CompletableFuture completableFuture = new CompletableFuture();
        completableFuture.whenComplete((v,e) -> {
            log.info("v = {},e = {}",v,e);
        });

        Thread.sleep(3000);
        completableFuture.complete(1);
        List<CompletableFuture<Integer>> futureList = Lists.newArrayList();

//        CompletableFuture<Void> voidCompletableFuture = CompletableFuture.runAsync(() -> System.out.println(111));
//        voidCompletableFuture.whenComplete((v,e) -> {
//
//        });
//        for(int i =1;i<11;i++){
//            int finalI = i;
//            CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
//                return getMoreData(finalI);
//            });
//            futureList.add(future);
//
//        }
//
//        // CompletableFuture::join不会显示错误信息,结果只返回10条
//        List<Integer> collect = futureList.stream().map(CompletableFuture::join).collect(Collectors.toList());
//        System.out.println(collect.size());

//        for(CompletableFuture<Integer> future : futureList){
//            Integer integer = future.get();
//            System.out.println(Thread.currentThread().getName()+" ,getValue ="+integer);
//            future.whenComplete((v,e) -> {
//                System.out.println(Thread.currentThread().getName()+" ,error ="+e+",value ="+v);
//            }) ;
//
//            future.exceptionally(e -> {
//                e.printStackTrace();
//                return null;
//            });
//
//        }
//        futureList.stream().forEach(future -> {
//            future.whenComplete((v,e) -> {
//                System.out.println("error ="+e+",value ="+v);
//            }) ;
//        });
//
        Thread.sleep(10000);


//        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> getMoreData(1));
//        Future<Integer> f = future.whenComplete((v, e) -> {
//            System.out.println(v);
//            System.out.println(e);
//        });
//        System.out.println(f.get());
//        System.in.read();
    }
}
