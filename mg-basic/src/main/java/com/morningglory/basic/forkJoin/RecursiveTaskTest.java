package com.morningglory.basic.forkJoin;

import java.util.concurrent.*;

/**
 * @Author: qianniu
 * @Date: 2019-03-21 13:34
 * @Desc:
 */
public class RecursiveTaskTest extends RecursiveTask {

    private int threshold = 2;

    private int[] array; // the data array

    private int index0 = 0;
    private int index1 = 0;

    public RecursiveTaskTest(int[] array, int index0, int index1) {
        this.array = array;
        this.index0 = index0;
        this.index1 = index1;
    }

    @Override
    protected Object compute() {
        int max = Integer.MIN_VALUE;
        if ((index1 - index0) <= threshold) {

            for (int i = index0;i <= index1; i ++) {
                max = Math.max(max, array[i]);
            }

        } else {
            //fork/join
            int mid = index0 + (index1 - index0) / 2;
            RecursiveTaskTest lMax = new RecursiveTaskTest(array, index0, mid);
            RecursiveTaskTest rMax = new RecursiveTaskTest(array, mid + 1, index1);

            lMax.fork();
            rMax.fork();

            int lm = (int) lMax.join();
            int rm = (int) rMax.join();

            max = Math.max(lm, rm);

        }

        return max;
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {

        ForkJoinPool pool = new ForkJoinPool();

        int[] array = {100,400,200,90,80,300,600,10,20,-10,30,2000,1000};

        RecursiveTaskTest task = new RecursiveTaskTest(array, 0, array.length - 1);

        Future<Integer> future = pool.submit(task);

        System.out.println("Result:" + future.get(1, TimeUnit.SECONDS));
    }
}
