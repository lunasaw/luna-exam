package com.luna.practice.ls.daily;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @Package: com.luna.practice.ls
 * @ClassName: Test_09_09
 * @Author: luna
 * @CreateTime: 2020/9/11 22:33
 * @Description:
 */
public class Test_09_11 {

    /**
     * 当n很大的时候可能会出现数字溢出，所以我们需要用结果对1000000007求余，但实际上可能还没有执行到最后一步就已经溢出了，所以我们需要对每一步的计算都要对1000000007求余
     * 取模运算有这样一个性质：(a+b)%c = ((a%c)+(b%c))%c
     * 1.1000000007是一个质数
     * 2.int32位的最大值为2147483647，所以对于int32位来说1000000007足够大
     * 3.int64位的最大值为2^63-1，对于1000000007来说它的平方不会在int64中溢出
     * 所以在大数相乘的时候，因为(a∗b)%c=((a%c)∗(b%c))%c，所以相乘时两边都对1000000007取模，再保存在int64里面不会溢出
     */
    static int constant = 1000000007;

    /**
     * 递归实现
     *
     * @param n
     * @return
     */
    public static int fib1(int n) {
        //斐波那契数列递归的时候会造成大量的重复计算
        //使用一个map把计算过的值存起来，每次计算的时候先看map中有没有，如果有就表示计算过，直接从map中取，如果没有就先计算，计算完之后再把结果存到map中。
        return fib(n, new HashMap());
    }

    public static int fib(int n, Map<Integer, Integer> map) {
        if (n < 2) {
            return n;
        }
        if (map.containsKey(n)) {
            return map.get(n);
        }
        int first = fib(n - 1, map) % constant;
        map.put(n - 1, first);
        int second = fib(n - 2, map) % constant;
        map.put(n - 2, second);
        int res = (first + second) % constant;
        map.put(n, res);
        return res;
    }


    /**
     * 非递归实现
     *
     * @param n
     * @return
     */
    public static int fib2(int n) {
        int first = 0;
        int second = 1;
        while (n-- > 0) {
            int temp = first + second;
            first = second % constant;
            second = temp % constant;
        }
        return first;
    }


    /**
     * 递归实现
     *
     * @param f
     * @return
     */
    public static BigDecimal fibBig(int f) {
        return fibBig(f, new HashMap());
    }

    public static BigDecimal fibBig(int n, Map<Integer, BigDecimal> map) {

        if (n <= 2) {
            return new BigDecimal(1);
        }
        if (map.containsKey(n)) {
            return map.get(n);
        }
        BigDecimal first = fibBig(n - 1, map);
        map.put(n - 1, first);
        BigDecimal second = fibBig(n - 2, map);
        map.put(n - 2, second);
        map.put(n, first.add(second));
        return first.add(second);
    }

    /**
     * 迭代实现
     *
     * @param f
     * @return
     */
    public static BigDecimal fibBigLoops(int f) {
        BigDecimal first = new BigDecimal(0);
        BigDecimal second = new BigDecimal(1);
        for (int i = 1; i <= f; i++) {
            second = first.add(second);
            first = second.subtract(first);
        }
        return first;
    }


    public static void main(String[] args) {
        System.out.println("===========递归实现===========");
        System.out.println(fib1(2));
        System.out.println(fib1(8));
        System.out.println(fib1(16));
        System.out.println(fib1(32));
        System.out.println(fib1(64));
        System.out.println(fib1(512));
        System.out.println(fib1(1024));
        System.out.println("=========非递归实现==========");
        System.out.println(fib2(2));
        System.out.println(fib2(8));
        System.out.println(fib2(16));
        System.out.println(fib2(32));
        System.out.println(fib2(64));
        System.out.println(fib2(512));
        System.out.println(fib2(1024));
        System.out.println(fib2(2048));

        System.out.println("===========递归实现===========");
        System.out.println(fibBig(2));
        System.out.println(fibBig(8));
        System.out.println(fibBig(16));
        System.out.println(fibBig(32));
        System.out.println(fibBig(64));
        System.out.println(fibBig(512));
        System.out.println(fibBig(1024));
        System.out.println("=========非递归实现==========");
        System.out.println(fibBigLoops(2));
        System.out.println(fibBigLoops(8));
        System.out.println(fibBigLoops(16));
        System.out.println(fibBigLoops(32));
        System.out.println(fibBigLoops(64));
        System.out.println(fibBigLoops(512));
        System.out.println(fibBigLoops(1024));
        System.out.println(fibBigLoops(2048));
    }
}
