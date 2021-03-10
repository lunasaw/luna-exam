package com.luna.practice.gw.applet.container;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

import java.util.*;

//SplitPaneDemo itself is not a visible component.
public class SplitPaneDemo implements ListSelectionListener {
    private Vector imageNames;
    private JLabel picture;
    private JList list;
    private JSplitPane splitPane;

    public SplitPaneDemo() {
        //Read image names from a properties file
        ResourceBundle imageResource;
        try {
            imageResource = ResourceBundle.getBundle("imagenames");
            String imageNamesString = imageResource.getString("images");
            imageNames = parseList(imageNamesString);
        } catch (MissingResourceException e) {
            System.err.println();
            System.err.println("Can't find the properties file " +
                               "that contains the image names.");
            System.err.println("Its name should be imagenames.properties, " +
                               "and it should");
            System.err.println("contain a single line that specifies " +
                	       "one or more image");
            System.err.println("files to be found in a directory " +
                               "named images.  Example:");
            System.err.println();
            System.err.println("    images=Bird.gif Cat.gif Dog.gif");
            System.err.println();
            System.exit(1);
        }

        //Create the list of images and put it in a scroll pane
        list = new JList(imageNames);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.addListSelectionListener(this);
        JScrollPane listScrollPane = new JScrollPane(list);

        //Set up the picture label and put it in a scroll pane
        //ImageIcon firstImage = new ImageIcon("./build/classes/"+(String)
ImageIcon firstImage = new ImageIcon("./"+(String)imageNames.firstElement());

        picture = new JLabel(firstImage);
        picture.setPreferredSize(new Dimension(firstImage.getIconWidth(),
                                               firstImage.getIconHeight()));
        JScrollPane pictureScrollPane = new JScrollPane(picture);

        //Create a split pane with the two scroll panes in it
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                                   listScrollPane, pictureScrollPane);
        splitPane.setOneTouchExpandable(true);
        splitPane.setDividerLocation(150);

        //Provide minimum sizes for the two components in the split pane
        Dimension minimumSize = new Dimension(100, 50);
        listScrollPane.setMinimumSize(minimumSize);
        pictureScrollPane.setMinimumSize(minimumSize);

        //Provide a preferred size for the split pane
        splitPane.setPreferredSize(new Dimension(400, 200));
    }

    //Used by SplitPaneDemo2
    public JList getImageList() {
        return list;
    }

    public JSplitPane getSplitPane() {
        return splitPane;
    }

    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting())
            return;

        JList theList = (JList)e.getSource();
        if (theList.isSelectionEmpty()) {
            picture.setIcon(null);
        } else {
            int index = theList.getSelectedIndex();
            ImageIcon newImage = new ImageIcon("./"+ 
                                     (String)imageNames.elementAt(index));
            picture.setIcon(newImage);
            picture.setPreferredSize(new Dimension(newImage.getIconWidth(),
                                               newImage.getIconHeight() ));
            picture.revalidate();
        }
    }

    protected static Vector parseList(String theStringList) {
        Vector v = new Vector(10);
        StringTokenizer tokenizer = new StringTokenizer(theStringList, " ");
        while (tokenizer.hasMoreTokens()) {
            String image = tokenizer.nextToken();
            v.addElement(image);
        }
        return v;
    }

    public static void main(String s[]) {
        JFrame frame = new JFrame("SplitPaneDemo");

        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {System.exit(0);}
        });

        SplitPaneDemo splitPaneDemo = new SplitPaneDemo();
        frame.getContentPane().add(splitPaneDemo.getSplitPane());
        frame.pack();
        frame.setVisible(true);
    }
}
