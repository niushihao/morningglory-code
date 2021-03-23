package com.morningglory.mvc.aop;

import java.lang.annotation.*;

/**
 * @author qianniu
 * @date 2020/7/21 4:02 下午
 * @desc
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequiresPermissions {

    /**
     * 权限标识
     * @return
     */
    PermissionEnum code();

    /**
     * 权限描述
     * @return
     */
    String desc() default "";

    /**
     * 参数值,配合SPEL获取
     * @return
     */
    String params() default "";

}
