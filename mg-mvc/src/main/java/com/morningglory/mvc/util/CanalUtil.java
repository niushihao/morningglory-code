package com.morningglory.mvc.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;

/**
 * @Author: qianniu
 * @Date: 2019-12-02 10:37
 * @Desc: canal工具类
 */
public class CanalUtil {

    public static <T> Object toPojo(Class<T> clazz, List<CanalEntry.Column> columns){

        String json = toJSONString(columns);

        T entity = JSON.parseObject(json, clazz);

        return entity;


    }

    private static String toJSONString(List<CanalEntry.Column> columns){
        Map<String,Object> map = Maps.newHashMap();

        for(CanalEntry.Column column : columns){
            String columnName = column.getName();
            if("json".equalsIgnoreCase(column.getMysqlType())){
                map.put(NameConvertUtils.lineToHump(columnName), JSON.parseObject(column.getValue()));
            }else {
                map.put(NameConvertUtils.lineToHump(columnName),column.getValue());
            }
        }
        return JSON.toJSONString(map);
    }
}
