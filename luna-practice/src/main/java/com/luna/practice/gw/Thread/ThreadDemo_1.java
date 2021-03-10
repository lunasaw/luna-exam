package com.luna.practice.gw.Thread;

/**
 * @author Luna@win10
 * @date 2020/5/12 12:52
 */
public class ThreadDemo_1 {

	public static void main(String[] args) {
		/**
		 * 调用线程
		 * 1. 实例化线程
		 */
		CreateThreadDemo01 createThreadDemo01=new CreateThreadDemo01();
		// 1. 调用start() 方法
		createThreadDemo01.start();
		for (int i = 0; i < 30; i++) {
			System.out.println("主线程: "+ i);
		}
		/**
		 * 开启多线程后 代码不会从上往下执行
		 * 1. 同步
		 * 2. 异步
		 */

	}

}

class CreateThreadDemo01 extends Thread {
	@Override
	public void run() {
		for (int i = 0; i < 30; i++) {
			System.out.println("run: " + i);
		}
	}
}
