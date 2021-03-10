package com.luna.practice.lyx.basic;

import java.util.*;

/**
 * @author luna@mac
 * @className ResolveChar.java
 * @description TODO
 * @createTime 2021年03月04日 10:22:00
 */
public class ResolveChar {

    public static void statTimes(String param) {
        if (param == null) {
            return;
        }
        // hashset保存不重复的值 因此
        HashSet<Character> hSet = new HashSet<Character>();
        char[] cs = param.toCharArray();
        for (char c : cs) {
            hSet.add(c);
        }
        ArrayList<Character> list = new ArrayList<Character>(hSet);
        // 有多少种字符
        int n = hSet.size();
        // 保存每种字符的出现次数
        int[] times = new int[n];

        for (char c : cs) {
            times[list.indexOf(c)]++;
        }
        for (int i = 0; i < n; i++) {
            System.out.println("字符" + list.get(i) + "=" + times[i] + "次");
            // 打印结果
        }
    }

    public static void main(String[] args) {
        statTimes("cccaaaabbee");
    }

}
