package com.morningglory.basic.jdbc;

import lombok.extern.slf4j.Slf4j;

import java.sql.*;

/**
 * @Author: qianniu
 * @Date: 2019-03-29 11:38
 * @Desc:
 */
@Slf4j
public class JDBCExample {


    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/demo?characterEncoding=utf8&useSSL=true";
    static final String USER = "root";
    static final String PASS = "123456";

    public static void main(String[] args) {

        useStatement();

        usePreparedStatement();
    }

    private static void useStatement() {

        Connection conn = null;
        Statement stmt = null;
        try {
            // 创建驱动
            Class.forName(JDBC_DRIVER);

            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            stmt = conn.createStatement();

            String sql = "SELECT * FROM student where name = 'test' ";

            ResultSet resultSet = stmt.executeQuery(sql);

            while (resultSet.next()){
                int id = resultSet.getInt("id");

                log.info("ID = {}",id);
            }

            resultSet.close();
            stmt.close();
            conn.close();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void usePreparedStatement() {

        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            // 创建驱动
            Class.forName("com.mysql.jdbc.Driver");

            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            /**
             * tableName或者orderBy中有占位符是不行的，因为preparedStatement在设值时都会加上单引号
             * 所以mybatis的tableName和orderBy等都要用${}而不能用#{}
              */
            //String sql = "SELECT * FROM student where name like ? order by ?";

            String sql = "SELECT * FROM student where name like ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            // anything' OR 'x'='x
            preparedStatement.setString(1,"%用户%");

            log.info("preparedStatement = {}",preparedStatement.toString());
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                int id = resultSet.getInt("id");
                int age = resultSet.getInt("age");

                log.info("ID = {},age = {}",id,age);
            }

            resultSet.close();
            preparedStatement.close();
            conn.close();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
