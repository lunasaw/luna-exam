package com.luna.practice.gw.practice;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

public class Test_4_29_2 {

	public static void main(String[] args) {
		System.out.println("请输入数组的个数：");
		Scanner scanner = new Scanner(System.in);
		int n = scanner.nextInt();
		int[] data = new int[n];
		System.out.println("请输入这" + n + "个数：");
		for (int i = 0; i < n; i++) {
			data[i] = scanner.nextInt();
		}
		System.out.println("请输入两个数的和：");
		int sum = scanner.nextInt();
		int[] result = Method(data, sum);
		System.out.println("这两个数字是：");
		System.out.println(result[1] + "和" + result[2]);
		if (result[0] > 2) {
			for (int i = 2; i <= result[0] / 2; i++) {
				System.out.println(result[2 * i - 1] + "和" + result[2 * i]);
			}
		}
	}

	static int[] Method(int[] data, int sum) {
		Map<Integer, Integer> map = new HashMap<>();
		int[] result = new int[11];
		for (int i = 0; i < data.length; i++) {
			if ((map.get(data[i])) == null) {
				map.put(data[i], 1);
			} else {
				int num = map.get(data[i]);
				map.put(data[i], num + 1);
			}
		}
		int[] flag = new int[data.length];
		int len = 0;
		Iterator<Integer> it = map.keySet().iterator();
		while (it.hasNext()) {
			int num = it.next();
			map.put(num, map.get(num) - 1);
			if (null != map.get(sum - num) && map.get(sum - num) > 0) {
				flag[len] = num;
				len++;
			}
			map.put(num, map.get(num) + 1);
		}
		result[0] = len;
		if (len != 0) {
			for (int i = 0, j = 1; i < len / 2; i++, j++) {
				result[2 * j - 1] = flag[i];
				result[2 * j] = flag[len - 1 - i];
			}
			return result;
		}
		result[1] = -1;
		result[2] = -1;
		return result;
	}

}
