package com.luna.practice.ls;

import java.util.Arrays;

public class nqueen {

    public static void main(String[] args) {
        //皇后数设置为16
        System.out.println(num(6));
    }

    //计算n皇后有多少种放置位置
    public static Long num(int n) {
        if (n < 1) {
            return Long.valueOf(0);
        }
        //Long res = Long.valueOf(0);
        int[] records = new int[n];
        for (int i = 0; i < records.length; i++) {
            records[i] = -1;
        }
        Long process = process(0, records, n);

        return process;
    }

    public static Long process(int i, int[] record, int n) {
        if (i == n) {
            return Long.valueOf(1);
        }
        Long res = Long.valueOf(0);
        for (int j = 0; j < n; j++) {
            if (isValid(record, i, j)) {
                record[i] = j;
                res += process(i + 1, record, n);
            }
        }
        return res;
    }

    public static boolean isValid(int[] record, int i, int j) {
        for (int k = 0; k < i; k++) {
            if (record[k] == j || (record[k] >= 0 && Math.abs(record[k] - j) == Math.abs(i - k))) {
                return false;
            }
        }
        System.out.println(Arrays.toString(record));
        return true;
    }

}