package com.luna.practice.ls.daily;

import java.util.Scanner;

/**
 * @author Luna@win10
 * @date 2020/4/24 15:43
 */
public class Test_04_24_1 {


	/**
	 * 1.先找到这一组的中位数,假设它就是众数，计算它的个数
	 * 2.根据中位数的位置划分左边和右边，分成n个子问题，个数小于中位数个数的不必计算，中位数就是众数
	 * 3.每次分割后继续比较
	 */

	//重数
	private static int multipleNumbers = 0;
	//众数
	private static String mode;
	//中位数
	private static int medianNumber;

	private static String[] element = {"1", "2", "2", "2", "3", "3", "3", "5", "5"};

	private static void start(int start, int end) {
		//求中位数下标
		int mid = (start + end) / 2;
		for (int i = start; i < end; i++) {
			if (element[i].equals(element[mid])) {
				//计算中位数个数
				medianNumber++;
				if (medianNumber > multipleNumbers) {
					multipleNumbers = medianNumber;
					mode = element[mid];
				}
			}
		}
		if (mid > medianNumber) {
			medianNumber = 0;
			start(0, mid);
		}
		if (end - mid - 1 > medianNumber) {
			medianNumber = 0;
			start(mid + 1, end);
		}

	}

	public static void main(String[] args) {
		Scanner scanner=new Scanner(System.in);
		String next = scanner.nextLine();
		String[] split = next.split(" ");
		element=split;
		start(0, split.length);
		System.out.println("重数: " + multipleNumbers + " 众数：" + mode);
	}


}
