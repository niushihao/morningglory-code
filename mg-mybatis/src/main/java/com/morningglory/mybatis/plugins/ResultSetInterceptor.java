package com.morningglory.mybatis.plugins;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.plugin.*;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.sql.Statement;
import java.util.List;
import java.util.Properties;

/**
 * @Author: qianniu
 * @Date: 2019-06-21 14:52
 * @Desc: 这个拦截器可以做 列数据权限,在查询返回后,根据配置将部分字段的值设置为null
 */
@Slf4j
@Intercepts({@Signature(type = ResultSetHandler.class,method = "handleResultSets",args = {Statement.class})})
public class ResultSetInterceptor implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        List<Object> result = (List<Object>) invocation.proceed();

        if(CollectionUtils.isEmpty(result)){
            return result;
        }

        // TODO 模拟列数据权限的配置
        String column = "age";
        for(Object obj : result){
            log.info("before obj = {}", JSON.toJSONString(obj));
            try {
//                Field field = obj.getClass().getDeclaredField(column);
//                field.setAccessible(true);
//                field.set(obj,null);
            }catch (Exception e){
               // do nothing
            }
            log.info("after obj = {}", JSON.toJSONString(obj));

        }
        return result;
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target,this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
