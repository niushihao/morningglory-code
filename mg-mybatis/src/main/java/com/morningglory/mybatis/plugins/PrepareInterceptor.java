package com.morningglory.mybatis.plugins;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.*;

import java.sql.Connection;
import java.util.Properties;

/**
 * @Author: qianniu
 * @Date: 2019-06-20 19:53
 * @Desc:
 */
@Slf4j
@Intercepts({@Signature(type = StatementHandler.class,method = "prepare",args = {Connection.class, Integer.class})})
public class PrepareInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        Object arg0 = invocation.getArgs()[0];
        Object arg1 = invocation.getArgs()[1];
        log.info("arg0 = {},arg1 = {}",arg0,arg1);

        String name = invocation.getMethod().getName();
        log.info("method name = {}",name);
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
