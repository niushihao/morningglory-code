import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * @author qianniu
 * @date 2022/8/19
 * @desc
 */
public abstract class MgTimerTask implements Runnable{

    /**
     * 0:init
     * 1:SCHEDULED
     * 2:EXECUTED
     * 3:CANCELLED
     */
    private volatile int status;

    private final long expectTime;
    private final TimeUnit unit;

    private long deadline;

    private static final AtomicIntegerFieldUpdater<MgTimerTask> WORKER_STATE_UPDATER =
            AtomicIntegerFieldUpdater.newUpdater(MgTimerTask.class, "status");

    private Object lockData = new Object();

    protected MgTimerTask(long expectTime, TimeUnit unit) {
        this.expectTime = expectTime;
        this.unit = unit;
    }

    @Override
    public abstract void run();

    protected boolean cancel(){
        return WORKER_STATE_UPDATER.compareAndSet(this,0,3);
    }


    public boolean isCanceled(){
        return 3 == status;
    }
}
