package com.morningglory.basic.timeWheel;

import java.util.LinkedList;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;

/**
 * @author qianniu
 * @date 2020/8/7 12:47 下午
 * @desc
 */
public class Bucket implements Delayed {

    /**
     * 当前槽的过期时间
     */
    private AtomicLong expiration = new AtomicLong(-1L);

    LinkedList taskList = new LinkedList();
    private TimedTask root = new TimedTask(-1,null);
    {
        root.pre = root;
        root.next = root;
    }

    public boolean setExpire(long expire){
        return expiration.getAndSet(expire) != expire;
    }

    /**
     * 获取某个槽的过期时间
     */
    public long getExpire() {
        return expiration.get();
    }

    public void addTask(TimedTask timedTask){
        synchronized (taskList){
            taskList.add(timedTask);
        }
    }

    public void removeTask(TimedTask timedTask){
        synchronized(taskList){
            taskList.remove(timedTask);
        }
    }

    /**
     * 重新分配槽
     */
    public synchronized void flush(Consumer<TimedTask> flush) {
        TimedTask timedTask = root.next;// 从尾巴开始（最先加进去的）

        while (!timedTask.equals(root)) {
            this.removeTask(timedTask);
            flush.accept(timedTask);
            timedTask = root.next;
        }
        expiration.set(-1L);
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return Math.max(0, unit.convert(expiration.get() - System.currentTimeMillis(), TimeUnit.MILLISECONDS));
    }

    @Override
    public int compareTo(Delayed o) {
        if (o instanceof Bucket) {
            return Long.compare(expiration.get(), ((Bucket) o).expiration.get());
        }
        return 0;
    }
}
