package com.luna.jvm.dayone;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil3 {
    private static final String            DRIVER        = "com.mysql.cj.jdbc.Driver";
    private static final String            USER          = "qa_msp";
    private static final String            PWD           = "64654dftgert4@";
    private static final String            URL           =
        "jdbc:mysql://pc-bp1lsgy6vl6yrrl81-test.rwlb.rds.aliyuncs.com:3306/test_1?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf-8&useSSL=false";

    // 定义一个数据库连接
    private static Connection              conn          = null;
    private static ThreadLocal<Connection> connContainer = new ThreadLocal<Connection>();

    // 获取连接
    public synchronized static Connection getConnection() {
        // 获取连接对象
        conn = connContainer.get();
        try {
            if (conn == null) {
                Class.forName(DRIVER);
                conn = DriverManager.getConnection(URL, USER, PWD);
                connContainer.set(conn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
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