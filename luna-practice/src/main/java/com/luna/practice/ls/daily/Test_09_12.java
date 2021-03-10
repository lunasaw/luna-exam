package com.luna.practice.ls.daily;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @Package: com.luna.practice.ls
 * @ClassName: Test_09_13
 * @Author: luna
 * @CreateTime: 2020/9/13 14:51
 * @Description:
 */
public class Test_09_12 {

    public static void main(String[] args) {
        System.out.println("用递归实现斐波那契数列  如下：");
        for (int a = 1; a <= 10; a++) {
            System.out.println("=========================");
            System.out.println((int) Math.pow(2, a) + "=>" + fib((int) Math.pow(2, a)));
        }

        System.out.println("用非递归实现斐波那契数列  如下：");
        for (int a = 1; a <= 10; a++) {
            System.out.println("=========================");
            System.out.println((int) Math.pow(2, a) + "=>" + fib3((int) Math.pow(2, a)));
        }
    }

    /**
     * 递归实现
     *
     * @param num
     * @return
     */
    public static BigDecimal fib(int num) {
        return fib(num, new HashMap());
    }

    public static BigDecimal fib(int n, Map<Integer, BigDecimal> map) {

        if (n <= 2) {
            return new BigDecimal(1);
        }
        if (map.containsKey(n)) {
            return map.get(n);
        }
        BigDecimal left = fib(n - 1, map);
        map.put(n - 1, left);
        BigDecimal right = fib(n - 2, map);
        map.put(n - 2, right);
        map.put(n, left.add(right));
        return left.add(right);
    }

    /**
     * 迭代实现
     *
     * @param num
     * @return
     */
    public static BigDecimal fib3(int num) {
        BigDecimal left = new BigDecimal(0);
        BigDecimal right = new BigDecimal(1);
        for (int i = 1; i <= num; i++) {
            right = left.add(right);
            left = right.subtract(left);
        }
        return left;
    }
}
