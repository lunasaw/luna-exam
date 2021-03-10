package com.luna.practice.ls.daily;

import java.io.*;

/**
 * @author Luna@win10
 * @date 2020/6/8 19:00
 */
public class Test_06_08 {

    /**
     * 最长公共子序列
     * 
     * @param str1
     * @param str2
     * @return
     */
    public static String lcse(String str1, String str2) {
        if (str1 == null || str1 == null || str1.equals("") || str2.equals(""))
            return "字符串不合格";
        char[] ch1 = str1.toCharArray();
        char[] ch2 = str2.toCharArray();
        int[][] dp = getdp(ch1, ch2);
        int m = ch1.length - 1;
        int n = ch2.length - 1;
        char[] res = new char[dp[m][n]];
        int index = res.length - 1;
        while (index >= 0) {
            if (n > 0 && dp[m][n] == dp[m][n - 1]) {
                n--;
            } else if (m > 0 && dp[m][n] == dp[m - 1][n]) {
                m--;
            } else {
                res[index--] = ch1[m];
                m--;
                n--;
            }
        }
        return String.valueOf(res);
    }

    public static int[][] getdp(char[] ch1, char[] ch2) {
        int[][] dp = new int[ch1.length][ch2.length];

        // 给dp矩阵的第一行赋值
        int m = Integer.MAX_VALUE;
        for (int i = 0; i < ch2.length; i++) {
            dp[0][i] = 0;
            if (ch1[0] == ch2[i]) {
                dp[0][i] = 1;
                m = i;
            }
            if (i > m) {
                dp[0][i] = 1;
            }
        }
        // 给dp矩阵的第一列赋值
        m = Integer.MAX_VALUE;
        for (int i = 0; i < ch2.length; i++) {
            dp[i][0] = 0;
            if (ch2[0] == ch1[i]) {
                dp[i][0] = 1;
                m = i;
            }
            if (i > m) {
                dp[i][0] = 1;
            }
        }
        // 给dp矩阵其他地方赋值
        for (int i = 1; i < ch1.length; i++) {
            for (int j = 1; j < ch2.length; j++) {
                dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                if (ch1[i] == ch2[j]) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                }
            }
        }
        return dp;
    }

    /**
     * 最长公共字符串
     * 
     * @param str1
     * @param str2
     * @return
     */
    public static String lcse1(String str1, String str2) {
        if (str1.length() < str2.length()) {
            String tmmp = str2;
            str2 = str1;
            str1 = tmmp;
        }

        int c[][] = new int[str1.length() + 1][str2.length() + 1];
        int result = 0;
        /**
         * 初始化矩阵 第一行和第一列全为0
         */
        for (int i = 0; i <= str1.length(); i++) {
            c[i][0] = 0;
        }
        for (int j = 0; j <= str2.length(); j++) {
            c[0][j] = 0;
        }

        for (int i = 1; i <= str1.length(); i++) {
            for (int j = 1; j <= str2.length(); j++) {
                if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
                    c[i][j] = c[i - 1][j - 1] + 1;
                    result = Math.max(c[i][j], result);
                } else {
                    c[i][j] = 0;
                }
            }
        }

        int col = 0;
        for (int i = 1; i <= str1.length(); i++) {
            for (int j = 1; j <= str2.length(); j++) {
                if (result <= c[i][j]) {
                    col = j;
                }
            }
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = result; i > 0; i--) {
            stringBuilder.append(str2.charAt(col - i));
        }
        return stringBuilder.toString();
    }

    /**
     * 文件读取
     * 
     * @param fileName
     * @return
     */
    public static String readFileContent(String fileName) {
        File file = new File(fileName);
        BufferedReader reader = null;
        StringBuffer sbf = new StringBuffer();
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempStr;
            while ((tempStr = reader.readLine()) != null) {
                sbf.append(tempStr);
            }
            reader.close();
            return sbf.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return sbf.toString();
    }

    /**
     * 测试main方法
     * 
     * @param args
     */
    public static void main(String[] args) {
        String s = readFileContent("D:\\books.user-improve\\learningDocuments\\算法分析与设计\\constant.txt");
        System.out.println(s);
//        s = "ABCBDAB";
        String s1 = readFileContent("D:\\books.user-improve\\learningDocuments\\算法分析与设计\\compare.txt");
        System.out.println(s1);
//        s1 = "BDCABA";
        System.out.println("\t\n最长公共序列-------------------");
        System.out.printf(lcse(s, s1));
        System.out.println("\t\n最长公共子串-------------------");
        System.out.printf(lcse1(s, s1));
    }
}