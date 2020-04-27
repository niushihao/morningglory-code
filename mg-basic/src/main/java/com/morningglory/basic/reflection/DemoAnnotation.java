package com.morningglory.basic.reflection;

import java.lang.annotation.*;

/**
 * @Author: qianniu
 * @Date: 2020-04-21 13:06
 * @Desc:
 */
@Target({ElementType.TYPE,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DemoAnnotation {

    String str = null;

    String name() default "name";

    int age() default 10;
}
