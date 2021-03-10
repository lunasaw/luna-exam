package com.luna.practice.gw.Thread;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author Luna@win10
 * @date 2020/5/13 22:56
 */
public class ThreadDemo_6 {

	/** 参数为参与此次准备的线程数
	 *  多线程同时执行
	 *
	 */
	private CyclicBarrier cyclicBarrier=new CyclicBarrier(3);
	/**
	 *
	 */
	public void startThread() {
		//1. 打印线程启动
		String name = Thread.currentThread().getName();
		System.out.println(name+"正在准备:");
		//2. 调用CyclicBarrier 的 await()方法全部准备完成
		try {
			cyclicBarrier.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (BrokenBarrierException e) {
			e.printStackTrace();
		}
		//3. 打印相应信息
		System.out.println(name+"线程准备完毕,开始打印"+System.currentTimeMillis());
	}

	public static void main(String[] args) {
		ThreadDemo_6 threadDemo_6=new ThreadDemo_6();
		Thread thread1=new Thread(new Runnable() {
			@Override
			public void run() {
				threadDemo_6.startThread();
			}
		},"线程1");
		Thread thread2=new Thread(new Runnable() {
			@Override
			public void run() {
				threadDemo_6.startThread();
			}
		},"线程2");
		Thread thread3=new Thread(new Runnable() {
			@Override
			public void run() {
				threadDemo_6.startThread();
			}
		},"线程3");
		thread1.start();
		thread2.start();
		thread3.start();
	}
}
