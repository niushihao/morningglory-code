package com.morningglory.basic.spring.syncconfig;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.*;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;

/**
 * @author qianniu
 * @date 2020/12/21 1:29 下午
 * @desc 获取所有有@Value注解的bean和属性，当配置变更后通过反射更新配置信息
 */
@Component
@Slf4j
public class ValueAnnotationBeanPostProcessor implements BeanPostProcessor, EnvironmentAware {

    private Environment environment;
    private MultiValueMap<String,ConfigValueHolder> keyHolder = new LinkedMultiValueMap<>();
    private static final PropertyPlaceholderHelper placeholderHelper = new PropertyPlaceholderHelper(
            SystemPropertyUtils.PLACEHOLDER_PREFIX,
            SystemPropertyUtils.PLACEHOLDER_SUFFIX,
            SystemPropertyUtils.VALUE_SEPARATOR,
            false
    );

    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        ReflectionUtils.doWithFields(bean.getClass(),field -> {
            Value annotation = AnnotationUtils.findAnnotation(field, Value.class);
            if(Objects.isNull(annotation)){
                return;
            }

            String value = environment.resolvePlaceholders(annotation.value());
            ReflectionUtils.makeAccessible(field);
            ReflectionUtils.setField(field,bean,value);

            String key = placeholderHelper.replacePlaceholders(annotation.value(),placeholderName -> placeholderName);
            ConfigValueHolder configValueHolder = new ConfigValueHolder(bean, beanName, field, key);
            keyHolder.add(key,configValueHolder);
            log.info("注册了配置: key：{}",key);
        });

        return bean;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    /**
     * 更新配置的值
     * @param key
     */
    public void update(String key){
        List<ConfigValueHolder> configValueHolders = keyHolder.get(key);
        if(CollectionUtils.isEmpty(configValueHolders)){
            return;
        }

        log.info("配置项 {} 发生变更,同步更新使用了这个配置的属性值",key);
        String newValue = environment.getProperty(key);
        configValueHolders.forEach(holder -> ReflectionUtils.setField(holder.getField(),holder.getBean(),newValue));
    }


    @AllArgsConstructor
    @Data
    public class ConfigValueHolder{
        final Object bean;
        final String beanName;
        final Field field;
        final String key;
    }
}
