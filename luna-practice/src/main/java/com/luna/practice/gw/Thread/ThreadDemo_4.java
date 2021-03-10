package com.luna.practice.gw.Thread;

/**
 * @author Luna@win10
 * @date 2020/5/12 12:52
 */
public class ThreadDemo_4 {

	public static void main(String[] args) {
		// 线程id
		for (int i = 0; i < 4; i++) {
			CreateThreadDemo04 createThreadDemo04 = new CreateThreadDemo04();
			createThreadDemo04.start();
		}
		//如何拿到主线程id 任意程序都有一个主线程
		//TODO Thread.currentThread() 获取当前线程
		System.out.println("主线程id" + Thread.currentThread().getId() + "主线程名" + Thread.currentThread().getName());

	}
}

class CreateThreadDemo04 extends Thread {
	@Override
	public void run() {
		for (int i = 0; i < 30; i++) {
			System.out.println("run: 子线程:" + getId() + "线程名:" + getName() + i);
			if (i == 5) {
				Thread.currentThread().stop();
			}
		}
	}
}
