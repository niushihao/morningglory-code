package com.morningglory.basic.poi.ann;

import org.apache.poi.hssf.util.HSSFColor;

import java.lang.annotation.*;

/**
 * @Author: qianniu
 * @Date: 2019-03-14 16:59
 * @Desc:
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface ExcelSheet {

    /**
     * 表名称
     *
     * @return
     */
    String name() default "";

    /**
     * 表头/首行的颜色
     *
     * @return
     */
    HSSFColor.HSSFColorPredefined headColor() default HSSFColor.HSSFColorPredefined.LIGHT_GREEN;

}
