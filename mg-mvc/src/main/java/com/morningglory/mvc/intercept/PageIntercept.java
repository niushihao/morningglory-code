package com.morningglory.mvc.intercept;

import com.morningglory.mvc.page.Page;
import com.morningglory.mvc.page.StudentPageRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;

/**
 * @Author: qianniu
 * @Date: 2019-06-24 15:32
 * @Desc:
 */
@Component
@Slf4j
@Intercepts({@Signature(type=StatementHandler.class,method="prepare",args={Connection.class,Integer.class})})
public class PageIntercept implements Interceptor {

    private final static String PAGE_QUERY = "ByPage";
    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();

        MetaObject metaObject = MetaObject.forObject(statementHandler, SystemMetaObject.DEFAULT_OBJECT_FACTORY, SystemMetaObject.DEFAULT_OBJECT_WRAPPER_FACTORY,new DefaultReflectorFactory());
        MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");

        String id = mappedStatement.getId();
        if(id.endsWith(PAGE_QUERY)){

            BoundSql boundSql = statementHandler.getBoundSql();
            String sql = boundSql.getSql();

            Map<String,Object> params = (Map<String, Object>) boundSql.getParameterObject();
            Page pageRequest = (StudentPageRequest) params.get("page");



            int totalCount = getTotalCount(invocation, metaObject, sql);
            pageRequest.setTotal(totalCount);

            String pageSql = sql + " limit " + pageRequest.getPageNo()+","+pageRequest.getPageSize();
            log.info("pageSql = {}",pageSql);

            metaObject.setValue("delegate.boundSql.sql", pageSql);
        }

        return invocation.proceed();

    }

    private int getTotalCount(Invocation invocation, MetaObject metaObject,String sql) throws SQLException {

        String countSql = "select count(*) from ("+sql+")a";

        Connection connection = (Connection) invocation.getArgs()[0];
        PreparedStatement statement = connection.prepareStatement(countSql);
        ParameterHandler parameterHandler = (ParameterHandler) metaObject.getValue("delegate.parameterHandler");
        parameterHandler.setParameters(statement);
        ResultSet rs = statement.executeQuery();

        ResultSet resultSet = statement.executeQuery();
        Page response = new Page();
        if(resultSet.next()){
            int count = resultSet.getInt(1);
            log.info("count = {}",count);
            response.setTotal(count);
            return count;
        }

        return 0;
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target,this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
