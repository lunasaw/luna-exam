package com.luna.practice.gw.practice;

import java.io.File;

/**
 * @author Iszychen@win10
 * @date 2020/3/10 10:01
 */
public class Test_3_10_2 {

	public static void main(String[] args) {

		File[] roots = File.listRoots();
		for (int i = 0; i < roots.length; i++)
			System.out.println(roots[i]);
	}
}
