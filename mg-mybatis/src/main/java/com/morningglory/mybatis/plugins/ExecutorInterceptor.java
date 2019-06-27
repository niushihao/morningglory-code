package com.morningglory.mybatis.plugins;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.util.Properties;


/**
 * @Author: qianniu
 * @Date: 2019-06-21 10:03
 * @Desc:
 */
@Slf4j
@Intercepts({@Signature(type = Executor.class,method = "query",args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})})
public class ExecutorInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        MappedStatement mappedStatement = (MappedStatement)invocation.getArgs()[0];
        SqlCommandType commandType = mappedStatement.getSqlCommandType();

        Object parameter = invocation.getArgs()[1];

        // 获取拦截器指定的方法类型, 通常需要拦截 update
        String methodName = invocation.getMethod().getName();
        String sql = mappedStatement.getBoundSql(parameter).getSql();
        String id = mappedStatement.getId();

        log.info("ExecutorInterceptor, methodName: {}, commandType: {}, sql: {}, id: {}"
                , methodName, commandType,sql,id);

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
