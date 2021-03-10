package com.luna.self.recursion;

/**
 * @author luna@mac
 * @className RecursionTest.java
 * @description 递归产生独立空间的栈 空间的数据（局部变量）独立
 * @createTime 2021年02月20日 14:00:00
 */
public class RecursionTest {

    /**
     * 阶乘
     *
     * @param n
     * @return
     */
    public static int factorial(int n) {
        if (n == 1) {
            return n;
        } else {
            return factorial(n - 1) * n;
        }
    }

    public static void main(String[] args) {
        System.out.println(factorial(3));
    }

}
