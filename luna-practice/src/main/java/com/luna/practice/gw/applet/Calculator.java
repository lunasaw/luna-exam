package com.luna.practice.gw.applet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

/**
 * @author Roger
 * @date 2020/3/31 13:08
 */
public class Calculator extends JFrame {

    public static void main(String[] args) {
        new Calculator();
    }

    public Calculator() {
        setTitle("简单运算器");
//        setDefaultLookAndFeelDecorated(true);
        setLayout(new GridLayout(2, 1));
        Container contentPane = getContentPane();

        JPanel p1 = initJPanel(2, 4);
        JTextField[] t = new JTextField[3];
        p1.add(new JLabel("第一个操作数："));
        t[0] = new JTextField();
        p1.add(t[0]);
        p1.add(new JLabel("第二个操作数："));
        t[1] = new JTextField();
        p1.add(t[1]);
        p1.add(new JLabel(""));
        p1.add(new JLabel("运算结果："));
        t[2] = new JTextField();
        t[2].setEditable(false);
        t[2].setText("0.0000");
        p1.add(t[2]);
        p1.add(new JLabel(""));

        JPanel p2 = initJPanel(3, 2);
        addButton(t, p2, "加法");
        addButton(t, p2, "减法");
        addButton(t, p2, "乘法");
        addButton(t, p2, "除法");
        addButton(t, p2, "取余");
        addButton(t, p2, "重置");

        contentPane.add(p1);
        contentPane.add(p2);
        pack();
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void addButton(JTextField[] t, JPanel p, String name) {
        JButton b = new JButton(name);
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (name == "重置") {
                    t[0].setText("");
                    t[1].setText("");
                    t[2].setText("0.0000");
                    return;
                }

                if (t[0].getText().isEmpty() || t[1].getText().isEmpty() || !isNum(t[0].getText()) || !isNum(t[1].getText())) {
                    if (t[0].getText().isEmpty() && t[1].getText().isEmpty()) {
                        t[2].setText("0.0000");
                    } else {
                        t[2].setText("错误");
                    }
                    return;
                }
                BigDecimal a = new BigDecimal(t[0].getText());
                BigDecimal b = new BigDecimal(t[1].getText());
                BigDecimal c = new BigDecimal("0");

                switch (name) {
                    case "加法":
                        c = a.add(b);
                        break;
                    case "减法":
                        c = a.subtract(b);
                        break;
                    case "乘法":
                        c = a.multiply(b);
                        break;
                    case "除法":
                        if (b.compareTo(BigDecimal.ZERO) == 0) {
                            t[2].setText("错误");
                            return;
                        }
                        c = a.divideAndRemainder(b)[0];
                        break;
                    case "取余":
                        if (b.compareTo(BigDecimal.ZERO) == 0) {
                            t[2].setText("错误");
                            return;
                        }
                        c = a.divideAndRemainder(b)[1];
                        break;
                    default:
                        t[0].setText("");
                        t[1].setText("");
                        t[2].setText("0.0000");
                        return;
                }
                t[2].setText(String.format("%.4f", c));
            }
        });
        p.add(b);
    }

    private JPanel initJPanel(int rows, int cols) {
        JPanel p = new JPanel();
        p.setLayout(new GridLayout(rows, cols));
//        p.setBorder(BorderFactory.createLineBorder(Color.black, 5));
        return p;
    }

    public static boolean isNum(String str) {
        String bigStr;
        try {
            bigStr = new BigDecimal(str).toString();
        } catch (Exception e) {
            return false;//异常 说明包含非数字。
        }
        return true;
    }
}
