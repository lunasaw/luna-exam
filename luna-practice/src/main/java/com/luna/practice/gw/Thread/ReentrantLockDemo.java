package com.luna.practice.gw.Thread;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Luna@win10
 * @da 2020/5/14 14:05
 */
public class ReentrantLockDemo {

	public static void main(String[] args) {
		ReentrantLock reentrantLock = new ReentrantLock();
		for (int i = 0; i < 10; i++) {
			reentrantLock.lock();
			try {
				System.out.println("加锁次数+1" + i);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		for (int i = 0; i < 10; i++) {
			try {
				System.out.println("解锁次数+1" + i);
			} finally {
				reentrantLock.unlock();
			}
		}
	}

}
