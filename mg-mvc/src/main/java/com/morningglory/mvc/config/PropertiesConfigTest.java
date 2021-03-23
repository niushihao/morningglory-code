package com.morningglory.mvc.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @author qianniu
 * @date 2020/10/27 12:51 下午
 * @desc 测试负责properties 的自动装配
 *
 */
@Configuration
@ConditionalOnProperty(value = "mg.properties.enable",havingValue = "true",matchIfMissing = false)
@EnableConfigurationProperties({PropertiesDemo1.class,PropertiesDemo2.class})
@Slf4j
public class PropertiesConfigTest implements InitializingBean {

    @Resource
    private PropertiesDemo1 propertiesDemo1;

    @Resource
    private PropertiesDemo2 propertiesDemo2;


    @Override
    public void afterPropertiesSet() throws Exception {
        log.info(propertiesDemo1.toString());
        log.info(propertiesDemo2.toString());
    }
}
