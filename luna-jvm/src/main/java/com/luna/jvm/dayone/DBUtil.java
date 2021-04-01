package com.luna.jvm.dayone;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String USER   = "root";
    private static final String PWD    = "root";
    private static final String URL    = "jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=utf8";

    // 定义一个数据库连接
    private static Connection   conn   = null;

    // 获取连接
    public static Connection getConnection() {
        try {
            Class.forName(DRIVER);
            if (conn == null) {
                conn = DriverManager.getConnection(URL, USER, PWD);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    // 关闭连接
    public static void closeConnection() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        System.out.println(getConnection());
    }
}