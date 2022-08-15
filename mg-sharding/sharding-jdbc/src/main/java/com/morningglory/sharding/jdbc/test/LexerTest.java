package com.morningglory.sharding.jdbc.test;

import org.apache.shardingsphere.core.parse.old.lexer.dialect.mysql.MySQLLexer;

/**
 * @author qianniu
 * @date 2022/7/21
 * @desc
 */
public class LexerTest {

    public static void main(String[] args) {

        String sql = " select * from user where id =? ";
        MySQLLexer mySQLLexer = new MySQLLexer(sql);
        mySQLLexer.nextToken();
    }
}
