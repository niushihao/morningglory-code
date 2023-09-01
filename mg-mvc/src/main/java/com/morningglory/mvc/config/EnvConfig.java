package com.morningglory.mvc.config;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * @author qianniu
 * @date 2023/3/3
 * @desc
 */
@Component
@Slf4j
public class EnvConfig implements EnvironmentAware {
    @Override
    public void setEnvironment(Environment environment) {
        log.info(environment.getProperty("server.port"));
        log.info(environment.resolvePlaceholders("server.port"));
        log.info(environment.resolveRequiredPlaceholders("server.port"));
        log.info(JSON.toJSONString(environment.getActiveProfiles()));
    }
}
