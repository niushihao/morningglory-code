package com.morningglory.binlog;

import com.alibaba.otter.canal.client.adapter.OuterAdapter;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author qianniu
 * @date 2020/11/24 4:19 下午
 * @desc
 */
@SpringBootApplication
@ComponentScan(value = {"com.morningglory.binlog","com.alibaba.otter.canal.adapter.launcher"})
public class Application {

    public static void main(String[] args) {
//        ConfigurableApplicationContext context = SpringApplication.run(Application.class);
//        CanalAdapterService bean = context.getBean(CanalAdapterService.class);
//        System.out.println(bean.toString());
        ExtensionLoader loader = ExtensionLoader.getExtensionLoader(OuterAdapter.class);
        Object defaultExtension = loader.getDefaultExtension();

    }
}
