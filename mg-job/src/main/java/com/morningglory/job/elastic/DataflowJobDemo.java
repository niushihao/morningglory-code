package com.morningglory.job.elastic;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.dataflow.DataflowJob;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.google.common.collect.Lists;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author qianniu
 * @date 2020/12/10 11:49 上午
 * @desc
 */
@Slf4j
public class DataflowJobDemo implements DataflowJob {


    private LocalDateTime localDateTime;
    public void init(){
        JobScheduler jobScheduler = new JobScheduler(ElasticConfig.defaultRegistryCenter()
                ,ElasticConfig.defaultDataflowConfig(DataflowJobDemo.class));
        jobScheduler.init();
        localDateTime = LocalDateTime.now();
        log.info("启动任务=================================");
    }

    /**
     * 如果获取数据为空，则按配置间隔重新触发任务
     * 如果不为空,则一直处理，直到查不到数据
     */
    @SneakyThrows
    @Override
    public List fetchData(ShardingContext shardingContext) {
        Object poll = ElasticConfig.queue.poll();
        if(Objects.nonNull(poll)){
            log.info("查到了数据呀,还剩:{} 条数据。。。",ElasticConfig.queue.size());
            return Lists.newArrayList(poll);
        }else {
            //log.info("没查到了数据啊。。。");
            return Collections.EMPTY_LIST;
        }
//        log.info("fetchData,开始时间为:{}",localDateTime);
//        Thread.sleep(3000);
//        return Lists.newArrayList(1,2,3);
        //return Lists.newArrayList();
    }

    @SneakyThrows
    @Override
    public void processData(ShardingContext shardingContext, List list) {
        Thread.sleep(5000 * 60);
        log.info("processData, list= {}",list);

    }

    public static void main(String[] args) throws InterruptedException {
        System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "error");
        System.setProperty("com.morningglory.job.elastic.DataflowJobDemo", "error");
        // 启动定时任务
        new DataflowJobDemo().init();


        Thread.sleep(10000);
        // 多线程放数据
        Runnable runnable = () -> {
            while (true){
                int i = 1;
                ElasticConfig.queue.add(i++);
                //log.info("放入数据。。。");
                if(i>3){
                    i=1;
                }
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        new Thread(runnable).start();
//        for(int i =0;i<10;i++){
//            ElasticConfig.queue.add(i);
//        }
    }
}
