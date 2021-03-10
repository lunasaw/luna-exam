package com.luna.practice.gw.Thread;

/**
 * @author Luna@win10
 * @date 2020/5/14 12:17
 */
public class ThreadLoacl {

	//1. 创建银行对象: 存款  取款
	static class Bank{
		ThreadLocal<Integer> local=new ThreadLocal<Integer>(){
			@Override
			protected Integer initialValue(){
				return 0;
			}
		};

		public Integer get(){
			return local.get();
		}

		public void set(Integer integer) {
			local.set(local.get()+integer);
		}
	}

	static class Transfer implements Runnable{
		private Bank bank;

		public Transfer(Bank bank){
			this.bank=bank;
		}

		@Override
		public void run() {
			for (int i = 0; i < 10; i++) {
				bank.set(10);
				System.out.println(Thread.currentThread().getName()+"账户余额"+bank.get());
			}
		}
	}
	//2. 创一个转账对象: 从银行中取钱,转账,保存到账户
	//3. 在main方法中模拟操作

	public static void main(String[] args) {
		Bank bank=new Bank();
		Transfer transfer=new Transfer(bank);
		Thread thread1=new Thread(transfer,"用户一");
		Thread thread2=new Thread(transfer,"用户二");
		thread1.start();
		thread2.start();
	}
}
