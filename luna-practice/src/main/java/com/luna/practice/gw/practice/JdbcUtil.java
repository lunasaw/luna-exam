package com.luna.practice.gw.practice;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Luna@win10
 * @date 2020/4/14 10:34
 */
public class JdbcUtil {

	private Connection conn = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;
	String url = "jdbc:mysql://111.229.114.126:3307/Luna?characterEncoding=utf-8&serverTimezone=GMT%2B8&useSSL=false";
	String name = "root";
	String pwd = "czy1024";

	public PreparedStatement getPs() {
		return ps;
	}

	public void setPs(PreparedStatement ps) {
		this.ps = ps;
	}

	public ResultSet getRs() {
		return rs;
	}

	public void setRs(ResultSet rs) {
		this.rs = rs;
	}


	//连接
	public Connection getConn() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(url, name, pwd);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	//关闭
	public void clossAll(Connection conn, PreparedStatement ps, ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
			}
			if (ps != null) {
				ps.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	//给占位符赋值
	public PreparedStatement setString(String sql, String uname, String pwd) {
		try {
			ps = getConn().prepareStatement(sql);
			ps.setString(1, uname);
			ps.setString(2, pwd);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ps;
	}

	public PreparedStatement setString(String sql, String uid) {
		try {
			ps = getConn().prepareStatement(sql);
			ps.setString(1, uid);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ps;
	}

	public List convertList(ResultSet rs) throws SQLException {
		List list = new ArrayList();
		ResultSetMetaData md = rs.getMetaData();
		//获取键名
		int columnCount = md.getColumnCount();
		//获取行的数量
		while (rs.next()) {
			Map rowData = new HashMap();
			//声明Map
			for (int i = 1; i <= columnCount; i++) {
				rowData.put(md.getColumnName(i), rs.getObject(i));
				//获取键名及值
			}
			list.add(rowData);
		}
		return list;
	}

	//查询
	public ResultSet select(String sql, Object[] pram) {
		conn = getConn();
		try {
			ps = conn.prepareStatement(sql);
			if (pram != null) {
				for (int i = 0; i < pram.length; i++) {
					ps.setObject(i + 1, pram[i]);
				}
			}
			rs = ps.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}


	//增加修改
	public int update(String sql, Object[] pram) {
		int n = 0;
		conn = getConn();
		try {
			ps = conn.prepareStatement(sql);
			if (pram != null) {
				for (int i = 0; i < pram.length; i++) {
					ps.setObject(i + 1, pram[i]);
				}
			}
			n = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			clossAll(conn, ps, rs);
		}
		return n;
	}

}
