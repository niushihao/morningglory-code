package com.morningglory.basic.spring.autoconfig;

import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.AsyncConfigurationSelector;

import java.lang.annotation.*;

/**
 * @author qianniu
 * @date 2020/6/24 2:45 下午
 * @desc
 * @see Log
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(LogConfigurationSelector.class)
public @interface EnableLog {

    AdviceMode mode() default AdviceMode.PROXY;
}
