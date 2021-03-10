package com.luna.practice.gw.practice.StuInfo;

import com.luna.practice.gw.practice.JdbcUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

/**
 * @author Luna@win10
 * @date 2020/4/14 11:24
 */
public class UserService {
	public static JdbcUtil jdbcUtil = new JdbcUtil();

	/**
	 * 添加
	 *
	 * @param user
	 */
	public boolean insert(User user) {
//		User books.user=new User(1,"����",1,"Engineer",5000.00,3);
        int update = jdbcUtil.update("insert into books.user values(?, ?, ?, ?, ?, ?)",
                new Object[]{user.getId(), user.getName(), user.getDeptId(), user.getDuty(), user.getSalary(), user.getEducation()});
        return update == 1;
    }

	/**
	 * 查找
	 *
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public User selectById(Integer id) throws SQLException {
        ResultSet selectById = jdbcUtil.select("select * from books.user where id=?", new Object[]{id});
        List list = jdbcUtil.convertList(selectById);
        HashMap map = (HashMap) list.get(0);
        return new User(Integer.parseInt(String.valueOf(map.get("id"))), String.valueOf(map.get("name")), Integer.parseInt(String.valueOf(map.get("dept_id"))), String.valueOf(map.get("duty")), Double.parseDouble(String.valueOf(map.get("salary"))), (int) map.get("education"));
    }

	/**
	 * 更新
	 *
	 * @param user
	 */
	public boolean update(User user) {
        int update = jdbcUtil.update("update books.user set name=?, dept_id=?, duty=?, salary=?, education=? where id=?",
                new Object[]{user.getName(), user.getDeptId(), user.getDuty(), user.getSalary(), user.getEducation(), user.getId()});
        return update == 1;
    }

	/**
	 * 删除
	 * @param id
	 * @return
	 */
	public boolean deleteById(Integer id) {
        int update = jdbcUtil.update("delete from books.user where id=?", new Object[]{id});
        return update == 1;
    }

	public List getList() throws SQLException {
        ResultSet selectList = jdbcUtil.select("select * from books.user ", new Object[]{});
        return jdbcUtil.convertList(selectList);
    }
}
