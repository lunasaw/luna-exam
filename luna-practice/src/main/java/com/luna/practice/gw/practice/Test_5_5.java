package com.luna.practice.gw.practice;

/**
 * @author Luna@win10
 * @date 2020/5/5 10:24
 */
public class Test_5_5 {

	static class MyThread extends Thread{

		/** 线程名*/
		private String str;
		private int delay;

		public MyThread(String str, int delay) {
			this.str = str;
			this.delay = delay;
		}

		@Override
		public void run() {
			try {
				Thread.sleep(this.delay);
				System.out.println(this.str);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			super.run();
		}
	}

	static class A implements Runnable{

		/** 线程名*/
		private String str;
		private int delay;

		public A(String str, int delay) {
			this.str = str;
			this.delay = delay;
		}

		@Override
		public void run() {
			try {
				Thread.sleep(this.delay);
				System.out.println(this.str);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		MyThread t1=new MyThread("线程A",1000);
		MyThread t2=new MyThread("线程B",2000);
		MyThread t3=new MyThread("线程C",3000);

		t1.start();
		t2.start();
		t3.start();


		A a1=new A("A类对象1",1000);
		A a2=new A("A类对象2",2000);

		a1.run();
		a2.run();
		System.out.println("程序2线程运行完毕");
	}


}
