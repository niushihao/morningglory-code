package com.morningglory.zk;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.List;

/**
 * @Author: qianniu
 * @Date: 2019-09-26 17:12
 * @Desc:
 */
@Slf4j
public class MyZkClient {

    private static final String connectString = "127.0.0.1:2181";

    private static final int sessionTimeout = 2000;

    private static ZooKeeper zk = null;



    public static void main(String[] args) throws Exception {

        // 创建
        create();

        // 获取子节点
        getChilden();

        // 判断是否存在
        isExist();

        // 设置数据
        setData();

        // 获取数据
        getData();

        // 删除数据x
        deleteData();
    }

    /**
     * 创建节点
     * @throws Exception
     */
    public static void create() throws Exception {
        ZooKeeper zk = getZookeeper();
        String path = zk.create("/mytest", "hello zookeeper".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        log.info("create result path = {}",path);
        log.info("#############create##################");
    }

    /**
     * 获取子节点
     */
    public static void getChilden() throws Exception {
        List<String> children = zk.getChildren("/", true);
        for(String node : children){
            log.info("子节点 = {}",node);
        }
        Thread.sleep(10000);
        log.info("#############getChilden##################");
    }

    /**
     * 判断节点是否存在
     * @throws Exception
     */
    public static void isExist() throws Exception {
        Stat exists = zk.exists("/mytest", false);
        if(exists == null){
            log.info("/mytest 不存在");
        }else {
            log.info("/mytest 存在");
        }
        log.info("#############isExist##################");

    }

    /**
     * 设置数据
     * @throws Exception
     */
    public static void setData() throws Exception{
        zk.setData("/mytest","hello,nsh".getBytes(),-1);
        log.info("#############setData##################");
    }

    /**
     * 获取节点数据
     * @throws Exception
     */
    public static void getData() throws Exception{
        byte[] mytests = zk.getData("/mytest", false, new Stat());
        log.info("mytest data = {}",new String(mytests));
        log.info("#############getData##################");
    }

    /**
     * 删除数据
     * @throws Exception
     */
    public static void deleteData() throws Exception{
        // 第二个字段指定删除对应版本的数据,-1为所有版本
        zk.delete("/mytest",-1);
        log.info("#############deleteData##################");
    }


    /**
     * 获取zk
     * @return
     * @throws IOException
     */
    public static ZooKeeper getZookeeper() throws IOException {

        zk = new ZooKeeper(connectString,sessionTimeout,new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                log.info("事件类型:{},路径:{}",watchedEvent.getType(),watchedEvent.getPath());
                try {
                    zk.getChildren("/", true);
                }catch (Exception e){
                    log.error(e.getMessage(),e);
                }
            }
        });
        return zk;
    }
}
