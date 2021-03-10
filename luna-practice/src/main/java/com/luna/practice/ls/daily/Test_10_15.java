package com.luna.practice.ls.daily;

import com.mysql.cj.xdevapi.JsonArray;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @Package: com.luna.practice.ls
 * @ClassName: Test_10_15
 * @Author: luna
 * @CreateTime: 2020/10/15 21:48
 * @Description:
 */
public class Test_10_15 {

    public static char[] WORD = "abcdefghijklmnopqrstuvwxyz".toCharArray();

    static char ch(char c) {
        if (!(c >= 97 && c <= 122)) {
            c += 32;
        }
        return c;
    }

    /**
     * 获取字符串
     *
     * @param path
     * @return
     */
    public static String getWords(String path) {
        try {
            FileInputStream fip = new FileInputStream(path);
            InputStreamReader reader = new InputStreamReader(fip);
            StringBuffer sb = new StringBuffer();
            while (reader.ready()) {
                sb.append((char) reader.read());
            }
            reader.close();
            fip.close();
            return sb.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 打印字母数
     *
     * @param X
     */
    public static void printWords(int[] X) {
        int sum = 0;
        for (int i = 0; i < 26; i++) {
            sum = sum + X[i];
        }
        System.out.println("一共有" + sum + "个字母。");
        System.out.println("各字母频率如下：");
        for (int i = 0; i < 26; i++) {
            if (X[i] != 0) {
                System.out.print(WORD[i] + ":" + X[i]);
                System.out.print("  ");
            }
        }
        System.out.println();
    }

    /**
     * 统计字母数
     *
     * @param words
     * @return
     */
    public static int[] acount(String words) {
        char NUM[] = new char[words.length()];
        char Z[] = new char[26];
        int X[] = new int[26];
        Z = WORD;
        for (int k = 0; k < 26; k++) {
            X[k] = 0;
            for (int i = 0; i < words.length(); i++) {
                NUM[i] = words.charAt(i);
                if (Z[k] == NUM[i] || Z[k] == ch(NUM[i])) {
                    X[k]++;
                }
            }
        }
        for (int i = 0; i < 25; i++) {
            for (int k = 0; k < 25 - i; k++) {
                if (X[k] < X[k + 1]) {
                    int temp2 = X[k];
                    X[k] = X[k + 1];
                    X[k + 1] = temp2;
                    char temp3 = Z[k];
                    Z[k] = Z[k + 1];
                    Z[k + 1] = temp3;
                }
            }
        }
        return X;
    }


    public static void main(String[] args) {
        String words = getWords("luna-practice/src/main/resources/lsdoc/ascll.txt");
        int[] wordNums = acount(words);
        printWords(wordNums);
    }
}
