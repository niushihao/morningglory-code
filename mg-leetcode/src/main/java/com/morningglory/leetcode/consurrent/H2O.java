package com.morningglory.leetcode.consurrent;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * @author qianniu
 * @date 2020/6/23 9:23 上午
 * @desc
 * 现在有两种线程，氧 oxygen 和氢 hydrogen，你的目标是组织这两种线程来产生水分子。
 *
 * 存在一个屏障（barrier）使得每个线程必须等候直到一个完整水分子能够被产生出来。
 *
 * 氢和氧线程会被分别给予 releaseHydrogen 和 releaseOxygen 方法来允许它们突破屏障。
 *
 * 这些线程应该三三成组突破屏障并能立即组合产生一个水分子。
 *
 * 你必须保证产生一个水分子所需线程的结合必须发生在下一个水分子产生之前。
 *
 * 换句话说:
 *
 * 如果一个氧线程到达屏障时没有氢线程到达，它必须等候直到两个氢线程到达。
 * 如果一个氢线程到达屏障时没有其它线程到达，它必须等候直到一个氧线程和另一个氢线程到达。
 * 书写满足这些限制条件的氢、氧线程同步代码。
 *
 *  
 *
 * 示例 1:
 *
 * 输入: "HOH"
 * 输出: "HHO"
 * 解释: "HOH" 和 "OHH" 依然都是有效解。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/building-h2o
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
@Slf4j
public class H2O {

    public static void main(String[] args) {

        Semaphore semaphoreH = new Semaphore(2);
        Semaphore semaphoreO = new Semaphore(1);

        CyclicBarrier barrier = new CyclicBarrier(3,() -> {
            semaphoreH.release(2);
            semaphoreO.release(1);
            log.info("H2O以到达。。");
        });

        Runnable hydrogenTask = () -> {
            try {
                semaphoreH.acquire();
                log.info("H");
                barrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }

        };

        Runnable oxygenTask = () -> {
            try {
                semaphoreO.acquire();
                log.info("O");
                barrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }

        };

        ThreadPoolExecutor executor = new ThreadPoolExecutor(10,10,0, TimeUnit.SECONDS,new LinkedBlockingDeque<>());
        executor.execute(oxygenTask);
        executor.execute(oxygenTask);
        executor.execute(oxygenTask);
        executor.execute(hydrogenTask);
        executor.execute(hydrogenTask);
        executor.execute(hydrogenTask);
        executor.execute(hydrogenTask);
        executor.execute(hydrogenTask);
        executor.execute(hydrogenTask);


        executor.shutdown();
    }

    public void hydrogen(Runnable releaseHydrogen) throws InterruptedException {

        // releaseHydrogen.run() outputs "H". Do not change or remove this line.
        releaseHydrogen.run();
    }

    public void oxygen(Runnable releaseOxygen) throws InterruptedException {

        // releaseOxygen.run() outputs "O". Do not change or remove this line.
        releaseOxygen.run();
    }
}
