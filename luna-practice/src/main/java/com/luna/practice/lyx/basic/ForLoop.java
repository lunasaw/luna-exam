package com.luna.practice.lyx.basic;

/**
 * @author luna@mac
 * @className ForLoop.java
 * @description TODO
 * @createTime 2021年03月01日 14:49:00
 */
public class ForLoop {
    public static void main(String[] args) {
        System.out.println(getCount(1, 1000));
    }

    public static int getCount(int beginNum, int endNum) {
        int result = 0;
        for (int i = beginNum; i <= endNum; i++) {
            // 能被3整除
            if (i % 3 != 0) {
                result += i;
            }
        }
        return result;
    }

}
