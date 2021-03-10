package com.luna.practice.ls.daily;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * @Package: lsJava
 * @ClassName: Test_6_15
 * @Author: luna
 * @CreateTime: 2020/6/15 19:51
 * @Description:
 */
public class Test_6_15 {

    static class LongestCommonSubsequence {
        private String      X;
        private String      Y;
        private int[][]     table;                       // 动态规划表
        private Set<String> set = new HashSet<String>(); // 最长子序列集合

        public LongestCommonSubsequence(String X, String Y) {
            this.X = X;
            this.Y = Y;
        }

        /**
         * 功能：构造矩阵，并返回最长子序列的长度
         */
        private int lcs() {
            int m = X.length();
            int n = Y.length();
            table = new int[m + 1][n + 1]; // 表的大小为(m+1)*(n+1)
            for (int i = 0; i < m + 1; ++i) {
                for (int j = 0; j < n + 1; ++j) {
                    // 第一行和第一列置0
                    if (i == 0 || j == 0)
                        table[i][j] = 0;
                    else if (X.charAt(i - 1) == Y.charAt(j - 1))
                        table[i][j] = table[i - 1][j - 1] + 1;
                    else
                        table[i][j] = Math.max(table[i - 1][j], table[i][j - 1]);
                }
            }
            return table[m][n];
        }

        /**
         * 功能：回溯，求出所有的最长公共子序列，并放入最长子序列集合set中
         */
        private void traceBack(int i, int j, String lcs_str) {
            while (i > 0 && j > 0) {
                if (X.charAt(i - 1) == Y.charAt(j - 1)) {
                    lcs_str += X.charAt(i - 1);
                    --i;
                    --j;
                } else {
                    if (table[i - 1][j] > table[i][j - 1])
                        --i;
                    else if (table[i - 1][j] < table[i][j - 1])
                        --j;
                    else { // 相等的情况
                        traceBack(i - 1, j, lcs_str);
                        traceBack(i, j - 1, lcs_str);
                        return;
                    }
                }
            }
            set.add(new StringBuffer(lcs_str).reverse().toString());
        }

        public void printLCS() {
            int m = X.length();
            int n = Y.length();
            String str = "";
            traceBack(m, n, str);
            for (String s : set) {
                System.out.println("\t" + s);
            }
        }
    }

    static class LongestCommonSubstring {

        private String      X;
        private String      Y;
        private int[][]     table;                       // 动态规划表
        private Set<String> set = new HashSet<String>(); // 最长字串的集合

        public LongestCommonSubstring(String X, String Y) {
            this.X = X;
            this.Y = Y;
        }

        /**
         * 功能：构造表，并返回最长子串的长度
         */
        private int lcs() {
            int m = X.length();
            int n = Y.length();
            int biggest = 0;
            table = new int[m + 1][n + 1]; // 表的大小为(m+1)*(n+1)
            for (int i = 0; i < m + 1; ++i) {
                for (int j = 0; j < n + 1; ++j) {
                    // 第一行和第一列置0
                    if (i == 0 || j == 0)
                        table[i][j] = 0;
                    else if (X.charAt(i - 1) == Y.charAt(j - 1)) {
                        table[i][j] = table[i - 1][j - 1] + 1;
                        if (table[i][j] > biggest) // 增加了一个最大值
                            biggest = table[i][j];
                    } else
                        table[i][j] = 0;
                }
            }
            return biggest;
        }

        /**
         * 功能：回溯，求出所有的最长公共子串，并放入最长字串的集合set中
         */
        private void traceBack(int m, int n, int lcs_len) {
            String strOflcs = "";
            for (int i = 1; i < m + 1; i++) {
                for (int j = 1; j < n + 1; j++) {
                    // 查到等于lcs_len的值，取字符
                    if (table[i][j] == lcs_len) {
                        int ii = i, jj = j;
                        while (table[ii][jj] >= 1) {
                            strOflcs = strOflcs + X.charAt(ii - 1);
                            ii--;
                            jj--;
                        }
                        String str = new StringBuffer(strOflcs).reverse().toString();
                        set.add(str);
                        strOflcs = "";
                    }
                }
            }
        }

        public void printLCS() {
            int m = X.length();
            int n = Y.length();
            traceBack(m, n, lcs());
            for (String s : set) {
                System.out.println("\t" + s);
            }
        }
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
        System.out.println("文件A的内容是：");
        System.out.println("\t" + s);
        String s1 = readFileContent("D:\\books.user-improve\\learningDocuments\\算法分析与设计\\compare.txt");
        System.out.println("文件B的内容是：");
        System.out.println("\t" + s1);
        LongestCommonSubsequence l1 = new LongestCommonSubsequence(s, s1);
        System.out.println("最长子序列的长度是：");
        System.out.println("\t" + l1.lcs());
        LongestCommonSubstring l2 = new LongestCommonSubstring(s, s1);
        System.out.println("最长子串的长度是：");
        System.out.println("\t" + l2.lcs());
        System.out.println("最长子串分别是：");
        l2.printLCS();
        System.out.println("最长子序列分别是：");
        System.out.printf(lcse(s, s1));
    }

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
        for (int i = 1; i < ch1.length; i++)
            for (int j = 1; j < ch2.length; j++) {
                dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                if (ch1[i] == ch2[j])
                    dp[i][j] = dp[i - 1][j - 1] + 1;
            }
        return dp;
    }
}
