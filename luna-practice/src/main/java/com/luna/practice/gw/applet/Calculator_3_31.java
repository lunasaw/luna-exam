package com.luna.practice.gw.applet;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.*;

/**
 * @author Luna@win10
 * @date 2020/3/31 10:21
 */

public class Calculator_3_31 extends JFrame {

	private JTextField jTextField = new JTextField(10);
	private JTextField jTextField1 = new JTextField(10);
	private JTextField jTextField2 = new JTextField(10);

	public Calculator_3_31() {
		super.setLayout(new GridLayout(10, 20));

		JLabel jLabel = new JLabel("第一操作数:      ");
		add(jLabel);
		add(jTextField);

		JLabel jLabel1 = new JLabel("第二操作数:      ");
		add(jLabel1);
		add(jTextField1);

		JLabel jLabel2 = new JLabel("运算  结果:      ");
		jTextField2.setEditable(false);
		jTextField2.setBackground(Color.LIGHT_GRAY);
		add(jLabel2);
		add(jTextField2);
		add(new JLabel("                       "));
		add(new JLabel("                            "));


	}

	public static void addButton(String name, Calculator_3_31 frame, String todo) {
		JButton button = new JButton(name);
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String text1 = frame.jTextField.getText();
				String text2 = frame.jTextField1.getText();
				if (isDecNum(text1) == false || isDecNum(text2) == false) {
					JOptionPane.showMessageDialog(null, "参数有误");
					frame.jTextField.setText("");
					frame.jTextField1.setText("");
					frame.jTextField2.setText("");
					return;
				}

				Double a = Double.parseDouble(text1);
				Double b = Double.parseDouble(text2);
				Object c = 0;
				if (todo.equals("+")) {
					c = a + b;
				} else if (todo.equals("-")) {
					c = a - b;
				} else if (todo.equals("*")) {
					c = a * b;
				} else if (todo.equals("/")) {
					c = a / b;
				} else if (todo.equals("%")) {
					c = a % b;
				} else {
					frame.jTextField.setText("");
					frame.jTextField1.setText("");
					frame.jTextField2.setText("");
					return;
				}
				frame.jTextField2.setText(String.format("%.2f", c));
			}
		});
		Dimension preferredSize = new Dimension(215, 30);
		button.setPreferredSize(preferredSize);
		frame.add(button);
	}

	public static void main(String[] args) {
		Calculator_3_31 frame = new Calculator_3_31();
		frame.setTitle("简单计算器");
		frame.setSize(480, 300);
		frame.setLocationRelativeTo(null);
		addButton("加法", frame, "+");
		addButton("减法", frame, "-");
		addButton("乘法", frame, "*");
		addButton("除法", frame, "/");
		addButton("取余", frame, "%");
		addButton("重置", frame, "~");

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	public static boolean isNum(String str) {
		String regEx = "-?[0-9]+(\\.[0-9]+)?";
		Pattern pat = Pattern.compile(regEx);
		Matcher mat = pat.matcher(str);
		if (mat.find()) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isDecNum(String str) {
		try {
			String s = new BigDecimal(str).toString();
		} catch (Exception e) {
			return false;
		}
		return true;
	}

}

