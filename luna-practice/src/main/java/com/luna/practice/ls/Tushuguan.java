package com.luna.practice.ls;

import java.util.Scanner;


public class Tushuguan {

	Scanner a = new Scanner(System.in);
	String[] number = new String[10];
	String[] name = new String[10];
	String[] borntime = new String[10];
	String[] bornplace = new String[10];
	int[] isborrowed = new int[5];
	int[] borrowtime = new int[5];
	int[] borrowlong = new int[5];
	int[] borrowcount = new int[5];

	public String[] getNumber() {
		return number;
	}

	public void setNumber(String[] number) {
		this.number = number;
	}

	public String[] getName() {
		return name;
	}

	public void setName(String[] name) {
		this.name = name;
	}

	public String[] getBorntime() {
		return borntime;
	}

	public void setBorntime(String[] borntime) {
		this.borntime = borntime;
	}

	public String[] getBornplace() {
		return bornplace;
	}

	public void setBornplace(String[] bornplace) {
		this.bornplace = bornplace;
	}

	public int[] getIsborrowed() {
		return isborrowed;
	}

	public void setIsborrowed(int[] isborrowed) {
		this.isborrowed = isborrowed;
	}

	public int[] getBorrowtime() {
		return borrowtime;
	}

	public void setBorrowtime(int[] borrowtime) {
		this.borrowtime = borrowtime;
	}

	public int[] getBorrowlong() {
		return borrowlong;
	}

	public void setBorrowlong(int[] borrowlong) {
		this.borrowlong = borrowlong;
	}

	public int[] getBorrowcount() {
		return borrowcount;
	}

	public void setBorrowcount(int[] borrowcount) {
		this.borrowcount = borrowcount;
	}

	public Tushuguan(String[] number, String[] name, String[] bornplace, int[] isborrowed, int[] borrowtime) {
		this.number = number;
		this.name = name;
		this.bornplace = bornplace;
		this.isborrowed = isborrowed;
		this.borrowtime = borrowtime;
	}

	public static void chaxun(String[] name, int[] isborrowed, int[] borrowcount, int[] borrowlong) {


		System.out.println("图书列表");
		System.out.println("图书序号\t图书名称\t图书状态\t借阅时间\t借阅次数");
		//遍历所有图书的信息来显示
		for (int i = 0; i <= 4; i++) {
			if (name[i] != null) {
				String isborrowed1 = (isborrowed[i] == 0 ? "可借阅" : "已借出");
				String date = (isborrowed[i] == 0 ? " " : borrowlong[i] + "日");
				String borrowcount1 = borrowcount[i] + "次";
				System.out.println((i + 1) + "\t" + name[i] + "\t" + isborrowed1 + "\t" + date + "\t" + borrowcount1);
			} else {
				//遇到第一个为null的空数组元素后面亦为空
				break;
			}
		}
	}

	public static void xinzeng(String[] name) {
		@SuppressWarnings("resource")
		Scanner a = new Scanner(System.in);


		for (int i = 0; i < name.length; i++) {
			if (name[i] != null) {

			} else {
				name[i] = a.next();
				System.out.print(name[i]);
				break;
			}
		}
	}

	public static void jieyue(String[] name, int[] isborrowed, int[] borrowtime, int[] borrowlong, int[] borrowcount) {
		@SuppressWarnings("resource")
		Scanner a = new Scanner(System.in);


		System.out.println("请借阅您想要的书籍");

		String s = a.next();
		for (int i = 0; i < name.length; i++) {
			if (name[i] == null) {
				System.out.print("没有你想要的书");
				continue;
			} else if (name[i].equals(s) && isborrowed[i] == 0) {
				System.out.print("请输入借阅时间");
				borrowtime[i] = a.nextInt();
				while (borrowtime[i] < 1 || borrowlong[i] > 31) {
					System.out.print("输入不正确，请重新输入");
					borrowlong[i] = a.nextInt();

				}
				System.out.print("借书成功");
				isborrowed[i] = 1;
				borrowcount[i]++;
				break;
			}
		}
	}

	public static void guihuan(String[] name, int[] isborrowed, int[] borrowlong) {
		@SuppressWarnings("resource")
		Scanner a = new Scanner(System.in);


		System.out.println("请归还图书");

		String guihuan = a.next();
		for (int i = 0; i < name.length; i++) {
			if (name[i].equals(guihuan) && isborrowed[i] == 1) {
				isborrowed[i] = 0;
				borrowlong[i] = 0;
				System.out.print("还书成功");
				break;
			} else if (name[i] == null) {
				System.out.print("非馆藏书籍，请重新输入");
			}
		}
	}

	public static void shanchu(String[] name, int[] borrowlong, int[] borrowcount) {
		@SuppressWarnings("resource")
		Scanner a = new Scanner(System.in);


		int index = -1;
		System.out.print("请删除不需要的老图书");

		String delete = a.next();
		for (int i = 0; i < name.length - 1; i++) {
			if (name[i].equals(delete)) {
				index = i;
				break;
			}
		}
		if (index != -1) {
			for (int i = index; i < name.length; i++) {
				if (i != name.length - 1) {
					name[i] = name[i + 1];
					borrowlong[i] = borrowlong[i + 1];
					borrowcount[i] = borrowcount[i + 1];
				}
			}
			System.out.print("删除成功");
		}
	}


}


