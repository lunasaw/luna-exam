package com.luna.practice.gw.practice;

import com.luna.practice.gw.practice.StuInfo.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;


/**
 * @author ��ΰ�
 * ʱ��:2015��3��9������4:23:48
 */
public class AccessDBUtil {

	static String accessFile = "d:\\a.accdb";

	public static void main(String[] args) throws SQLException {
		getUserList();
	}

	public static List<User> getUserList() throws SQLException {
        Connection conn = null;
        final String sql = "select * from `׷���ܱ�`";
        ResultSet rs = null;
        List<User> list = null;
        try {
            conn = getConnection();
            rs = conn.createStatement().executeQuery(sql);
            ;
            while (rs.next()) {
                User reAdd = new User();
                reAdd.setName("����");
                System.out.println(reAdd.getName());
                list.add(reAdd);
            }
		} finally {
			rs.close();
			conn.close();
		}
		return list;
	}

	public static Connection getConnection() throws SQLException {
        Properties prop = new Properties();
        prop.put("charSet", "gb2312");
        //���ñ����ֹ���ĳ�������
        return DriverManager.getConnection("jdbc:odbc:Driver={MicroSoft Access Driver (*.mdb, *.accdb)};DBQ=" + accessFile, prop);
    }


}