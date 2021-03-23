package com.morningglory.basic.spring.autoconfig;

import java.lang.annotation.*;

/**
 * @author qianniu
 * @date 2020/6/24 2:45 下午
 * @desc
 */
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {
}
