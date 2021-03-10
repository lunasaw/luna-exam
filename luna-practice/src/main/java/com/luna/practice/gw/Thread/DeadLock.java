package com.luna.practice.gw.Thread;

/**
 * @author Luna@win10
 * @date 2020/5/13 13:25
 */
public class DeadLock implements Runnable {

	//决定线程走向的标记
	private int flag;

	//锁对象1 static 变成共享资源
	private static Object object1 = new Object();

	//锁对象2
	private static Object object2 = new Object();

	public DeadLock(int flag) {
		this.flag = flag;
	}

	@Override
	public void run() {
		if (flag == 1) {
			//这是线程1执行: 拥有一个资源,请求另一个资源
			synchronized (object1) {
				System.out.println("这是线程1:" + Thread.currentThread().getName() + "已获取到资源Object1,正在请求Object2");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				synchronized (object2) {
					System.out.println("线程1执行完,请求线程2" + Thread.currentThread().getName() + "已经获取到");
				}
			}
		} else {
			//线程2执行
			synchronized (object2) {
				System.out.println("这是线程2:" + Thread.currentThread().getName() + "已获取到资源Object2,正在请求Object1");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				synchronized (object1) {
					System.out.println("线程2执行完,请求线程1" + Thread.currentThread().getName() + "已经获取到");
				}
			}
		}
	}
}

class Test {
	public static void main(String[] args) {
		//1. 创建两个线程对象实例 分别运行 一个flag=1 一个flag=2
		DeadLock deadLock1 = new DeadLock(1);
		Thread thread1 = new Thread(deadLock1,"线程1");
		DeadLock deadLock2 = new DeadLock(2);
		Thread thread2 = new Thread(deadLock2,"线程2");
		thread1.start();
		thread2.start();
	}
}