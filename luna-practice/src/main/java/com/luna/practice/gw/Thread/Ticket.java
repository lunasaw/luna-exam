package com.luna.practice.gw.Thread;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Luna@win10
 * @date 2020/5/12 18:31
 */
public class Ticket implements Runnable {

	private int ticket = 100;

	private Object object = new Object();
	//�Ƿ�ƽ������Դ
	private Lock lock = new ReentrantLock(true);

	/**
	 * һ:ͬ�������
	 * 1. ����һ��������
	 * 2. synchronized(obj) ���������
	 * ��:
	 */
	@Override
	public void run() {
		while (true) {
			lock.lock();
			try {
				if (ticket > 0) {
					//��Ʊ ˯��100����
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println("��ǰ����Ʊ:" + ticket-- + "�����߳�:" + Thread.currentThread().getName());
					//Ʊ����һ
				}
			} finally {
				lock.unlock();
			}

//			synchronized (object){
//				if (ticket > 0) {
//					//��Ʊ ˯��100����
//					try {
//						Thread.sleep(100);
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}
//					System.out.println("��ǰ����Ʊ:" + ticket-- + "�����߳�:" + Thread.currentThread().getName());
//					//Ʊ����һ
//				}
//			}
//			saleTicket();
		}
	}

	/**
	 * ʹ��ͬ������ ����synchronized �ؼ���
	 * static���� Ĭ��Ϊ  synchronized(��ǰ�����.class)
	 * ��ͨ���� Ĭ��Ϊ synchronized(new ʵ������)
	 */
	private synchronized void saleTicket() {
		if (ticket > 0) {
			//��Ʊ ˯��100����
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("��ǰ����Ʊ:" + ticket-- + "�����߳�:" + Thread.currentThread().getName());
			//Ʊ����һ
		}
	}
}
class TicketSell {

	public static void main(String[] args) {
		//1.������ӰƱ����
		Ticket ticket = new Ticket();
		//2.����Thread �߳�
		Thread thread = new Thread(ticket, "����1");
		Thread thread2 = new Thread(ticket, "����2");
		Thread thread3 = new Thread(ticket, "����3");
		//3.ִ������
		thread.start();
		thread2.start();
		thread3.start();
	}

}
