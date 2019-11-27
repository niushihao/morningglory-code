package com.morningglory.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author: qianniu
 * @Date: 2019-11-27 16:19
 * @Desc:
 */
@ConfigurationProperties(prefix = "es")
@Data
public class EsHighLevelClientProperty {

    /**
     * es地址
     */
    private String host;

    /**
     * 端口号
     */
    private int port;
}
