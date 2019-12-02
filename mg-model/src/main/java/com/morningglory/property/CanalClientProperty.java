package com.morningglory.property;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @Author: qianniu
 * @Date: 2019-11-29 15:03
 * @Desc:
 */
@ConfigurationProperties(prefix = "canal")
@Data
public class CanalClientProperty {

    private String host;

    private int port;

    private String dbName;

    private String destination;

    private String userName;

    private String password;

    private CanalClusterProperties cluster;

    @Data
    @ToString
    public static class CanalClusterProperties {
        private Boolean enable;

        private String zkHosts;
    }
}
