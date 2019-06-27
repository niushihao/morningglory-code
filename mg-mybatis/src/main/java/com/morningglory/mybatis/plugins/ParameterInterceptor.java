package com.morningglory.mybatis.plugins;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.plugin.*;

import java.sql.PreparedStatement;
import java.util.Properties;

/**
 * @Author: qianniu
 * @Date: 2019-06-21 14:51
 * @Desc:
 */
@Slf4j
@Intercepts({@Signature(type = ParameterHandler.class,method = "setParameters",args = {PreparedStatement.class})})
public class ParameterInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        PreparedStatement statement = (PreparedStatement) invocation.getArgs()[0];
        log.info("PreparedStatement = {}",statement.toString());
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
