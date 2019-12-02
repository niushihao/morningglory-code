package com.morningglory.mvc.config;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.morningglory.property.CanalClientProperty;
import com.morningglory.property.EsHighLevelClientProperty;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetSocketAddress;


/**
 * @Author: qianniu
 * @Date: 2019-11-27 16:24
 * @Desc:
 */
@Configuration
@EnableConfigurationProperties({EsHighLevelClientProperty.class, CanalClientProperty.class})
@Slf4j
public class AutoConfigBean {

    @Bean
    public RestHighLevelClient esClient(EsHighLevelClientProperty esProperty){
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost(esProperty.getHost(), esProperty.getPort())
                ));
        log.info("esClient initialized with property [host:{}],[port:{}]",esProperty.getHost(),esProperty.getPort());
        return client;
    }

    @Bean
    public CanalConnector canalConnector(CanalClientProperty property){
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
}
