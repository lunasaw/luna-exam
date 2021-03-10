package com.luna.practice.gw.practice;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

/**
 * @author Luna@win10
 * @date 2020/4/27 21:41
 */
public class Test_4_29_1 {

	public static void main(String[] args) {
		System.out.println("请输入数组的个数：");
		Scanner scanner = new Scanner(System.in);
		int n = scanner.nextInt();
		int[] data = new int[n];
		System.out.println("请输入这" + n + "个数：");
		for (int i = 0; i < n; i++) {
			data[i] = scanner.nextInt();
		}
		int[] result = Method(data);
		System.out.print("多重集S的众数是：");
		for (int i = 2; i < result[1] + 2; i++) {
			System.out.print(result[i] + ",");
		}
		System.out.println("其重数是" + result[0]);
	}

	static int[] Method(int[] data) {
		Map<Integer, Integer> map = new HashMap<>();
		int[] result = new int[data.length];
		int max = -1;
		for (int i = 0; i < data.length; i++) {
			if ((map.get(data[i])) == null) {
				map.put(data[i], 1);
			} else {
				int num = map.get(data[i]);
				map.put(data[i], num + 1);
			}
		}
		Iterator<Integer> iterator = map.keySet().iterator();
		while (iterator.hasNext()) {
			int num = iterator.next();
			if (map.get(num) > max) {
				max = map.get(num);
			}
		}
		iterator = map.keySet().iterator();
		int i = 2;
		while (iterator.hasNext()) {
			int num = iterator.next();
			if (map.get(num) == max) {
				result[i] = num;
				i++;
			}
		}
		result[0] = max;
		result[1] = i - 2;
		return result;
	}
}
