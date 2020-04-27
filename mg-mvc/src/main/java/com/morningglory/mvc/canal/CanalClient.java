package com.morningglory.mvc.canal;
import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.protocol.Message;
import com.morningglory.property.CanalClientProperty;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;

import java.net.InetSocketAddress;
/**
 * @Author: qianniu
 * @Date: 2019-12-17 00:36
 * @Desc: canal客户端
 */
@Slf4j
public class CanalClient {

    /**默认读取消息数量*/
    private static int DEFAULT_BATCH_SIZE = 5 * 1024;

    /**client当前运行状态*/
    private volatile Boolean shutDown = true;

    /**初始化client的配置*/
    private CanalClientProperty property;

    /**canal链接对象*/
    private CanalConnector connector;

    /**canal消息监听器*/
    private CanalListener listener;

    public CanalClient(CanalClientProperty property) {
        this.property = property;
        // 创建链接
        this.connector = createConnector();
    }


    /**
     * 启动客户端，接收canal消息
     */
    public void init(){
        connector.connect();
        log.info("canal 连接成功");
        connector.subscribe(property.getSubscribe());
        log.info("canal 订阅成功 subscribe = {}",property.getSubscribe());
        connector.rollback();
        listener.init();
        shutDown = false;
        // 启动客户端
        clientStart();
    }

    public void destroy(){
        shutDown = true;
        connector.disconnect();
    }

    public void setListener(CanalListener listener){
        this.listener = listener;
    }

    /**
     * 创建链接
     * 根据配置创建集群/单价版的链接
     */
    private CanalConnector createConnector() {
        if(property.getCluster() != null && property.getCluster().getEnable()){
            CanalConnector connector = CanalConnectors.newClusterConnector(property.getCluster().getZkHosts()
                    , property.getDestination(), property.getUserName(), property.getPassword());
            log.info("cluster canalConnector initialized with property hosts:[{}],destination:[{}],userName:[{}],password:[{}]"
                    ,property.getCluster().getZkHosts(),property.getDestination(),property.getUserName(),property.getPassword());
            return connector;
        }

        CanalConnector connector = CanalConnectors.newSingleConnector(new InetSocketAddress(property.getHost()
                ,property.getPort()),property.getDestination(),property.getUserName(),property.getPassword());
        log.info("single canalConnector initialized with property hosts:[{}],destination:[{}],userName:[{}],password:[{}]"
                ,property.getHost(),property.getDestination(),property.getUserName(),property.getPassword());
        return connector;
    }


    /**
     * 启动客户端
     */
    private void clientStart() {
        Runnable runnable = () -> {
            log.info("canal task 开始工作");
            while (!shutDown) {
                Message message = connector.getWithoutAck(DEFAULT_BATCH_SIZE);
                if (CollectionUtils.isEmpty(message.getEntries())) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    continue;
                }

                listener.onMessage(message);
                connector.ack(message.getId());
            }
            log.info("canal task 停止工作");
        };
        new Thread(runnable,"canal-client").start();
    }


}
