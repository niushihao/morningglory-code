package com.morningglory.basic.spring.env;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.core.env.StandardEnvironment;

import java.util.Map;

/**
 * @author qianniu
 * @date 2020/6/9 9:52 上午
 * @desc
 */
@Slf4j
public class EnvironmentTest {

    public static void main(String[] args) {

        Environment env = new StandardEnvironment();

        // 1. 操作系统的环境变量
        Map<String, Object> systemEnvironment = ((StandardEnvironment) env).getSystemEnvironment();
        log.info("systemEnvironment = {}",systemEnvironment);

        // 2. jvm属性配置
        Map<String, Object> systemProperties = ((StandardEnvironment) env).getSystemProperties();
        log.info("systemProperties = {}",systemProperties);

        // 3. 自定义属性
        log.info("self propert = {}",env.getProperty("redis.host"));
    }
}
