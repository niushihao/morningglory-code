package com.morningglory.mvc.config;

import com.morningglory.mvc.canal.CanalClient;
import com.morningglory.property.CanalClientProperty;
import com.morningglory.property.EsHighLevelClientProperty;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
    public CanalClient canalClient(CanalClientProperty property){
        return new CanalClient(property);
    }
}
