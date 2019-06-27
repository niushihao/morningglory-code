package com.morningglory.basic.poi;

import com.google.common.collect.Lists;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;

/**
 * @Author: qianniu
 * @Date: 2019-03-14 18:59
 * @Desc:
 */
public class FieldUtil {

    /**
     * 获取 sheetClass对象中有clazzType 的属性
     * @param sheetClass
     * @param clazzType
     * @return
     */
    public static List<Field> getFields(Class<?> sheetClass,Class clazzType) {
        List<Field> list = Lists.newArrayList();
        if (sheetClass.getDeclaredFields()!=null && sheetClass.getDeclaredFields().length>0) {
            for (Field field: sheetClass.getDeclaredFields()) {
                if (Modifier.isStatic(field.getModifiers())) {
                    continue;
                }
                if(field.getAnnotation(clazzType) == null){
                    continue;
                }
                list.add(field);
            }
        }
        return list;
    }
}
