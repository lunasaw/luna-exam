package com.luna.self.search;

import java.util.ArrayList;
import java.util.List;

/**
 * @author luna@mac
 * @className BinarySearch.java
 * @description TODO 差值查找 数组需为有序数组
 * @createTime 2021年02月28日 18:18:00
 */
public class InsertValueSearch {

    /**
     * 二分查找算法
     *
     * @param arr 数组
     * @param left 左边的索引
     * @param right 右边的索引
     * @param findVal 要查找的值
     * @return 如果找到就返回下标，如果没有找到，就返回 -1
     */
    public static int insertValueSearch(int[] arr, int left, int right, int findVal) {
        // 当 left > right 时，说明递归整个数组，但是没有找到
        if (left > right || findVal < arr[0] || findVal > arr[arr.length - 1]) {
            return -1;
        }
        int mid = left + (right - left) * (findVal - arr[left]) / (arr[right] - arr[left]);
        int midVal = arr[mid];
        // 向 右递归
        if (findVal > midVal) {
            return insertValueSearch(arr, mid + 1, right, findVal);
            // 向左递归
        } else if (findVal < midVal) {
            return insertValueSearch(arr, left, mid - 1, findVal);
        } else {
            return mid;
        }
    }

    public static List<Integer> insertValueSearchTwo(int[] arr, int left, int right, int findVal) {
        // 当 left > right 时，说明递归整个数组，但是没有找到
        if (left > right || findVal < arr[0] || findVal > arr[arr.length - 1]) {
            return new ArrayList<>();
        }
        int mid = left + (right - left) * (findVal - arr[left]) / (arr[right] - arr[left]);
        int midVal = arr[mid];
        // 向 右递归
        if (findVal > midVal) {
            return insertValueSearchTwo(arr, mid + 1, right, findVal);
            // 向左递归
        } else if (findVal < midVal) {
            return insertValueSearchTwo(arr, left, mid - 1, findVal);
        } else {
            // * 思路分析
            // * 1. 在找到mid 索引值，不要马上返回
            // * 2. 向mid 索引值的左边扫描，将所有满足 1000， 的元素的下标，加入到集合ArrayList
            // * 3. 向mid 索引值的右边扫描，将所有满足 1000， 的元素的下标，加入到集合ArrayList
            // * 4. 将Arraylist返回
            List<Integer> resIndexlist = new ArrayList<Integer>();
            // 向mid 索引值的左边扫描，将所有满足 1000， 的元素的下标，加入到集合ArrayList
            int temp = mid - 1;
            while (true) {
                if (temp < 0 || arr[temp] != findVal) {
                    // 退出
                    break;
                }
                // 否则，就temp 放入到 resIndexlist
                resIndexlist.add(temp);
                // temp左移
                temp -= 1;
            }
            resIndexlist.add(mid);
            // 向mid 索引值的右边扫描，将所有满足 1000， 的元素的下标，加入到集合ArrayList
            temp = mid + 1;
            while (true) {
                if (temp > arr.length - 1 || arr[temp] != findVal) {
                    // 退出
                    break;
                }
                // 否则，就temp 放入到 resIndexlist
                resIndexlist.add(temp);
                // temp右移
                temp += 1;
            }

            return resIndexlist;
        }

    }

    public static void main(String[] args) {
        int[] ints = new int[] {1, 3, 5, 8, 12, 16};
        int i = insertValueSearch(ints, 0, ints.length - 1, 3);
        System.out.println(ints[i]);
    }
}
