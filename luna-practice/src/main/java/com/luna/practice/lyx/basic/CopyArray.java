package com.luna.practice.lyx.basic;

import java.util.Arrays;

/**
 * @author luna@mac
 * @className CopyArray.java
 * @description TODO
 * @createTime 2021年03月01日 17:37:00
 */
public class CopyArray {

    public static void main(String[] args) {
        System.out.println(Arrays.toString(copyArray(new int[] {1, 3, 4, 5, 6,})));
    }

    public static int[] copyArray(int[] arr) {
        int[] ints = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            ints[i] = arr[i];
        }
        return ints;
    }
}
