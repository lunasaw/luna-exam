package com.luna.practice.gw.practice;

/**
 * @version 1.0
 * @Author NL
 * @date 2020/5/12 9:53
 */
public class Test_5_12 {

	public static void main(String[] args) {
		Stack stack = new Stack();
		producer p = new producer(stack);
		customer c = new customer(stack);
		new Thread(p).start();
		new Thread(c).start();
	}
}

class Stack {
	private int top = -1;
	//栈顶指针，也表示当前栈中结点个数
	String[] data = new String[1];

	public synchronized void push(String strCell) {
		while (true) {
			if (top == data.length - 1) {
				System.out.println("堆栈已满，压入失败");
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} else {
				top++;
				data[top] = strCell;
				notifyAll();
				System.out.println("压入成功'" + strCell + "'");
				break;
			}
		}
	}

	public synchronized String pop() {
		while (true) {
			if (top == -1) {
				System.out.println("堆栈已空，弹出失败");
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} else {
				String str = data[top];
				data[top] = null;
				top--;
				notifyAll();
				System.out.println("弹出成功'" + str + "'");
				return str;
			}
		}
	}
}

class producer implements Runnable {
	Stack s;

	public producer(Stack s) {
		this.s = s;
	}

	@Override
	public void run() {
		for (int i = 0; i < 20; i++) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			String a = "" + i;
			s.push(a);
		}
	}
}

class customer implements Runnable {
	Stack s;

	public customer(Stack s) {
		this.s = s;
	}

	@Override
	public void run() {
		for (int i = 0; i < 20; i++) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			s.pop();
		}
	}
}