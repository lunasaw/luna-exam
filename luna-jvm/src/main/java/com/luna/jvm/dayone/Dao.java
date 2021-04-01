package com.luna.jvm.dayone;

import java.sql.Connection;

public class Dao {
    public void insert() {
        // 获取连接
        // System.out.println("Dao.insert()-->" + Thread.currentThread().getName() + DBUtil.getConnection());
        // Connection conn = new DBUtil2().getConnection();
        Connection conn = DBUtil3.getConnection();
        System.out.println("Dao.insert()-->" + Thread.currentThread().getName() + conn);
    }

    public void delete() {
        // 获取连接
        // System.out.println("Dao.delete()-->" + Thread.currentThread().getName() + DBUtil.getConnection());
        // Connection conn = new DBUtil2().getConnection();
        Connection conn = DBUtil3.getConnection();
        System.out.println("Dao.delete()-->" + Thread.currentThread().getName() + conn);
    }

    public void update() {
        // 获取连接
        // System.out.println("Dao.update()-->" + Thread.currentThread().getName() + DBUtil.getConnection());
        // Connection conn = new DBUtil2().getConnection();
        Connection conn = DBUtil3.getConnection();
        System.out.println("Dao.update()-->" + Thread.currentThread().getName() + conn);
    }

    public void select() {
        // 获取连接
        // System.out.println("Dao.select()-->" + Thread.currentThread().getName() + DBUtil.getConnection());
        // Connection conn = new DBUtil2().getConnection();
        Connection conn = DBUtil3.getConnection();
        System.out.println("Dao.select()-->" + Thread.currentThread().getName() + conn);
    }
}