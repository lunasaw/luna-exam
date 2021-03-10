package com.luna.practice.gw.Thread;

/**
 * @author Luna@win10
 * @date 2030/5/12 12:52
 */
public class ThreadDemo_3 {

	public static void main(String[] args) {
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				//需要线程执行代码
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				for (int i = 0; i < 30; i++) {
					System.out.println("内部类多线程:" + i);
				}
			}
		});

		thread.setDaemon(true);
		thread.start();
		//设为守护线程,与主线程一起销毁
		for (int i = 0; i < 5; i++) {
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("主线程:" + i);
		}
		System.out.println("主线程执行完毕");
	}

}

/**
 * 匿名内部类
 */
abstract class CreateThreadDemo03 {
	public abstract void add();
}
