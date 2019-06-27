package com.morningglory.basic.poi.ann;

import java.lang.annotation.*;

/**
 * @Author: qianniu
 * @Date: 2019-03-14 17:07
 * @Desc:
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface ExcelField {

    /**
     * 列名称
     *
     * @return
     */
    String name() default "";

    /**
     * 列宽
     * @return
     */
    int width() default 0;

    /**
     * 是否加粗
     * @return
     */
    boolean isBold() default false;

    /**
     * 字体大小
     * @return
     */
    short fontSize() default 11;
}
