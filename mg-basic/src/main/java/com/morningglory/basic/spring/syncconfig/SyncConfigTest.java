package com.morningglory.basic.spring.syncconfig;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;

import java.util.Map;

/**
 * @author qianniu
 * @date 2020/12/21 1:46 下午
 * @desc
 */
@Slf4j
public class SyncConfigTest {

    public static void main(String[] args) {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();

        ConfigurableEnvironment environment = context.getEnvironment();
        MutablePropertySources propertySources = environment.getPropertySources();

        Map<String,Object> source = Maps.newHashMap();
        source.put("name","nsh1");

        propertySources.addFirst(new MapPropertySource("nsh",source));


        context.register(ConfigBean.class);
        context.register(ValueAnnotationBeanPostProcessor.class);
        context.refresh();

        // 系统启动后读取的配置
        ConfigBean bean = context.getBean(ConfigBean.class);
        log.info("default config value = {}",bean.getName());

        // 手动修改配置看对象属性是否会同步更新
        source.put("name","nsh2");
        propertySources.addFirst(new MapPropertySource("nsh",source));

        ValueAnnotationBeanPostProcessor postProcessor = context.getBean(ValueAnnotationBeanPostProcessor.class);
        postProcessor.update("name");
        log.info("after sync config value = {}",bean.getName());
    }

}
