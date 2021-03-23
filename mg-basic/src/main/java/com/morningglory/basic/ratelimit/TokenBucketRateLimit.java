package com.morningglory.basic.ratelimit;

import com.morningglory.basic.concurrent.thread.NamedThreadFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.concurrent.*;

/**
 * @author qianniu
 * @date 2020/11/25 6:52 下午
 * @desc 令牌桶限流
 */
@Slf4j
public class TokenBucketRateLimit implements RateLimit,Runnable{
    /**
     * token 生成 速率 （每秒）
     */
    private Integer tokenLimitSecond;

    /**
     * 令牌桶队列
     */
    private BlockingQueue<String /* token */> tokenBucket;

    private static final String TOKEN = "__token__";

    private ScheduledExecutorService scheduledExecutorService;

    public TokenBucketRateLimit(Integer tokenLimitSecond,Integer bucketSize) {
        this.tokenLimitSecond = tokenLimitSecond;
        this.tokenBucket = new LinkedBlockingQueue<>(bucketSize);
        this.startResetTask();
    }

    private void startResetTask() {
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor(new NamedThreadFactory("startResetTask"));
        long interval = (1000 * 1000 * 1000) / tokenLimitSecond;
        log.info("startResetTask interval = {}",interval);
        scheduledExecutorService.scheduleAtFixedRate(this, 0, interval, TimeUnit.NANOSECONDS);
    }

    @Override
    public boolean canPass() throws BlockException {
        String token = tokenBucket.poll();
        if (StringUtils.isEmpty(token)) {
            log.error("获取令牌失败。。。");
            throw new BlockException();
        }
        log.info("成功获取令牌");
        return true;
    }

    @Override
    public void run() {

        boolean offer = tokenBucket.offer(TOKEN);
        if(offer){
            log.info("放入令牌");
        }else {
            log.info("令牌桶满了,丢弃。。。");
        }

    }

    public static void main(String[] args) {
        TokenBucketRateLimit rateLimit = new TokenBucketRateLimit(1000,1000);
        ExecutorService executorService = Executors.newFixedThreadPool(2,
                new NamedThreadFactory("TokenBucketRateLimit",false,10));
        for(int i = 0; i< 100; i++){
            Runnable runnable = () -> {
                rateLimit.canPass();
            };
            executorService.submit(runnable);
        }
    }

}
