package com.luna.practice.gw.practice;

/**
 * @author Luna@win10
 * @date 2020/6/10 15:06
 */
public class Test_6_10 {

    public static void getCommonStrLength(String text1, String text2) {
        int[][] dp = new int[text1.length() + 1][text2.length() + 1];
        for (int i = 0; i <= text1.length(); i++) {
            for (int j = 0; j <= text2.length(); j++) {
                if ((i == 0) || (j == 0))
                    dp[i][j] = 0;
                else if (text1.charAt(i - 1) == text2.charAt(j - 1))
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                else
                    dp[i][j] = 0;
            }
        }
        int max = -1, col = 0;
        for (int i = 1; i <= text1.length(); i++) {
            for (int j = 1; j <= text2.length(); j++) {
                if (max <= dp[i][j]) {
                    max = dp[i][j];
                    col = j;
                }
            }
        }
        System.out.println(col);
        System.out.println(max);
        for (int i = max; i > 0; i--) {
            System.out.print(text2.charAt(col - i));
        }
    }

    public static void getCommonStrLength1(String text1, String text2) {
        int[][] dp = new int[text1.length() + 1][text2.length() + 1];
        for (int i = 0; i <= text1.length(); i++) {
            for (int j = 0; j <= text2.length(); j++) {
                if ((i == 0) || (j == 0))
                    dp[i][j] = 0;
                else if (text1.charAt(i - 1) == text2.charAt(j - 1))
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                else
                    dp[i][j] = 0;
            }
        }
        int max = -1, col = 0;
        for (int i = 1; i <= text1.length(); i++) {
            for (int j = 1; j <= text2.length(); j++) {
                if (max <= dp[i][j]) {
                    max = dp[i][j];
                    col = j;
                }
            }
        }
        for (int i = max; i > 0; i--) {
            System.out.print(text2.charAt(col - i));
        }
    }

    public static void main(String[] args) {
        getCommonStrLength1(
            "求解两个字符串的最长公共子序列或最长公共子串是字符串最常见的操作之一，应用非常广泛。有关的子序列和子串的定义说明和有关算法的基本思路请参考《算法与数据结构》或《算法设计与分析》理论课教材。",
            "最长公共子序列课理论");
    }

}
