package com.luna.practice.gw.practice;

import java.util.Scanner;

/**
 * @author Luna@win10
 * @date 2020/4/27 21:41
 */
public class Test_4_27 {

	public static void main(String[] arg) {
		int n, m;
		int[] num = new int[7];
		int[] count = new int[7];
		Scanner input = new Scanner(System.in);
		n = input.nextInt();
		for (int l = 0; l < n; l++) {
			m = input.nextInt();
			for (int i = 0; i < m; i++) {
				num[i] = input.nextInt();
				//元素值
				count[i] = 1;
			}
			if (m == 1) {
				System.out.println(num[0] + " 1");
				continue;
			}
			for (int i = 0; i < m - 1; i++) {
				//count数组用来存放该元素出现的次数
				for (int j = i + 1; j < m; j++) {
					if (num[i] == num[j])
						count[i] += 1;
				}
			}
			int max = count[0];
			int flag = 0;
			for (int i = 1; i < m; i++) {
				//选择出现次数最大的元素
				if (count[i] > max) {
					max = count[i];
					flag = i;
				}
			}
			System.out.println("众数：" + num[flag]);
			//输出众数和重数
			System.out.println("重数：" + count[flag]);
		}
	}


}
