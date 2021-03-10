package com.luna.practice.gw.Thread;

/**
 * @author Luna@win10
 * @date 2020/5/12 12:52
 */
public class ThreadDemo_5 {

	/**
	 * ��Ҫ T2 �� T1��ִ�� T3��T2 ��ִ��
	 * join ��ʾ�ó���ǰ�̸߳������߳�
	 * @param args
	 */
	public static void main(String[] args) {
		Thread thread1 = new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < 5; i++) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println("����һ���߳�1:" + Thread.currentThread().getId());
				}
			}
		});
		thread1.start();
		Thread thread2 = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					thread1.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				for (int i = 0; i < 5; i++) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println("����һ���߳�2:" + Thread.currentThread().getId());
				}
			}
		});
		thread2.start();
		Thread thread3 = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					thread2.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				for (int i = 0; i < 5; i++) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println("����һ���߳�3:" + Thread.currentThread().getId());
				}
			}
		});
		thread3.start();
	}

}


