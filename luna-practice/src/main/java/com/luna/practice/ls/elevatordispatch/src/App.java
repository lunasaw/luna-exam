package com.luna.practice.ls.elevatordispatch.src;

import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class App extends JFrame {
    private JPanel     panel    = new JPanel();
    /** 电梯 */
    private Elevator[] elevator = new Elevator[Elevator.TOTAL_ELEVATOR];
    /** 楼 */
    private Floor      floor    = new Floor(elevator);

    public App() {
        panel.setLayout(new GridLayout(1, Elevator.TOTAL_ELEVATOR + 1, 20, 0));
        panel.add(floor);

        // 遍历电梯数目 生成JFrame
        for (int i = 0; i < Elevator.TOTAL_ELEVATOR; ++i) {
            elevator[i] = new Elevator(floor);
            elevator[i].add(panel);
        }

        this.add(panel);
        setTitle("Elevator Dispatch");
        // 设置点击退出关闭程序
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 设置大小
        setSize(1000, 750);
        // 设置框架是否可由用户调整大小。
        setResizable(false);
        // 显示窗口
        setVisible(true);
    }

    public static void main(String[] args) {
        new App();
    }
}
