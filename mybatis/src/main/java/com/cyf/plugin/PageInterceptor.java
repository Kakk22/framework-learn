package com.cyf.plugin;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Properties;

/**
 * 分页拦截器
 *
 * @author 陈一锋
 * @date 2021/9/4 5:04 下午
 */
@Intercepts({@Signature(
        type = StatementHandler.class,
        method = "prepare",
        args = {Connection.class}
)})
public class PageInterceptor implements Interceptor {

    @SuppressWarnings("unchecked")
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        //1.获取分页参数
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        try {
            Page page = PageHelper.get();
            if (page == null) {
                return invocation.proceed();
            }
            //2.查询总条数
            int count = getCount(invocation);
            page.setTotal(count);
            System.out.println("总行数:" + count);

            BoundSql boundSql = statementHandler.getBoundSql();
            String newSql = String.format(boundSql.getSql() + " limit %s,%s ", page.getOffset(), page.getPageSize());
            SystemMetaObject.forObject(boundSql).setValue("sql", newSql);
            //3.查询sql
            return invocation.proceed();
        } finally {
            PageHelper.clear();
        }
    }

    private int getCount(Invocation invocation) throws SQLException {
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        BoundSql boundSql = statementHandler.getBoundSql();
        String countSql = String.format("select count(*) from (%s) as temp", boundSql.getSql());
        Connection connection = (Connection) invocation.getArgs()[0];
        PreparedStatement preparedStatement = connection.prepareStatement(countSql);
        //设置查询参数
        statementHandler.getParameterHandler().setParameters(preparedStatement);
        ResultSet resultSet = preparedStatement.executeQuery();
        int count = 0;
        if (resultSet.next()) {
            count = resultSet.getInt("count(*)");
        }
        resultSet.close();
        preparedStatement.close();
        return count;
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
