package com.morningglory.basic.forkJoin;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.TimeUnit;

/**
 * @Author: nsh
 * @Date: 2018/6/28
 * @Description: 快排
 */
public class SortTask extends RecursiveAction{

    final long[] array;
    final int lo;
    final int hi;
    private int THRESHOLD = 4;

    public SortTask(long[] array) {
        this.array = array;
        this.lo = 0;
        this.hi = array.length -1;
    }
    public SortTask(long[] array, int lo, int hi) {
        this.array = array;
        this.lo = lo;
        this.hi = hi;
    }


    @Override
    protected void compute() {
        if(hi - lo < THRESHOLD){
            sequentiallySort(array,lo,hi);
        }else{
            int pivot = partition(array, lo, hi);
            invokeAll(new SortTask(array, lo, pivot - 1), new SortTask(array,
                    pivot + 1, hi));
        }
    }

    private int partition(long[] array, int lo, int hi) {
        long x = array[hi];
        int i = lo - 1;
        for (int j = lo; j < hi; j++) {
            if (array[j] <= x) {
                i++;
                swap(array, i, j);
            }
        }
        swap(array, i + 1, hi);
        return i + 1;
    }

    private void swap(long[] array, int i, int j) {
        if (i != j) {
            long temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
    }

    private void sequentiallySort(long[] array, int lo, int hi) {
        Arrays.sort(array, lo, hi + 1);
    }


    public static void main(String[] args) throws InterruptedException {
        long[] array = {1,3,2,4,6,5,7,9,9,0};
        ForkJoinTask sort = new SortTask(array);
        ForkJoinPool fjpool = new ForkJoinPool();
        fjpool.submit(sort);
        fjpool.shutdown();

        fjpool.awaitTermination(30, TimeUnit.SECONDS);

        List list = Arrays.asList(array);
        System.out.println(list.toString());

    }
}