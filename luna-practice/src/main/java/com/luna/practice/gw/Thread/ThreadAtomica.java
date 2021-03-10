package com.luna.practice.gw.Thread;

import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @author Luna@win10
 * @date 2020/5/14 12:36
 */
public class ThreadAtomica {
	//操作变量
	private static int n = 0;

	//使用原子类操作++
//	private static AtomicInteger atomicInteger = new AtomicInteger(n);

	//解决ABA问题
	private static AtomicStampedReference<Integer> atomicReference = new AtomicStampedReference(0, 0);

	public static void main(String[] args) {
		//在此用两个线程对n++
		Thread thread1 = new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < 1000; i++) {
//					atomicInteger.getAndIncrement();
//					n++;
					int stamp;
					Integer reference;
					do {
						stamp = atomicReference.getStamp();
						reference = atomicReference.getReference();
					} while (!atomicReference.compareAndSet(reference, reference + 1, stamp, stamp + 1));
				}
			}
		}, "线程1");
		Thread thread2 = new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < 1000; i++) {
//					atomicInteger.getAndIncrement();
//					n++;
					int stamp;
					Integer reference;
					do {
						stamp = atomicReference.getStamp();
						reference = atomicReference.getReference();
					} while (!atomicReference.compareAndSet(reference, reference + 1, stamp, stamp + 1));
				}
			}
		}, "线程2");
		thread1.start();
		thread2.start();
		try {
			thread1.join();
			thread2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
//		System.out.println("操作结束后" + atomicInteger.get());
		System.out.println("操作结束后" + atomicReference.getReference());
	}
}
