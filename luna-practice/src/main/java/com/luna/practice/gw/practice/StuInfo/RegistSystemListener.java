package com.luna.practice.gw.practice.StuInfo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * ���ڲ���JDialog,ʵ���¼��������ӿ�
 */
public class RegistSystemListener implements ActionListener {
	public static UserService userService = new UserService();

	JDialog dialog;
	JTextField tF1 = new JTextField();
	JTextField tF2 = new JTextField();
	JTextField tF3 = new JTextField();
	JTextField tF4 = new JTextField();
	JTextField tF5 = new JTextField();
	JTextField tF6 = new JTextField();

	RegistSystemListener(JFrame f) {
        //���췽��,������÷����л�öԻ���ĸ�����
        dialog = new JDialog(f, "Ա���Ǽ�", true);
        //����һmodal�Ի���
        Container dialogPane = dialog.getContentPane();
        //������ע����Ӹ������
        dialogPane.setLayout(new GridLayout(7, 2));
        dialogPane.add(new JLabel("Ա����ţ�", SwingConstants.CENTER));
        dialogPane.add(tF1);
        dialogPane.add(new JLabel("Ա��������", SwingConstants.CENTER));
        dialogPane.add(tF2);
        dialogPane.add(new JLabel(" ���ű��", SwingConstants.CENTER));
        dialogPane.add(tF3);
        dialogPane.add(new JLabel("ְ��", SwingConstants.CENTER));
        dialogPane.add(tF4);
        dialogPane.add(new JLabel(" ����", SwingConstants.CENTER));
        dialogPane.add(tF5);
        dialogPane.add(new JLabel(" ѧ�����", SwingConstants.CENTER));
        dialogPane.add(tF6);
        JButton b1 = new JButton("ȷ��");
        dialogPane.add(b1);
        JButton b2 = new JButton(" ȡ��");
        dialogPane.add(b2);
        b1.addActionListener(this);
        //Ϊ����ť�����¼�������
        b2.addActionListener(this);
        dialog.setBounds(300, 300, 400, 300);
        dialog.show();
    }

	@Override
	public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        if (cmd.equals("ȷ��")) {
            //�������
            Integer id = Integer.parseInt(tF1.getText());
            String name = tF2.getText();
            Integer deptId = Integer.parseInt(tF3.getText());
            String duty = tF4.getText();
            Double salary = Double.parseDouble(tF5.getText());
            int eduId = Integer.parseInt(tF6.getText());
            try {
                userService.insert(new User(id, name, deptId, duty, salary, eduId));
                JOptionPane.showMessageDialog(null, "�����ɹ�");
                dialog.dispose();
            } catch (HeadlessException ex) {
                JOptionPane.showMessageDialog(null, "����ʧ��,������");
            }
        } else if (cmd.equals("ȡ��")) {
            dialog.dispose();
            //ֱ�ӷ���������
        }
	}
}
