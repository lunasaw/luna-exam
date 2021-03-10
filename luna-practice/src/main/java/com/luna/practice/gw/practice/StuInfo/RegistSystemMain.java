package com.luna.practice.gw.practice.StuInfo;

import com.sun.glass.events.WindowEvent;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.List;

public class RegistSystemMain implements ActionListener {

	public static UserService userService = new UserService();

	JFrame f = null;
	//类属性
	//构造方法
	public RegistSystemMain() {
		f = new JFrame("员工信息");
		//创建一个顶层容器
		Container contentPane = f.getContentPane();
		//获得其内容面板
		JPanel buttonPanel = new JPanel();
		//创建一中间容器JPanel
		JButton b = new JButton("员工登记");
		//创建一原子组件——按钮
		b.addActionListener(this);
		//为按钮添加事件监听器对象
		buttonPanel.add(b);
		//将此按钮添加到中间容器
		b = new JButton("退出系统");
		//再创建一按钮
		b.addActionListener(this);
		//为按钮增加事件监听器
		buttonPanel.add(b);
		//将按钮添加到中间容器
		//设置中间容器边框
		buttonPanel.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.blue, 2),
				"员工登记系统", TitledBorder.CENTER, TitledBorder.TOP));
		contentPane.add(buttonPanel, BorderLayout.CENTER);
		//将中间容器添加到内容面板
		JMenuBar mBar = new JMenuBar();
		//创建菜单条
		JMenu selection = new JMenu("选项");
		JMenuItem regist = new JMenuItem(" 员工登记");
		JMenuItem sum = new JMenuItem("统计");
		selection.add(regist);
		selection.add(sum);
		JMenu sys = new JMenu("系统");
		JMenuItem exit = new JMenuItem("退出系统");
		sys.add(exit);
		mBar.add(selection);
		mBar.add(sys);
		f.setJMenuBar(mBar);
		//为窗体增加菜单
		regist.addActionListener(this);
		//为菜单添加事件监听器
		sum.addActionListener(this);
		exit.addActionListener(this);
		f.pack();
		f.setVisible(true);
		f.addWindowListener(new WindowAdapter() {
			//为窗口操作添加监听器
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//实现ActionListener接口唯一的方法
		String cmd = e.getActionCommand();
		//从事件对象获得相关命令名称
		if (cmd.equals("员工登记")) {
			//根据名称选择相应事件
			new RegistSystemListener(f);
			//显示员工登记对话框
		} else if (cmd.equals("退出系统")) {
			System.exit(0);
		} else if (cmd.equals("统计")) {
			try {
				List list = userService.getList();
				JOptionPane.showMessageDialog(f,"共有"+list.size()+"名员工");
				//显示信息对话框
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
	}


	public static void main(String[] args) {
		new RegistSystemMain();
	}
}

