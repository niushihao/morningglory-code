package com.morningglory.mvc.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

/**
 * @author qianniu
 * @date 2020/10/27 12:49 下午
 * @desc
 */
@Data
@ConfigurationProperties(prefix = "mg.properties2")
public class PropertiesDemo2 {

    Map<String,PropertiesItems> items;

    @Data
    public static class PropertiesItems{

        private String name;

        private String path;
    }
}
