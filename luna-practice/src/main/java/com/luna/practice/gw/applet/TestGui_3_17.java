package com.luna.practice.gw.applet;

import javax.swing.*;
import java.applet.AudioClip;
import java.awt.*;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author Luna@win10
 * @date 2020/3/17 10:24
 */
public class TestGui_3_17 extends JApplet {

	AudioClip audioClip;
	Image image;
	Image image1;
	Image image2;
	Image image3;

	@Override
	public void init() {

		try {

			File audio = new File("D:\\books.user-improve\\endOfTerm\\Java\\Iszychen\\luna\\src\\gwjava\\audio\\happyActive.wav");
			URL au = audio.toURI().toURL();
			audioClip = getAudioClip(au, "happyActive.wav");

			File img = new File("D:\\books.user-improve\\endOfTerm\\Java\\Iszychen\\luna\\src\\gwjava\\imgs\\happy.jpg");
			URL im = img.toURI().toURL();
			image = getImage(im, "happy.jpg");

			File img1 = new File("D:\\books.user-improve\\endOfTerm\\Java\\Iszychen\\luna\\src\\gwjava\\imgs\\puke1.jpg");
			URL im1 = img1.toURI().toURL();
			image1 = getImage(im1, "puke1.jpg");

			File img2 = new File("D:\\books.user-improve\\endOfTerm\\Java\\Iszychen\\luna\\src\\gwjava\\imgs\\puke2.jpg");
			URL im2 = img2.toURI().toURL();
			image2 = getImage(im2, "puke2.jpg");

			File img3 = new File("D:\\books.user-improve\\endOfTerm\\Java\\Iszychen\\luna\\src\\gwjava\\imgs\\puke3.jpg");
			URL im3 = img3.toURI().toURL();
			image3 = getImage(im3, "puke3.jpg");


		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void paint(Graphics graphics) {
		graphics.drawString("Applet中播放声音和显示图像", 30, 30);
		audioClip.loop();
		graphics.drawImage(image, 60, 40, 400, 240, this);
		graphics.drawImage(image3, 350, 240, this);
		graphics.drawImage(image2, 30, 240, this);
		graphics.drawImage(image1, 190, 240, this);
	}

}
