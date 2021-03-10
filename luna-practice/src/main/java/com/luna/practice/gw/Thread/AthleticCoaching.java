package com.luna.practice.gw.Thread;

import java.util.concurrent.CountDownLatch;

/**
 * @author Luna@win10
 * @date 2020/5/13 22:39
 */
public class AthleticCoaching {

	//设置要等待的运动员
	private CountDownLatch countDownLatch = new CountDownLatch(3);

	/**
	 * 运动员方法
	 */
	public void race() throws InterruptedException {
		//1. 先获取运动员名称--线程名
		String name = Thread.currentThread().getName();
		//2. 运动员开始准备
		System.out.println(name + "开始准备:");
		//3. 等待,表示正在准备
		Thread.sleep(1000);
		//4. 准备完毕
		System.out.println(name + "准备完毕:");
		countDownLatch.countDown();
	}

	/**
	 * 教练方法
	 */
	public void coach() throws InterruptedException {
		//1. 获取教练名称
		String name = Thread.currentThread().getName();
		//2. 教练等待所有运动员准备完毕
		System.out.println(name + "等待运动员准备");
		//3. 调用countDownLatchd await()方法 等待其他线程完毕
		countDownLatch.await();
		//4. 开始训练
		System.out.println("所有运动员已经就绪!" + name + "训练开始");
	}

	public static void main(String[] args) {
		//1. 创建AthleticCoaching实例
		AthleticCoaching athleticCoaching = new AthleticCoaching();
		//2. 创建三个线程调用运动员方法
		Thread thread1 = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					athleticCoaching.race();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}, "运动员1");
		Thread thread2 = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					athleticCoaching.race();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}, "运动员2");
		Thread thread3 = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					athleticCoaching.race();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}, "运动员3");
		//3. 创建一个线程调用教练方法
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					athleticCoaching.coach();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}, "教练");
		thread.start();
		thread1.start();
		thread2.start();
		thread3.start();
	}
}
