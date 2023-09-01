import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author qianniu
 * @date 2022/8/23
 * @desc
 */
@Slf4j
public class MgHashWheelTimer implements MgTimer{

    private final long tickDuration;
    private final int mask;
    private final HashedWheelBucket[] wheel;
    private final long maxPendingTimeouts;

    private Worker worker = new Worker();
    private final Thread workerThread ;

    private final AtomicLong pendingTask = new AtomicLong(0);

    private volatile long startTime;
    private final Queue<MgTimerTask> timeouts = new LinkedBlockingQueue<>();
    private volatile boolean init = false;

    private static final AtomicInteger INSTANCE_COUNTER = new AtomicInteger();

    public MgHashWheelTimer(long tickDuration, TimeUnit unit){
        this.tickDuration = unit.toNanos(tickDuration);
        wheel = createWheel(512);
        this.mask = wheel.length - 1;

        if(tickDuration > Integer.MAX_VALUE / wheel.length){
            throw new IllegalArgumentException(String.format(
                    "tickDuration: %d (expected: 0 < tickDuration in nanos < %d",
                    tickDuration, Long.MAX_VALUE / wheel.length));
        }

        this.workerThread = new Thread(worker,"test");
        this.maxPendingTimeouts = Integer.MAX_VALUE;

        if(INSTANCE_COUNTER.incrementAndGet() >= 64){
            log.error("You are creating too many  instances. is a shared resource that must be reused across the JVM," +
                    "so that only a few instances are created.");
        }
    }


    @Override
    public void addTimerTask(MgTimerTask timerTask, long delay, TimeUnit unit) {
        Feature
        if(Objects.isNull(timerTask) || Objects.isNull(unit)){
            return;
        }
        long pendingTaskNum = pendingTask.incrementAndGet();
        if(pendingTaskNum >= maxPendingTimeouts){
            throw new RejectedExecutionException("Number of pending timeouts ("
                    + pendingTaskNum + ") is greater than or equal to maximum allowed pending "
                    + "timeouts (" + maxPendingTimeouts + ")");
        }

        start();

        long deadline = unit.toNanos(delay) - System.nanoTime() - unit.toNanos(delay) - startTime;
        timeouts.add(timerTask);
    }

    private void start(){
        if(init){
            return;
        }
        synchronized (this){
            if(init){
                return;
            }
            init = true;
            startTime = System.nanoTime();
            workerThread.start();
        }

    }


    private class Worker implements Runnable{

        private long tick;

        @Override
        public void run() {

            while (init){
                long deadline = waitForNextTick();

                int index = (int) (tick & mask);
                HashedWheelBucket hashedWheelBucket = wheel[index];

                transferTaskToBuckets();
            }
        }

        private void transferTaskToBuckets() {

            for(int i=0;i<100000;i++){
                MgTimerTask task = timeouts.poll();
                if(Objects.isNull(task)){
                    break;
                }

                if(task.isCanceled()){
                    continue;
                }

                task.

            }
        }

        private long waitForNextTick() {
            final long currentTime = System.nanoTime() - startTime;
            long deadline = tickDuration * (tick + 1);
            while (true){
                long sleepTime = deadline - currentTime;
                if(sleepTime <= 0){
                    return currentTime;
                }

                try {
                    Thread.sleep(currentTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return -1;
                }
            }
        }
    }

    private class HashedWheelBucket{
        private final Queue<MgTimerTask> timeouts = new LinkedBlockingQueue<>();
    }


    private HashedWheelBucket[] createWheel(int ticksPerWheel) {
        HashedWheelBucket[] wheelBuckets = new HashedWheelBucket[ticksPerWheel];
        for(int i=0;i< ticksPerWheel;i++){
            wheelBuckets[i] = new HashedWheelBucket();
        }
        return wheelBuckets;
    }
}
