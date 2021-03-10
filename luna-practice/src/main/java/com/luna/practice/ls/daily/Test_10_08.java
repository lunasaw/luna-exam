package com.luna.practice.ls.daily;

import java.util.Scanner;

/**
 * @Package: com.luna.practice.ls
 * @ClassName: Test_10_08
 * @Author: luna
 * @CreateTime: 2020/10/8 21:43
 * @Description:
 */
public class Test_10_08 {
    static int[] c = new int[100];     //将划分到的每个数字存储到c数组里面
    static int num = 0;                //存储待划分的数字

    public static void dfs(int start, int n) {
        if (n == 0) {
            System.out.print(num + "=");
            for (int i = 1; i <= start - 2; i++) {
                System.out.print(c[i] + "+");
            }
            System.out.println(c[start - 1]);
            return;
        }
        for (int i = 1; i <= n; i++) {
            if (i >= c[start - 1]) {
                c[start] = i;
                dfs(start + 1, n - i);
            }
        }
    }

    public static void main(String args[]) {
        Scanner cin = new Scanner(System.in);
        num = cin.nextInt();
        dfs(1, num);
    }
}
