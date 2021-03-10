package com.luna.practice.gw.Thread;

/**
 * @author Luna@win10
 * @date 2020/5/12 12:52
 */
public class ThreadDemo_2 {

	public static void main(String[] args) {
		/**
		 * 调用线程
		 * 1. 实例化线程
		 * 2. new Thread
		 */
		CreateThreadDemo02 createThreadDemo02 = new CreateThreadDemo02();
		Thread thread = new Thread(createThreadDemo02);
		CreateThreadDemo05 createThreadDemo05 = new CreateThreadDemo05();
		Thread thread2 = new Thread(createThreadDemo05);
		thread.start();
		for (int i = 0; i < 30; i++) {
			System.out.println("主线程: " + i);
		}
		/**
		 * 开启多线程后 代码不会从上往下执行
		 * 1. 同步
		 * 2. 异步
		 */

	}

}

class CreateThreadDemo02 implements Runnable {
	@Override
	public void run() {
		for (int i = 0; i < 30; i++) {
			System.out.println("run: " + i);
		}
	}
}

class CreateThreadDemo05 implements Runnable {
	@Override
	public void run() {
		for (int i = 0; i < 30; i++) {
			System.out.println("run子线程: " + i);
		}
	}
}