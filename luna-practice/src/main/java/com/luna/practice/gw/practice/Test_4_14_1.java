package com.luna.practice.gw.practice;

import com.luna.practice.gw.practice.StuInfo.User;
import com.luna.practice.gw.practice.StuInfo.UserService;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;

/**
 * @author Luna@win10
 * @date 2020/4/14 10:34
 */
public class Test_4_14_1 {
	public static UserService userService = new UserService();

	@Test
	public void aTest() {
		User user = new User(1, "张三", 1, "Engineer", 5000.00, 3);
		JdbcUtil jdbcUtil = new JdbcUtil();

		jdbcUtil.update("insert into books.user values(?, ?, ?, ?, ?, ?)",
				new Object[]{user.getId(), user.getName(), user.getDeptId(), user.getDuty(), user.getSalary(), user.getEducation()});
	}

	@Test
	public void bTest() throws SQLException {
		User user = userService.selectById(1);
		System.out.println(user);
	}

	@Test
	public void cTest() {
		User user = new User(1, "李四", 1, "Chairman", 9800.0, 1);
		userService.update(user);
	}

	@Test
	public void dTest() {
		userService.deleteById(1);
	}

	@Test
	public void eTest() throws SQLException {
		List list = userService.getList();
		System.out.println(list.size());
	}

}
