package com.morningglory.h2;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

import java.io.PrintWriter;
import java.sql.*;

@Controller
@Slf4j
public class TestContoller {

    static final String JDBC_DRIVER = "org.h2.Driver";
    static final String MYSQL_JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:h2:file:/Users/nsh/data/demo";
    static final String USER = "root";
    static final String PASS = "123456";


    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        Class.forName(MYSQL_JDBC_DRIVER);
        Class.forName(JDBC_DRIVER);
        DriverManager.setLogWriter(new PrintWriter(System.err));
        Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);

//        String createTableSql = "CREATE TABLE QN_H2(id INT,name VARCHAR(100))";
//        connection.createStatement().execute(createTableSql);

//        String string = "INSERT INTO QN_H2(id,name) VALUES(1,'hello')";
//        connection.createStatement().execute(string);

        String selectSql = "SELECT name FROM QN_H2 where id = 1";
        PreparedStatement preparedStatement = connection.prepareStatement(selectSql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            System.out.println(JSON.toJSONString(resultSet.getObject(1)));
        }

    }
}
