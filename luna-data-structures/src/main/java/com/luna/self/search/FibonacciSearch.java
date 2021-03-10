package com.luna.self.search;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * @author luna@mac
 * @className FibonacciSearch.java
 * @description TODO 斐波那契查找 待查找数组需要有序
 * @createTime 2021年03月01日 14:00:00
 */
public class FibonacciSearch {

    public static int[] fib(int n) {
        int[] fib = new int[n];
        fib[0] = fib[1] = 1;
        for (int i = 2; i < n; i++) {
            fib[i] = fib[i - 1] + fib[i - 2];
        }
        return fib;
    }

    /**
     * 非递归斐波那契查找
     * 
     * @param arr
     * @param key
     */
    public static int fibSearch(int[] arr, int n, int key) {
        int low = 0;
        int high = arr.length - 1;
        // 分割数值下标
        int k = 0;
        // 存放mid值
        int mid = 0;
        int[] fib = fib(n);
        // 获取分割数下标
        while (high > fib[k] - 1) {
            k++;
        }
        // 因为f[k]可能大于a的长度 因此需要使用Array类 并指向arr[],不足的部分使用0填充
        int[] temp = Arrays.copyOf(arr, fib[k]);
        // 实际需要使用arr的最后一个数填充
        for (int i = high + 1; i < temp.length; i++) {
            temp[i] = arr[high];
        }
        // 循环处理找到需要数
        while (low <= high) {
            mid = low + fib[k - 1] - 1;
            if (key < temp[mid]) {
                // 继续向前部查找
                high = mid - 1;
                k--;
            } else if (key > temp[mid]) {
                low = mid + 1;
                k -= 2;
            } else {
                if (mid <= high) {
                    return mid;
                } else {
                    return high;
                }
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] ints = {1, 3, 5, 6, 7, 8, 9, 11, 17};
        System.out.println(fibSearch(ints, 20, 11));
    }
}
