package com.luna.practice.ls.elevatordispatch.src;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ScheduledExecutorService;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;


/**
 * 电梯视图
 */
class ElevatorView extends JPanel {
    // 电梯内部按钮
    private JButton[] floorButton = new JButton[Floor.TOTAL_FLOOR + 1];
    // 电梯当前状态展示
    private JLabel currentState;
    // 电梯当前楼层展示
    private JLabel currentFloor;
    // 运行电梯
    private Elevator elevator;

    public ElevatorView(Elevator e) {
        elevator = e;
        // 设置布局
        this.setLayout(new GridLayout((Floor.TOTAL_FLOOR + 1) / 2 + 1, 2, 4, 4));

        // 初始楼层为1
        currentFloor = new JLabel("1");
        // 格式设置
        Font font = currentFloor.getFont();
        currentFloor.setHorizontalAlignment(JLabel.CENTER);
        currentFloor.setFont(new Font(font.getFontName(), font.getStyle(), 25));
        // 背景色
        currentFloor.setForeground(Color.blue);
        // 初始状态为Free
        currentState = new JLabel("-");
        // 格式设置
        currentState.setHorizontalAlignment(JLabel.CENTER);
        currentState.setFont(new Font(font.getFontName(), font.getStyle(), 25));
        // 背景色
        currentState.setForeground(Color.blue);

        // 将俩展示视图放入view
        this.add(currentState);
        this.add(currentFloor);
        // 遍历楼层数
        for (int i = 1; i <= Floor.TOTAL_FLOOR; ++i) {
            // 生成内部按钮
            floorButton[i] = new JButton(String.valueOf(i));
            floorButton[i].setMargin(new Insets(1, 1, 1, 1));
            floorButton[i].setFocusPainted(false);
            // 背景色
            floorButton[i].setBackground(Color.white);
            floorButton[i].setFont(new Font(font.getFontName(), font.getStyle(), 15));
            // 添加监听方法
            floorButton[i].addActionListener(floorButtonListener);
            // 放入视图
            this.add(floorButton[i]);
        }
    }

    /**
     * 按钮触发事件
     */
    ActionListener floorButtonListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton button = (JButton) e.getSource();
            // 获取当前内部按钮的楼层数
            int floor = Integer.parseInt(button.getText());
            // 将内部任务加入任务列表
            elevator.addJob(floor);
        }
    };

    /**
     * 按钮按下触发动作
     *
     * @param i
     */
    public void buttonPressed(int i) {
        // 将按钮设置为不可按操作
        floorButton[i].setEnabled(false);
        // 设置背景
        floorButton[i].setBackground(new Color(176, 196, 222));
    }

    public void changeFloor(int f) {
        currentFloor.setText(String.valueOf(f));
    }

    /**
     * 改变显示状态
     *
     * @param s
     */
    public void changeState(int s) {
        switch (s) {
            case Elevator.FREE:
                currentState.setText("-");
                break;
            case Elevator.UP:
                currentState.setText("↑");
                break;
            case Elevator.DOWN:
                currentState.setText("↓");
                break;
            default:
                currentState.setText("open");
                break;
        }
    }

    /**
     * 设置楼层内部按钮可用
     *
     * @param f
     */
    public void arriveFloor(int f) {
        floorButton[f].setEnabled(true);
        floorButton[f].setBackground(Color.white);
    }
}

public class Elevator {
    /**
     * 电梯数目
     */
    public static final int TOTAL_ELEVATOR = 2;
    /**
     * 电梯状态
     */
    public static final int FREE = 0;
    public static final int UP = 1;
    public static final int DOWN = 2;
    public static final int DOOR_OPEN = 3;

    // 电梯视图
    private ElevatorView view = new ElevatorView(this);
    // 当前按钮是否已经按下
    private boolean[] floorButtonPressed = new boolean[Floor.TOTAL_FLOOR + 1];
    // 外部任务
    private int[] outJobDirection = new int[Floor.TOTAL_FLOOR + 1];
    private Timer timer;
    private TimerTask timerTask;
    private Floor floorView;
    /**
     * 电梯当前状态
     */
    private int state;
    // 电梯接受外部任务后order和state可能不一致
    /**
     * 电梯执行的外部指令的方向  取决于外部任务按钮的方向而不是电梯实际运动方向
     */
    private int order;
    private int tempState;
    private boolean changeOrder;
    /**
     * 电梯所在楼层
     */
    private int floor;
    /**
     * 电梯最低到达楼层
     */
    private int maxJob;
    /**
     * 电梯最大到达楼层
     */
    private int minJob;
    // 计时器，模拟开关门操作 代表电梯是否到达任务楼层，即电梯是否处于停靠状态电梯内部按钮按下后将对应楼层添加为任务楼层
    private boolean arriveFloor;

    /**
     * 重置定时器
     * 电梯类中计时器会每隔 1800ms 检查一次电梯状态
     */
    private void restartTimer() {
        timer.cancel();
        timerTask.cancel();
        timer = null;
        timerTask = null;
        timer = new Timer(true);
        timerTask = new TimerTask() {
            @Override
            public void run() {
                start();
            }
        };
        timer.schedule(timerTask, 1800, 1800);
    }

    /**
     * 电梯初始化
     *
     * @param f
     */
    public Elevator(Floor f) {
        floorView = f;
        order = state = FREE;
        changeOrder = false;
        floor = 1;
        maxJob = 0;
        minJob = Floor.TOTAL_FLOOR + 1;
        arriveFloor = false;
        for (int i = 0; i <= Floor.TOTAL_FLOOR; ++i) {
            floorButtonPressed[i] = false;
            outJobDirection[i] = FREE;
        }
        timer = new Timer(true);
        timerTask = new TimerTask() {
            @Override
            public void run() {
                start();
            }
        };
        timer.schedule(timerTask, 1800, 1800);
    }

    public final int getFloor() {
        return floor;
    }

    public final int getState() {
        return state;
    }

    public final int getOrder() {
        return order;
    }

    public final boolean isPause() {
        return arriveFloor;
    }

    public void addOutJob(int f, int d) {
        System.out.println("==========》》》添加外部任务：" + f + ">>方向" + d);
        if (order == FREE) {
            order = d;
        }
        outJobDirection[f] = d;
        System.out.println("==========》》》转为内部任务============");
        addJob(f);
    }

    /**
     * 添加任务
     *
     * @param f
     */
    public void addJob(int f) {
        System.out.println("==========》》》添加内部任务：" + f);
        // 将按钮设置为已经按下
        floorButtonPressed[f] = true;
        // 触发按钮事件
        view.buttonPressed(f);
        // 当前电梯状态
        int tempState = state;
        // 如果当前电梯是Free或者 （ 内部请求楼层为当前电梯所在楼层并且电梯是处于停靠状态 ）
        if (state == FREE || (f == floor && arriveFloor)) {
            // 如果请求楼层大于当前所在楼层
            if (f > floor) {
                // 则修改状态为UP
                state = UP;
                // 如果请求楼层小于当前所在楼层
            } else if (f < floor) {
                // 则修改状态为DOWN
                state = DOWN;
                // 如果请求楼层与当前楼层相等
            } else {
                // 则完成任务
                finishJob();
                // 重置计时器
                restartTimer();
            }
            // 如果当前电梯在当前楼层但是未停靠
            if (!arriveFloor) {
                // 重启定时器
                restartTimer();
                // 改变展示状态
                view.changeState(state);
            }
        }
        if (order == tempState) {
            order = state;
        }
    }

    /**
     * 检查状态
     */
    public void start() {
        // 判断当前电梯是否停靠
        if (arriveFloor) {
            // 如果按钮可用
            if (changeOrder) {
                state = order = tempState;
            }
            view.changeState(state);
            arriveFloor = false;
            // 如果电梯未停靠
        } else {
            switch (state) {
                // 根据状态选择上升或者下降一层
                case UP:
                    upFloor();
                    break;
                case DOWN:
                    downFloor();
                    break;
                default:
                    break;
            }
        }
    }

    public void upFloor() {
        floor += 1;
        view.changeFloor(floor);
        if (floorButtonPressed[floor]) {
            finishJob();
        }
    }

    public void downFloor() {
        floor -= 1;
        view.changeFloor(floor);
        if (floorButtonPressed[floor]) {
            finishJob();
        }
    }

    /**
     * 任务完成
     */
    private void finishJob() {
        System.out.println("==========》》》任务完成："+floor);
        // 改变当前电梯展示状态
        view.changeState(DOOR_OPEN);
        // 停靠楼层为true
        arriveFloor = true;

        // 更新minJob和maxJob
        maxJob = 0;
        minJob = Floor.TOTAL_FLOOR + 1;
        for (int i = 1; i <= Floor.TOTAL_FLOOR; ++i) {
            // 遍历寻找按钮 重置任务最大最小
            if (floorButtonPressed[i]) {
                if (i < minJob) {
                    minJob = i;
                }
                if (i > maxJob)
                    maxJob = i;
            }
        }

        int tempState = state;
        // 上行到最高任务楼层
        if (state == UP && maxJob <= floor) {
            // 更新state
            if (minJob < floor) {
                state = DOWN;
            } else {
                state = FREE;
            }
            // 下行到最低任务楼层
        } else if (state == DOWN && minJob >= floor) {
            // 更新state
            if (maxJob > floor) {
                state = UP;
            } else {
                state = FREE;
            }
        }
        floorButtonPressed[floor] = false;
        if (order == tempState) {
            order = state;
        }

        // 修改按钮可用
        System.out.println("修改按钮可按："+floor);
        view.arriveFloor(floor);
        if (outJobDirection[floor] != FREE) {
            if (tempState != order) {
                changeOrder = true;
                int temp = tempState;
                tempState = state;
                state = temp;
            }
            floorView.finishJob(floor, outJobDirection[floor]);
            outJobDirection[floor] = FREE;
        }
    }

    public void add(JPanel panel) {
        panel.add(this.view);
    }
}