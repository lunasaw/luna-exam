package com.luna.practice.gw.Thread;

import java.util.concurrent.Semaphore;

/**
 * @author Luna@win10
 * @date 2020/5/13 23:44
 */
public class WorkMechine {

	static class Work implements Runnable {
		//工号
		private int workerNumber;
		//机器数
		private Semaphore semaphore;

		public Work(int workerNumber, Semaphore semaphore) {
			this.workerNumber = workerNumber;
			this.semaphore = semaphore;
		}

		/**
		 * 工人使用机器工作
		 */
		@Override
		public void run() {
			//1. 工人获取机器
			try {
				semaphore.acquire();
				//2. 打印工人获取到机器开始工作
				String name = Thread.currentThread().getName();
				System.out.println(name + "获取到机器");
				//3. 线程睡眠1秒模拟工作
				Thread.sleep(1000);
				//4. 使用完毕,释放机器
				semaphore.release();
				System.out.println("使用完毕,释放机器");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		//工人数
		int workers = 8;
		//机器数
		Semaphore semaphore = new Semaphore(3);
		for (int i = 0; i < workers; i++) {
			Thread thread = new Thread(new Work(i, semaphore));
			thread.start();
		}
	}

}

