package com.luna.practice.gw.applet;

import javax.swing.*;
import java.awt.*;

/**
 * @author Luna@win10
 * @date 2020/3/31 10:21
 */
public class TestGui_3_31 extends JFrame {

	public void ShowFlowLayout() {
		super.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 20));
		JTextField jTextField = new JTextField(10);
		JLabel jLabel = new JLabel("��һ������:      ");
		add(jLabel);
		add(jTextField);

		JTextField jTextField1 = new JTextField(10);
		JLabel jLabel1 = new JLabel("�ڶ�������:         ");
		add(jLabel1);
		add(jTextField1);

		add(new JLabel("                            "));

		JTextField jTextField2 = new JTextField(10);
		JLabel jLabel2 = new JLabel("������:                  ");
		jTextField2.setEnabled(false);
		jTextField2.setBackground(Color.LIGHT_GRAY);
		add(jLabel2);
		add(jTextField2);

		add(new JLabel("                       "));
	}


}
