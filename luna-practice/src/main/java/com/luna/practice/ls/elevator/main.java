package com.luna.practice.ls.elevator;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class main
{
	public static void main(String[] args)
	{
		Manager ELEVATOR=new Manager();
		ELEVATOR.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e) {
				System.exit(0);// TODO Auto-generated Event stub windowClosing()
			}
		});
		ELEVATOR.setSize(944, 573);
		ELEVATOR.setVisible(true);
	}

}
