package com.morningglory.mvc.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @author qianniu
 * @date 2020/10/27 12:49 下午
 * @desc
 */
@ConfigurationProperties(prefix = "mg.properties1")
@Data
public class PropertiesDemo1 {

    private List<PropertiesItems> items;

    @Data
    public static class PropertiesItems{

        private String name;

        private String path;
    }
}
