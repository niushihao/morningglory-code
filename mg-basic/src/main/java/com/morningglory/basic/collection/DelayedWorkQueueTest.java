package com.morningglory.basic.collection;

/**
 * @Author: qianniu
 * @Date: 2019-04-18 10:24
 * @Desc:
 */
public class DelayedWorkQueueTest {

    Integer[] queue = new Integer[16];

    int size = 0;

    public static void main(String[] args) {
        DelayedWorkQueueTest array = new DelayedWorkQueueTest();
        array.add(5);
        array.add(4);
        array.add(10);
        array.add(6);
        /*System.out.println(array.take());
        System.out.println(array.take());
        System.out.println(array.take());
        System.out.println(array.take());*/
        for(int i : array.queue){
            System.out.println(i);
        }

    }

    boolean add(Integer e) {
        if (e == null)
            throw new NullPointerException();
        int i = size;
        size = i + 1;
        if (i == 0) {
            queue[0] = e;
        } else {
            siftUp(i, e);
        }
        return true;
    }

    Integer take() {
        Integer i = queue[0];
        int s = --size;
        Integer k = queue[s];
        if (size != 0)
            siftDown(0, k);
        return i;
    }

    private void siftUp(int k, Integer key) {
        while (k > 0) {
            int parent = (k - 1) >>> 1;
            Integer e = queue[parent];
            if (key.compareTo(e) >= 0)
                break;
            queue[k] = e;
            k = parent;
        }
        queue[k] = key;
    }

    private void siftDown(int k, Integer key) {
        int half = size >>> 1;
        while (k < half) {
            int child = (k << 1) + 1;
            Integer c = queue[child];
            int right = child + 1;
            if (right < size && c.compareTo(queue[right]) > 0)
                c = queue[child = right];
            if (key.compareTo(c) <= 0)
                break;
            queue[k] = c;
            k = child;
        }
        queue[k] = key;
    }
}
