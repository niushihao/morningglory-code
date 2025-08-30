package com.morningglory.h2;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

import javax.sql.DataSource;
import java.io.OutputStreamWriter;
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

        // 获取 Runtime 实例
        Runtime runtime = Runtime.getRuntime();

        // 当前堆内存使用情况
        long usedMemory = runtime.totalMemory() - runtime.freeMemory();
        long maxMemory = runtime.maxMemory();
        long initialMemory = runtime.totalMemory();
        System.out.println("Used Memory: " + bytesToMB(usedMemory));
        System.out.println("Max Memory: " + bytesToMB(maxMemory));
        System.out.println("Initial Memory: " + bytesToMB(initialMemory));
        //Class.forName(MYSQL_JDBC_DRIVER);
        Class.forName(JDBC_DRIVER);
        DriverManager.setLogWriter(new PrintWriter(new OutputStreamWriter(System.out)));
        Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);

        //DataSource.

//        String createTableSql = "CREATE TABLE QN_H2(id INT,name VARCHAR(100))";
//        connection.createStatement().execute(createTableSql);
//
        String string = "INSERT INTO QN_H2(id,name) VALUES(1,'hello')";
        connection.createStatement().execute(string);

        String string1 = "INSERT INTO QN_H2(id,name) VALUES(2,'hello')";
        connection.createStatement().execute(string1);

        String selectSql = "SELECT name FROM QN_H2 where id = 1";
        PreparedStatement preparedStatement = connection.prepareStatement(selectSql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            System.out.println(JSON.toJSONString(resultSet.getObject(1)));
        }

    }

    private static double bytesToMB(long bytes) {
        return bytes / (1024.0 * 1024.0);
    }
}
