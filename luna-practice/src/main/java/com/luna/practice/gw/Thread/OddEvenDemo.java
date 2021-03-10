package com.luna.practice.gw.Thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Luna@win10
 * @date 2020/5/13 22:13
 */
public class OddEvenDemo {
	//要打印的数
	private int anInt = 0;
//	private Object object = new Object();

	private Lock lock=new ReentrantLock();
	//帮助等待和唤醒线程
	private Condition condition=lock.newCondition();
	/**
	 * 奇数打印方法
	 */
	public void odd() throws InterruptedException {
		//1. 判断数是否小于10
		while (anInt < 10) {
			lock.lock();
//			synchronized (object) {
				//打印
			try {
				if (anInt % 2 == 1) {
					System.out.println(Thread.currentThread().getName() + "打印奇数:" + anInt);
					anInt++;
					condition.signal();
					//唤醒偶数线程打印
//					object.notify();
				} else {
					condition.await();
					//等待偶数线程执行
//					object.wait();
				}
			} finally {
				lock.unlock();
			}
//			}
		}
	}

	/**
	 * 偶数打印
	 */
	public void even() throws InterruptedException {
		//1. 判断数是否小于10
		while (anInt <= 10) {
			lock.lock();
//			synchronized (object) {
				//打印
			try {
				if (anInt % 2 == 0) {
					System.out.println(Thread.currentThread().getName() + "打印偶数:" + anInt);
					anInt++;
					condition.signal();
					//唤醒奇数线程打印
//					object.notify();
				} else {
					condition.await();
					//等待奇数线程执行
//					object.wait();
				}
			} finally {
				lock.unlock();
			}
//			}
		}
	}

	public static void main(String[] args) {
		OddEvenDemo oddEvenDemo = new OddEvenDemo();
		//1. 开启奇数线程打印
		Thread thread1 = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					oddEvenDemo.odd();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}, "打印奇数线程");
		//2. 开启偶数线程打印
		Thread thread2 = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					oddEvenDemo.even();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}, "打印偶数线程");
		thread1.start();
		thread2.start();
	}
}
