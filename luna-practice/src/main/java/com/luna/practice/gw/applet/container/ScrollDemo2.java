package com.luna.practice.gw.applet.container;/*
 * This code is based on an example provided by John Vella,
 * a tutorial reader.
 */

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class ScrollDemo2 extends JPanel {
    private Dimension size; // indicates size taken up by graphics
    private Vector objects; // rectangular coordinates used to draw graphics

    private final Color colors[] = {
        Color.red, Color.blue, Color.green, Color.orange,
        Color.cyan, Color.magenta, Color.darkGray, Color.yellow};
    private final int color_n = colors.length;

    JPanel drawingArea;

    public ScrollDemo2() {
        setOpaque(true);
        size = new Dimension(0,0);
        objects = new Vector();

        //Set up the instructions.
        JLabel instructionsLeft = new JLabel(
                        "Click left mouse button to place a circle.");
        JLabel instructionsRight = new JLabel(
                        "Click right mouse button to clear drawing area.");
        JPanel instructionPanel = new JPanel(new GridLayout(0,1));
        instructionPanel.add(instructionsLeft);
        instructionPanel.add(instructionsRight);

        //Set up the drawing area.
        drawingArea = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                Rectangle rect;
                for (int i = 0; i < objects.size(); i++) {
                    rect = (Rectangle)objects.elementAt(i);
                    g.setColor(colors[(i % color_n)]);
                    g.fillOval(rect.x, rect.y, rect.width, rect.height);
                }
            }
        };
        drawingArea.setBackground(Color.white);
        drawingArea.addMouseListener(new MyMouseListener());

        //Put the drawing area in a scroll pane.
        JScrollPane scroller = new JScrollPane(drawingArea);
        scroller.setPreferredSize(new Dimension(200,200));

        //Layout this demo.
        setLayout(new BorderLayout());
        add(instructionPanel, BorderLayout.NORTH);
        add(scroller, BorderLayout.CENTER);
    }

    class MyMouseListener extends MouseInputAdapter {
        final int W = 100;
        final int H = 100;

        public void mouseReleased(MouseEvent e) {
            boolean changed = false;
            if (SwingUtilities.isRightMouseButton(e)) {
                // This will clear the graphic objects.
                objects.removeAllElements();
                size.width=0;
                size.height=0;
                changed = true;
            } else {
                int x = e.getX() - W/2;
                int y = e.getY() - H/2;
                if (x < 0) x = 0;
                if (y < 0) y = 0;
                Rectangle rect = new Rectangle(x, y, W, H);
                objects.addElement(rect);
                drawingArea.scrollRectToVisible(rect);

                int this_width = (x + W + 2);
                if (this_width > size.width)
                    {size.width = this_width; changed=true;}

                int this_height = (y + H + 2);
                if (this_height > size.height)
                    {size.height = this_height; changed=true;}
            }
            if (changed) {
                //Update client's preferred size because 
                //the area taken up by the graphics has
                //gotten larger or smaller (if cleared).
                drawingArea.setPreferredSize(size);

                //Let the scroll pane know to update itself
                //and its scrollbars.
                drawingArea.revalidate();
            }
            drawingArea.repaint();
        }
    }

    public static void main (String args[]) {
        JFrame frame = new JFrame("ScrollDemo2");
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {System.exit(0);}
        });

        frame.setContentPane(new ScrollDemo2());
        frame.pack();
        frame.setVisible(true);
    }
}
