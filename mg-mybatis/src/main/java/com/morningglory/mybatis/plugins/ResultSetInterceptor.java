package com.morningglory.mybatis.plugins;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.plugin.*;

import java.sql.Statement;
import java.util.Properties;

/**
 * @Author: qianniu
 * @Date: 2019-06-21 14:52
 * @Desc:
 */
@Slf4j
@Intercepts({@Signature(type = ResultSetHandler.class,method = "handleResultSets",args = {Statement.class})})
public class ResultSetInterceptor implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Statement statement = (Statement) invocation.getArgs()[0];
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target,this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
