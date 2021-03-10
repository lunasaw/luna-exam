package com.luna.practice.gw.practice;

import java.util.concurrent.locks.ReentrantLock;


public class SyncThreadDemo {

	public static void main(String[] args) {
		MyRunnable mr = new MyRunnable();

		new Thread(mr).start();
		new Thread(mr).start();
	}

}

class MyRunnable implements Runnable {

	private int tickts = 10;

	@Override
	public void run() {

		for (int i = 0; i < 100; i++) {
//			if (tickts > 0) {
			// 方法一：使用同步代码块
//				synchronized (this) {
//					System.out.println("第" + (tickts--) + "张票售出");
//					try {
//						Thread.sleep(100);
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}
//				}
//			}
			// 方法二：使用同步方法达到线程安全
			saleTickts();

			// 方法三：使用ReentrantLock，更加灵活，可以加入判断，在特定的情况下释放锁
			saleTickts2();
		}// for
	}

	// 同步方法：同步的是当前对象
	private synchronized void saleTickts() {
		if (tickts > 0) {
			System.out.println("第" + (tickts--) + "张票售出");
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}// saleTickts

	ReentrantLock lock = new ReentrantLock();

	// 方法三：使用ReentrantLock，更加灵活，可以加入判断，在特定的情况下释放锁
	private void saleTickts2() {
		lock.lock();
		try {
			if (tickts > 0) {
				System.out.println("第" + (tickts--) + "张票售出");
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			// 保证锁一定会被释放，不会出现死锁情况
		} finally {
			lock.unlock();// 释放锁
		}
	}// saleTickts2
}