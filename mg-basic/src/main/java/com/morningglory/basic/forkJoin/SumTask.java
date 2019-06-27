package com.morningglory.basic.forkJoin;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * @Author: qianniu
 * @Date: 2019-04-02 10:18
 * @Desc:
 */
public class SumTask extends RecursiveTask<Long> {

    private Long sum = 0L;

    private int begin,end;

    public SumTask(int begin, int end) {
        this.begin = begin;
        this.end = end;
    }

    @Override
    protected Long compute() {
        System.out.println("执行的线程 "+Thread.currentThread().getName());
        if(end - begin <= 10){
            /*try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
            for(int i = begin; i< end;i++){
                sum += i;
            }
        }else{
            System.out.println("需要拆分");
            int mid = (end+begin)/2;

            SumTask left = new SumTask(begin,mid);
            //left.fork();

            SumTask right = new SumTask(mid,end);
            //right.fork();
            invokeAll(left,right);

            Long ll = left.join();
            Long lr = right.join();
            sum = ll + lr;
            System.out.println("sum = "+sum);
        }
        return sum;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ForkJoinPool forkJoinPool1 = new ForkJoinPool(4);
        ForkJoinTask<Long> ft1 = forkJoinPool1.submit(new SumTask(0, 10000));

        //ForkJoinPool forkJoinPool2 = new ForkJoinPool();
        ForkJoinTask<Long> ft2 = forkJoinPool1.submit(new SumTask(0, 10000));

        System.out.println("计算结果是："+ft1.get());
        System.out.println("计算结果是："+ft2.get());
        forkJoinPool1.shutdown();

        int count = 0;
        for(int i =0; i< 10000;i++){
            count += i;
        }
        System.out.println("count ="+count);
    }
}
