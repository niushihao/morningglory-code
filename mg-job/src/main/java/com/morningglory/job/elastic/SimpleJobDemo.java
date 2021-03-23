package com.morningglory.job.elastic;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;

/**
 * @author qianniu
 * @date 2020/12/10 10:36 上午
 * @desc
 */
@Slf4j
public class SimpleJobDemo implements SimpleJob {

    public void init(){

        JobScheduler jobScheduler = new JobScheduler(ElasticConfig.defaultRegistryCenter()
                ,ElasticConfig.defaultSimpleConfig(SimpleJobDemo.class));
        jobScheduler.init();
    }

    @Override
    public void execute(ShardingContext shardingContext) {
        log.info("<====Demo Job Begin====>");
        log.info(String.format("------Thread ID: %s, 任务总片数: %s, 当前分片项: %s",
                Thread.currentThread().getId(), shardingContext.getShardingTotalCount(), shardingContext.getShardingItem()));
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("<====Demo Job End====>");
    }

    // 取消jar中的日志
    static {
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        //ch.qos.logback.classic.Logger root1 = loggerContext.getLogger("com.dangdang");
        ch.qos.logback.classic.Logger root2 = loggerContext.getLogger("org.apache.zookeeper");
        ch.qos.logback.classic.Logger root3 = loggerContext.getLogger("org.apache.curator");
        //root1.setLevel(Level.toLevel("OFF"));
        root2.setLevel(Level.toLevel("INFO"));
        root3.setLevel(Level.toLevel("INFO"));
    }

    public static void main(String[] args) {
        Runnable runnable = () -> {
            new SimpleJobDemo().init();
        };

        new Thread(runnable).start();
        new Thread(runnable).start();
        //new SimpleJobDemo().init();
        //new SimpleJobDemo().init();
    }
}
