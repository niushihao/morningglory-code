import java.util.concurrent.TimeUnit;

/**
 * @author qianniu
 * @date 2022/8/19
 * @desc
 */
public interface MgTimer {

    void addTimerTask(MgTimerTask timerTask,long delay, TimeUnit unit);
}
