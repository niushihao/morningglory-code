package com.morningglory.job.elastic;

import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.dataflow.DataflowJobConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.reg.base.CoordinatorRegistryCenter;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author qianniu
 * @date 2020/12/10 11:50 上午
 * @desc
 */
public class ElasticConfig {

    public static Queue queue = new LinkedBlockingQueue();

    public static LiteJobConfiguration defaultSimpleConfig(Class clazz){
        JobCoreConfiguration coreConfiguration = JobCoreConfiguration
                .newBuilder(clazz.getName()
                        // 10秒执行一次
                        //, "0/10 * * * * ?"
                        , "0 0/1 * * * ?"
                        , 2)
                .build();

        SimpleJobConfiguration jobConfig = new SimpleJobConfiguration(coreConfiguration, clazz.getCanonicalName());
        LiteJobConfiguration liteJobConfiguration = LiteJobConfiguration.newBuilder(jobConfig).overwrite(true).build();
        return liteJobConfiguration;
    }

    public static LiteJobConfiguration defaultDataflowConfig(Class clazz){
        JobCoreConfiguration coreConfiguration = JobCoreConfiguration
                .newBuilder(clazz.getName()
                        // 10秒执行一次
                        , "0/10 * * * * ?"
                        , 2)
                .build();

        DataflowJobConfiguration jobConfig = new DataflowJobConfiguration(coreConfiguration, clazz.getCanonicalName(),true);
        LiteJobConfiguration liteJobConfiguration = LiteJobConfiguration.newBuilder(jobConfig).overwrite(true).build();
        return liteJobConfiguration;
    }

    public static CoordinatorRegistryCenter defaultRegistryCenter(){
        //ZookeeperConfiguration configuration = new ZookeeperConfiguration("172.21.8.140:2181", "async-expor11");
        ZookeeperConfiguration configuration = new ZookeeperConfiguration("127.0.0.1:2181", "async-expor11");
        ZookeeperRegistryCenter registryCenter = new ZookeeperRegistryCenter(configuration);
        registryCenter.init();
        return registryCenter;
    }
}
