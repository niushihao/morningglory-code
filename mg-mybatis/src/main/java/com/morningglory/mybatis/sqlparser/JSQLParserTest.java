package com.morningglory.mybatis.sqlparser;


import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.relational.*;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.*;

import java.io.StringReader;
import java.util.List;

/**
 * @Author: qianniu
 * @Date: 2019-08-19 11:46
 * @Desc:
 */
@Slf4j
public class JSQLParserTest {

    public static void main(String[] args) throws JSQLParserException {

        String sql = "select * from demo.item i where id in ('1','2','3')";

        CCJSqlParserManager parserManager = new CCJSqlParserManager();
        Statement parse = parserManager.parse(new StringReader(sql));

        Select select = (Select) parse;
        PlainSelect plainSelect = (PlainSelect) select.getSelectBody();

        FromItem fromItem = plainSelect.getFromItem();
        log.info("fromItem = {}",fromItem);
        Expression where = plainSelect.getWhere();
        log.info("where = {}",where);

        Column tenantColumn = new Column("status");

        Alias alias = fromItem.getAlias();

        if (alias != null) {
            Table tb = new Table();
            tb.setName(fromItem.getAlias().getName());
            tenantColumn.setTable(tb);
        }


        InExpression inExpression = new InExpression();
        inExpression.setLeftExpression(tenantColumn);

        List<Expression> values = Lists.newArrayList();

        StringValue value1 = new StringValue("ON");
        StringValue value2 = new StringValue("OFF");
        values.add(value1);
        values.add(value2);
        ExpressionList expressionList = new ExpressionList(values);


        inExpression.setRightItemsList(expressionList);

        log.info("inExpression = {}",inExpression.toString());
        if(where == null){
            where = inExpression;
        }else{
            where = new AndExpression(where,inExpression);
        }

        log.info("where = {}",where);
    }


}
