package com.luna.practice.ls.elevator;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.SwingConstants;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;

public class Manager extends JFrame implements Runnable {

    private static final long serialVersionUID = 1L;
    private static final int ELEVATOR_SIZE = 2;
    private JPanel jContainPanel = null;
    private JPanel jPanel = null;
    private JButton[] UpButton = new JButton[20];
    private JButton[] DownButton = new JButton[20];

    private Elevator[] elevator = new Elevator[ELEVATOR_SIZE];
    private boolean[] UpState = new boolean[20];
    private boolean[] DownState = new boolean[20];
    private Thread thread;
    private JLabel jlabel, jlabel1;

    /**
     * This is the default constructor
     */
    public Manager() {
        super();
        initialize();
        thread.start();
    }

    /**
     * This method initializes this
     *
     * @return void
     */
    private void initialize() {
        this.setSize(944, 573);
        this.setContentPane(getJContainPanel());
        this.setTitle("Elevator");
        thread = new Thread(this);
        for (int i = 0; i < 20; i++) {
            UpState[i] = false;
            DownState[i] = false;
        }

    }

    /**
     * This method initializes jContainPanel
     *
     * @return javax.swing.JPanel
     */
    private JPanel getJContainPanel() {
        if (jContainPanel == null) {
            jContainPanel = new JPanel();
            jContainPanel.setLayout(new GridLayout(1, 6));
            jContainPanel.add(getJPanel(), null);
            for (int i = 0; i < ELEVATOR_SIZE; i++) {
                Elevator Ele = new Elevator();
                Ele.setBorder(BorderFactory.createTitledBorder(null, "Elevator" + (i + 1), TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
                Ele.getthread().start();
                jContainPanel.add(Ele);
                elevator[i] = Ele;
            }
        }
        return jContainPanel;
    }

    /**
     * This method initializes jPanel
     *
     * @return javax.swing.JPanel
     */
    private JPanel getJPanel() {
        if (jPanel == null) {
            jPanel = new JPanel();
            jPanel.setBackground(Color.LIGHT_GRAY);
            jPanel.setLayout(new GridLayout(22, 2));
            jlabel = new JLabel("UP");
            jlabel1 = new JLabel("DOWN");
            jlabel.setHorizontalAlignment(SwingConstants.CENTER);
            jlabel1.setHorizontalAlignment(SwingConstants.CENTER);
            jPanel.add(jlabel);
            jPanel.add(jlabel1);
            jPanel.setBounds(new Rectangle(1, 0, 166, 533));
            jPanel.setBorder(BorderFactory.createTitledBorder(null, "Control", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
            for (int i = 19; i >= 0; i--) {
                UpButton[i] = new JButton();
                DownButton[i] = new JButton();
                UpButton[i].setBackground(Color.DARK_GRAY);
                DownButton[i].setBackground(Color.DARK_GRAY);
                UpButton[i].addActionListener(new Action());
                UpButton[i].setText("UP");
                DownButton[i].setText("DOWN");
                UpButton[i].setForeground(Color.white);
                DownButton[i].setForeground(Color.white);
                DownButton[i].addActionListener(new Action());
                jPanel.add(UpButton[i]);
                jPanel.add(DownButton[i]);
            }
            UpButton[19].setEnabled(false);
            DownButton[0].setEnabled(false);
            UpButton[19].setText("");
            DownButton[0].setText("");
        }
        return jPanel;
    }

    class Action implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            for (int i = 0; i < 20; i++) {
                if (e.getSource() == UpButton[i]) {
                    UpState[i] = true;
                } else if (e.getSource() == DownButton[i]) {
                    DownState[i] = true;
                }
            }
        }
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub

        while (true) {
            for (int i = 0; i < 20; i++) {
                if (UpState[i]) {
                    ManageUpElevator(i);
                }
                if (DownState[i]) {
                    ManageDownElevator(i);
                }
            }
        }

    }

    private void ManageDownElevator(int i) {
        // TODO Auto-generated method stub
        int Elevator1 = 0, Elevator2 = 0, Elevator = -1;
        int Distance1 = 20;
        int Distance2 = 20;
        int Distance = 20;
        int Temp;
        for (int j = 0; j < ELEVATOR_SIZE; j++) {
            if ((elevator[j].get_NextDirection() == -1) && (elevator[j].getDirection() == 1)) {
                if (i < elevator[j].getToFloor()) {
                    Temp = Math.abs(i - elevator[j].getToFloor());
                    if (Temp < Distance) {
                        Elevator = j;
                        Distance = Temp;
                    }
                }
            }
        }
        if (Distance != 20) {
            elevator[Elevator].Set_NextDirectionDown();
            elevator[Elevator].setToFloor(i);
            DownState[i] = false;
            return;
        } else {
            for (int j = 0; j < ELEVATOR_SIZE; j++) {
                if ((elevator[j].getDirection() == -1) && (i < elevator[j].getCurPosition()) && (elevator[j].get_NextDirection() == -1)) {
                    Temp = Math.abs(i - elevator[j].getCurPosition());
                    if (Temp < Distance1) {
                        Elevator1 = j;
                        Distance1 = Temp;
                    }
                }
            }
            for (int j = 0; j < ELEVATOR_SIZE; j++) {
                if (elevator[j].getDirection() == 0) {
                    Temp = Math.abs(i - elevator[j].getCurPosition());
                    if (Temp < Distance2) {
                        Elevator2 = j;
                        Distance2 = Temp;
                    }
                }
            }
            if ((Distance1 != 20) || (Distance2 != 20)) {
                if (Distance1 <= Distance2) {
                    elevator[Elevator1].Set_NextDirectionDown();
                    elevator[Elevator1].setToFloor(i);
                    DownState[i] = false;
                } else if (Distance2 < Distance1) {
                    elevator[Elevator2].Set_NextDirectionDown();
                    elevator[Elevator2].setToFloor(i);
                    DownState[i] = false;
                }
            }
        }
    }

    private void ManageUpElevator(int i) {
        // TODO Auto-generated method stub
        int Elevator1 = 0, Elevator2 = 0, Elevator = 0;
        int Distance1 = 20;
        int Distance2 = 20;
        int Distance = 20;
        int Temp;
        for (int j = 0; j < ELEVATOR_SIZE; j++) {
            if ((elevator[j].get_NextDirection() == 1) && (elevator[j].getDirection() == -1)) {
                if (i > elevator[j].getToFloor()) {
                    Temp = Math.abs(i - elevator[j].getToFloor());
                    if (Temp < Distance) {
                        Elevator = j;
                        Distance = Temp;
                    }
                }
            }
        }
        if (Distance != 20) {
            elevator[Elevator].Set_NextDirectionUp();
            elevator[Elevator].setToFloor(i);
            UpState[i] = false;
            return;
        } else {
            for (int j = 0; j < ELEVATOR_SIZE; j++) {
                if ((elevator[j].getDirection() == 1) && (i > elevator[j].getCurPosition()) && (elevator[j].get_NextDirection() == 1)) {
                    Temp = Math.abs(i - elevator[j].getCurPosition());
                    if (Temp < Distance1) {
                        Elevator1 = j;
                        Distance1 = Temp;
                    }
                }
            }
            for (int j = 0; j < ELEVATOR_SIZE; j++) {
                if (elevator[j].getDirection() == 0) {
                    Temp = Math.abs(i - elevator[j].getCurPosition());
                    if (Temp < Distance2) {
                        Elevator2 = j;
                        Distance2 = Temp;
                    }
                }
            }
            if ((Distance1 != 20) || (Distance2 != 20)) {
                if (Distance1 <= Distance2) {
                    elevator[Elevator1].Set_NextDirectionUp();
                    elevator[Elevator1].setToFloor(i);
                    UpState[i] = false;
                } else if (Distance2 < Distance1) {
                    elevator[Elevator2].Set_NextDirectionUp();
                    elevator[Elevator2].setToFloor(i);
                    UpState[i] = false;
                }
            }
        }
    }


}  //  @jve:decl-index=0:visual-constraint="10,10"






















