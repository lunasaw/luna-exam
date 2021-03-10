package com.luna.practice.ls.elevator;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URL;

class Ring {
    Ring() {
    }

    @SuppressWarnings("deprecation")
    public void sound() {
        try {
            File file = new File("com/luna/practice/ls/elevator/lift.wav");
            URL url = file.toURL();
            AudioClip clip = Applet.newAudioClip(url);
            clip.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

public class Elevator extends JPanel implements Runnable {
        /**  */
    private static final long serialVersionUID = 1L;
    /**  */
    private JButton[] button = new JButton[20];
    /**  */
    private JButton[] Button = new JButton[20];
    /**  */
    private int CurPosition = 0;
    /** 当前去那一层 */
    private int ToFloor = 0;
    /** 电梯状态 升 降 停 */
    private int Up = 1, Down = -1, Still = 0;
    /** 操作 */
    private int Direction;
    /**  */
    private Thread thread;
    /** 电梯在当前层到状态 */
    private boolean[] state = new boolean[20];
    /** 当前电梯状态展示 */
    private JLabel jlabel = new JLabel();
    /** 实时层数展示 */
    private JLabel jlabel1 = new JLabel();
    /** 按钮背景色 */
    private Color color = Color.CYAN;  //  @jve:decl-index=0:
    /**  */
    private JButton NoUse = new JButton();
    /**  */
    private Ring ring = new Ring();
    /** SOS按钮 */
    private JButton alarm = new JButton("SOS");
    /**  */
    private int Next_Direction;


    /**
     * 默认构造方法
     */
    public Elevator() {
        super();
        initialize();
    }

    public Thread getthread() {
        return thread;
    }

    /**
     * This method initializes this
     *
     * @return void
     */
    private void initialize() {
        this.setSize(152, 506);
        this.setLayout(new GridLayout(22, 2));
        this.setBackground(Color.LIGHT_GRAY);
        Direction = Still;
        Next_Direction = Still;
        thread = new Thread(this);
        jlabel.setText("STILL");
        jlabel.setFont(new Font("Consolas", Font.BOLD, 20));
        jlabel.setForeground(Color.blue);
        jlabel.setHorizontalAlignment(SwingConstants.CENTER);
        jlabel1.setText("1");
        jlabel1.setFont(new Font("Consolas", Font.BOLD, 20));
        jlabel1.setForeground(Color.blue);
        jlabel1.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(jlabel);
        this.add(jlabel1);
        for (int i = 19; i >= 0; i--) {
            state[i] = false;
            button[i] = new JButton();
            Button[i] = new JButton();
            button[i].setText(String.valueOf(i + 1));
            Button[i].setText(String.valueOf(i + 1));
            Button[i].setBackground(Color.lightGray);
            Button[i].addActionListener(new Action());
            button[i].setBorder(javax.swing.BorderFactory.createLineBorder(Color.blue, 4));
            button[i].setEnabled(false);
            button[i].setBackground(Color.black);
            this.add(button[i]);
            this.add(Button[i]);
        }
        button[0].setBackground(Color.red);
        alarm.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                ring.sound();
            }

        });
        alarm.setBackground(Color.LIGHT_GRAY);
        alarm.setForeground(Color.red);
        NoUse.setEnabled(false);
        NoUse.setBackground(Color.LIGHT_GRAY);
        this.add(NoUse);
        this.add(alarm);
    }

    class Action implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            for (int i = 0; i < 20; i++) {
                if (e.getSource() == Button[i]) {
                    state[i] = true;
                    Button[i].setBackground(color);
                    // 如果当前电梯为停止
                    if (Direction == Still) {
                        // 设置电梯目的层数
                        ToFloor = i;
                        // 如果当前电梯为上升
                    } else if (Direction == Up) {
                        // 则上升到最高需求楼层
                        ToFloor = MaxToFloor();
                    } else if (Direction == Down) {
                        // 则下降到最低需求楼层
                        ToFloor = MinToFloor();
                    }
                }
            }
        }
    }

    /**
     * 获取当前电梯要达到到最高楼层
     * @return
     */
    private int MaxToFloor() {
        int Max = -1;
        for (int i = 19; i >= 0; i--) {
            // 遍历层数是否有请求到第一个请求楼层，获取最大到层数 从上往下数
            if (state[i]) {
                Max = i;
                break;
            }
        }
        return Max;
    }

    /**
     * 获取当前电梯要达到到最低楼层
     * @return
     */
    private int MinToFloor() {
        int Min = -1;
        for (int i = 0; i < 20; i++) {
            // 遍历层数是否有请求到第一个请求楼层，获取最小到层数 从下往上数
            if (state[i]) {
                Min = i;
                break;
            }
        }
        return Min;
    }


    public void run() {
        while (true) {
            try {
                thread.sleep(100);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            // 如果当前目的楼层当前位置
            if (ToFloor > CurPosition) {
                // 操作更改为向上
                Direction = Up;
                // 开始向上
                MoveUp();
                Direction = Still;
                jlabel.setText("STILL");
            } else if (ToFloor < CurPosition) {
                Direction = Down;
                MoveDown();
                Direction = Still;
                jlabel.setText("STILL");
            }
            for (int i = 0; i < 20; i++) {
                if (state[i]) {
                    if (Direction == Still) {
                        ToFloor = i;
                    } else if (Direction == Up) {
                        ToFloor = MaxToFloor();
                    } else if (Direction == Down) {
                        ToFloor = MinToFloor();
                    }
                }
            }
        }
    }

    /**
     * 电梯向上移动操作
     */
    private void MoveUp() {
        // 如果下次操作为上升或者停止
        if (Next_Direction == Up || Next_Direction == Still) {
            // 设置当前标签内容
            jlabel.setText("UP");
            int OldCurPosition = CurPosition;
            try {
                thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            button[OldCurPosition].setBackground(Color.black);
            for (int i = OldCurPosition + 1; i < ToFloor; i++) {
                // 在操作启动层与目的层之间移动
                button[i].setBackground(Color.red);
                jlabel1.setText(String.valueOf(i + 1));
                try {
                    thread.sleep(1000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                button[i].setBackground(Color.black);
                CurPosition = i;
                if (state[i]) {
                    state[i] = false;
                    Button[i].setBackground(null);
                    button[i].setBackground(Color.white);
                    ring.sound();
                    try {
                        thread.sleep(2000);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    button[i].setBackground(Color.red);
                    try {
                        thread.sleep(1000);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    button[i].setBackground(Color.black);
                }
            }
            button[ToFloor].setBackground(Color.red);
            jlabel1.setText(String.valueOf(ToFloor + 1));
            Button[ToFloor].setBackground(null);
            try {
                thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            button[ToFloor].setBackground(Color.white);
            ring.sound();
            try {
                thread.sleep(2000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            button[ToFloor].setBackground(Color.red);
            CurPosition = ToFloor;

            state[ToFloor] = false;
        } else if (Next_Direction == Down) {
            jlabel.setText("UP");
            int OldCurPosition = CurPosition;
            try {
                thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            button[OldCurPosition].setBackground(Color.black);
            for (int i = OldCurPosition + 1; i < ToFloor; i++) {
                button[i].setBackground(Color.red);
                jlabel1.setText(String.valueOf(i + 1));
                try {
                    thread.sleep(1000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                button[i].setBackground(Color.black);
                CurPosition = i;
            }
            button[ToFloor].setBackground(Color.red);
            jlabel1.setText(String.valueOf(ToFloor + 1));
            Button[ToFloor].setBackground(null);
            try {
                thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            button[ToFloor].setBackground(Color.white);
            ring.sound();
            try {
                thread.sleep(2000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            button[ToFloor].setBackground(Color.red);
            CurPosition = ToFloor;
            state[ToFloor] = false;
            if (MinToFloor() != -1) {
                ToFloor = MinToFloor();
            }
            Next_Direction = Still;
            MoveDown();

        }
    }

    private void MoveDown() {
        // TODO Auto-generated method stub
        if (Next_Direction == Down || Next_Direction == Still) {
            jlabel.setText("DOWN");
            int OldCurPosition = CurPosition;
            try {
                thread.sleep(100);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            button[OldCurPosition].setBackground(Color.black);
            for (int i = OldCurPosition - 1; i > ToFloor; i--) {

                button[i].setBackground(Color.red);
                jlabel1.setText(String.valueOf(i + 1));
                try {
                    thread.sleep(1000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                button[i].setBackground(Color.black);
                CurPosition = i;
                if (state[i]) {
                    state[i] = false;
                    Button[i].setBackground(null);
                    button[i].setBackground(Color.white);
                    ring.sound();
                    try {
                        thread.sleep(2000);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    button[i].setBackground(Color.red);
                    try {
                        thread.sleep(1000);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    button[i].setBackground(Color.black);
                }
            }
            button[ToFloor].setBackground(Color.red);
            jlabel1.setText(String.valueOf(ToFloor + 1));
            Button[ToFloor].setBackground(null);
            try {
                thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            button[ToFloor].setBackground(Color.white);
            ring.sound();
            try {
                thread.sleep(2000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            button[ToFloor].setBackground(Color.red);
            CurPosition = ToFloor;
            state[ToFloor] = false;
        } else if (Next_Direction == Up) {
            jlabel.setText("DOWN");
            int OldCurPosition = CurPosition;
            try {
                thread.sleep(100);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            button[OldCurPosition].setBackground(Color.black);
            for (int i = OldCurPosition - 1; i > ToFloor; i--) {

                button[i].setBackground(Color.red);
                jlabel1.setText(String.valueOf(i + 1));
                try {
                    thread.sleep(1000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                button[i].setBackground(Color.black);
                CurPosition = i;
            }
            button[ToFloor].setBackground(Color.red);
            jlabel1.setText(String.valueOf(ToFloor + 1));
            Button[ToFloor].setBackground(null);
            try {
                thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            button[ToFloor].setBackground(Color.white);
            ring.sound();
            try {
                thread.sleep(2000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            button[ToFloor].setBackground(Color.red);
            CurPosition = ToFloor;
            state[ToFloor] = false;
            if (MaxToFloor() != -1) {
                ToFloor = MaxToFloor();
            }
            Next_Direction = Still;
            MoveUp();
        }
    }

    public void setToFloor(int i) {
        state[i] = true;
        if (Direction == Still)
            ToFloor = i;
        else if (Direction == Up)
            ToFloor = MaxToFloor();
        else if (Direction == Down) {
            ToFloor = MinToFloor();
        }
    }

    public int getCurPosition() {
        return CurPosition;
    }

    public int getDirection() {
        return Direction;
    }

    public int getToFloor() {

        return ToFloor;
    }

    public void Set_NextDirectionUp() {
        Next_Direction = Up;
    }

    public void Set_NextDirectionDown() {
        Next_Direction = Down;
    }

    public int get_NextDirection() {
        return Next_Direction;
    }
}//  @jve:decl-index=0:visual-constraint="10,10"



















































