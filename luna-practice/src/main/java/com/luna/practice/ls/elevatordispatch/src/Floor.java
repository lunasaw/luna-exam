package com.luna.practice.ls.elevatordispatch.src;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class Floor extends JPanel {
    /**
     * 楼层树
     */
    public static final int TOTAL_FLOOR = 20;

    /**
     * 向上外部按钮
     */
    private JButton[] upButton = new JButton[TOTAL_FLOOR + 1];
    /**
     * 向下外部按钮
     */
    private JButton[] downButton = new JButton[TOTAL_FLOOR + 1];
    /**
     * 电梯
     */
    private Elevator[] elevators;
    /**
     * 电梯运行任务
     */
    private LinkedList<Integer> noDispatchedJob = new LinkedList<Integer>();

    /**
     * 大楼构造方法
     *
     * @param es 大楼内的所有电梯
     */
    public Floor(Elevator[] es) {
        elevators = es;
        this.setLayout(new GridLayout(TOTAL_FLOOR, 3, 2, 2));

        for (int i = TOTAL_FLOOR; i >= 1; --i) {
            JLabel floorLabel = new JLabel(i + "F");
            floorLabel.setHorizontalAlignment(JLabel.CENTER);
            Font font = floorLabel.getFont();
            floorLabel.setFont(new Font(font.getFontName(), font.getStyle(), 15));
            this.add(floorLabel);

            // 设置每个向上按钮的触发事件和展示内容
            upButton[i] = new JButton(i + "↑");
            upButton[i].setMargin(new Insets(1, 1, 1, 1));
            upButton[i].setFocusPainted(false);
            upButton[i].setBackground(Color.white);
            upButton[i].setFont(new Font(font.getFontName(), font.getStyle(), 15));
            // 监听器
            upButton[i].addActionListener(upButtonListrner);
            // 最高楼不能再上了
            if (i == TOTAL_FLOOR) {
                upButton[i].setEnabled(false);
            }
            this.add(upButton[i]);

            // 设置每个向下按钮的触发事件和展示内容
            downButton[i] = new JButton(i + "↓");
            downButton[i].setMargin(new Insets(1, 1, 1, 1));
            downButton[i].setFocusPainted(false);
            downButton[i].setBackground(Color.white);
            downButton[i].setFont(new Font(font.getFontName(), font.getStyle(), 15));
            downButton[i].addActionListener(downButtonListrner);
            // 1楼不能再往下了
            if (i == 1) {
                downButton[i].setEnabled(false);
            }
            this.add(downButton[i]);
        }

        // 定时器
        Timer timer = new Timer(true);
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                lookUpJobDispatch();
            }
        };
        // 每次移动停一秒
        timer.schedule(timerTask, 1000, 1000);

    }

    /**
     * 向上任务
     */
    private void lookUpJobDispatch() {
        ArrayList<Integer> delList = new ArrayList<Integer>();
        // 任务列表遍历
        for (int i : noDispatchedJob) {
            int direction;
            int f = i;
            // 如果当前上升请求楼层大于最大楼层
            if (i > TOTAL_FLOOR) {
                // 则减去最大楼层数执行向下指令
                f -= TOTAL_FLOOR;
                direction = Elevator.DOWN;
            } else {
                direction = Elevator.UP;
            }
            if (schedule(f, direction, true)) {
                delList.add(new Integer(i));
            }
        }
        noDispatchedJob.removeAll(delList);
    }

    /**
     * 处理任务
     *
     * @param floor      运行层数
     * @param direction  任务方向
     * @param fromLookUp
     * @return
     */
    private boolean schedule(int floor, int direction, boolean fromLookUp) {
        System.out.println("处理任务" + floor + "方向" + direction);
        // 获取运行方向
        int adverseDirec = Elevator.UP + Elevator.DOWN - direction;
        System.out.println("电梯运行方向：" + adverseDirec);
        int distance = TOTAL_FLOOR + 1;
        int elevatorId = -1;
        // 寻找最合适点电梯接受该任务
        for (int i = 0; i < Elevator.TOTAL_ELEVATOR; ++i) {
            int d2 = Math.abs(floor - elevators[i].getFloor());
            // 如果电梯已经上行/下行至该楼层且不是停靠状态跳过该电梯
            // 如果电梯为非free状态且其所在楼层在要求楼层与direction相反的方向跳过该电梯
            // 如果电梯运行方向与需求方向相反则跳过该电梯
            if ((d2 == 0 && (elevators[i].getState() == direction && !elevators[i].isPause()))
                    || (elevators[i].getState() == Elevator.UP && floor < elevators[i].getFloor())
                    || (elevators[i].getState() == Elevator.DOWN && floor > elevators[i].getFloor())
                    || elevators[i].getState() == adverseDirec
                    || elevators[i].getOrder() == adverseDirec) {
                continue;
            }
            // 选距离更小者
            if (d2 < distance) {
                distance = d2;
                elevatorId = i;
                // 若距离相等 选择非free状态的电梯
            } else if (d2 == distance) {
                if (elevators[i].getState() == direction) {
                    elevatorId = i;
                }
            }
        }
        // 若没找到合适的电梯则将其加入任务列表
        if (elevatorId == -1) {
            if (!fromLookUp) {
                switch (direction) {
                    case Elevator.UP:
                        System.out.println("添加向上任务："+floor);
                        noDispatchedJob.add(floor);
                        break;
                    case Elevator.DOWN:
                        System.out.println("添加向下任务："+floor);
                        noDispatchedJob.add(floor + TOTAL_FLOOR);
                        break;
                }
            }
            return false;
        } else {
            // 若找到了合适的电梯让其接受该任务
            System.out.println("找到了" + elevatorId + "号电梯让其接受该任务" + floor + ">>" + direction);
            elevators[elevatorId].addOutJob(floor, direction);
            return true;
        }
    }

    /**
     * 任务完成视图展示
     *
     * @param floor
     * @param direction
     */
    public void finishJob(int floor, int direction) {
        switch (direction) {
            case Elevator.UP:
                upButton[floor].setEnabled(true);
                upButton[floor].setBackground(Color.white);
                break;
            case Elevator.DOWN:
                downButton[floor].setEnabled(true);
                downButton[floor].setBackground(Color.white);
                break;
        }
    }

    ActionListener upButtonListrner = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton button = (JButton) e.getSource();
            button.setEnabled(false);
            button.setBackground(new Color(176, 196, 222));
            String text = button.getText();
            int floor = Integer.parseInt(text.substring(0, text.length() - 1));
            // 处理任务
            schedule(floor, Elevator.UP, false);
        }
    };

    ActionListener downButtonListrner = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton button = (JButton) e.getSource();
            button.setEnabled(false);
            button.setBackground(new Color(176, 196, 222));
            String text = button.getText();
            int floor = Integer.parseInt(text.substring(0, text.length() - 1));
            schedule(floor, Elevator.DOWN, false);
        }
    };
}
