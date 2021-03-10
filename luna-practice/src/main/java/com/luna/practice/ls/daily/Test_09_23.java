package com.luna.practice.ls.daily;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * @Package: com.luna.practice.ls
 * @ClassName: Test_09_23
 * @Author: luna
 * @CreateTime: 2020/9/23 20:46
 * @Description:
 */
public class Test_09_23 {
    public Map<String, Boolean> map = new HashMap<String, Boolean>();

    /**
     * 递归普通全排列
     *
     * @param list
     * @param index
     */
    public void perm(char[] list, int index) {
        if (index == list.length) {
            System.out.println(String.valueOf(list));
        }
        for (int i = index; i < list.length; i++) {
            swap(list, index, i);
            perm(list, index + 1);
            swap(list, index, i);
        }
    }

    /**
     * 递归去重全排列
     *
     * @param list
     * @param index
     */
    public void permdropmove(char[] list, int index) {
        if (map.get(String.valueOf(list)) == null) {
            if (index == list.length) {
                System.out.println(String.valueOf(list));
                map.put(String.valueOf(list), true);
            }
        } else {
            return;
        }
        for (int i = index; i < list.length; i++) {
            swap(list, index, i);
            perm(list, index + 1);
            swap(list, index, i);
        }
    }

    /**
     * 递归交换
     *
     * @param list
     * @param start
     * @param end
     */
    public static void swap(char[] list, int start, int end) {
        char temp = list[start];
        list[start] = list[end];
        list[end] = temp;
    }

    /**
     * 回溯法
     *
     * @param array
     * @param stack
     */
    public void permforrecall(char[] array, Stack<Character> stack) {
        if (array.length <= 0) {
            System.out.println(stack);
        } else {
            for (int i = 0; i < array.length; i++) {
                char[] tempArray = new char[array.length - 1];
                System.arraycopy(array, 0, tempArray, 0, i);
                System.arraycopy(array, i + 1, tempArray, i, array.length - i - 1);
                stack.push(array[i]);
                permforrecall(tempArray, stack);
                stack.pop();
            }
        }
    }

    public static void main(String[] args) {

        char[] list = {'1', '2', '3', '4'};
        long start1 = System.currentTimeMillis();
        long time;
        try {
            new Test_09_23().permdropmove(list, 0);
        } finally {
            System.out.println("cost for recursion:" + (System.currentTimeMillis() - start1) + "ms");
            time = System.currentTimeMillis() - start1;
        }
        long start2 = System.currentTimeMillis();
        try {
            new Test_09_23().permforrecall(list, new Stack<>());
        } finally {
            System.out.println("cost for permdropmove:" + time + "ms");
            System.out.println("cost for permforrecall:" + (System.currentTimeMillis() - start2) + "ms");
        }

    }

}
