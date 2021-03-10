package com.luna.self.sort;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.Random;

/**
 * @author luna@mac
 * @className MergeSort.java
 * @description TODO 归并排序 分而治之   二分 然后合并  两个指针往中间移动,比较大小依次放入新的数组中
 * @createTime 2021年02月23日 12:10:00
 */
public class MergeSort {

    private int[] array;

    private LocalTime start;

    private LocalTime end;

    public int[] getArray() {
        return array;
    }

    public void setArray(int[] array) {
        this.array = array;
    }

    public LocalTime getStart() {
        return start;
    }

    public void setStart(LocalTime start) {
        this.start = start;
    }

    public LocalTime getEnd() {
        return end;
    }

    public void setEnd(LocalTime end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return "MergeSort {" +
                "array=" + Arrays.toString(array) +
                ", start=" + start +
                ", end=" + end +
                '}';
    }

    public MergeSort(int n) {
        this.array = new int[n];
        Random random = new Random();
        // 随机初始化数组
        for (int i = 0; i < n; i++) {
            array[i] = random.nextInt(n);
        }
    }

    /**
     * 分+合方法
     *
     * @param arr
     * @param left
     * @param right
     * @param temp
     */
    public static void mergeSortLargeToSmall(int[] arr, int left, int right, int[] temp) {
        if (left < right) {
            //中间索引
            int mid = (left + right) / 2;
            //向左递归进行分解
            mergeSortLargeToSmall(arr, left, mid, temp);
            //向右递归进行分解
            mergeSortLargeToSmall(arr, mid + 1, right, temp);
            //合并
            mergeLargeToSmall(arr, left, mid, right, temp);
        }
    }

    /**
     * 从小到大合并的方法
     *
     * @param arr   排序的原始数组
     * @param left  左边有序序列的初始索引
     * @param mid   中间索引
     * @param right 右边索引
     * @param temp  做中转的数组
     */
    public static void mergeSmallToLarge(int[] arr, int left, int mid, int right, int[] temp) {

        /**  初始化i, 左边有序序列的初始索引 */
        int i = left;
        /** 初始化j, 右边有序序列的初始索引 */
        int j = mid + 1;
        /** 指向temp数组的当前索引 */
        int t = 0;
        // 合并冲突数据
        while (i <= mid && j <= right) {
            if (arr[i] <= arr[j]) {
                temp[t++] = arr[i++];
            } else {
                temp[t++] = arr[j++];
            }
        }
        // 合并剩余数据
        //左边的有序序列还有剩余的元素，就全部填充到temp
        while (i <= mid) {
            temp[t++] = arr[i++];
        }

        //右边的有序序列还有剩余的元素，就全部填充到temp
        while (j <= right) {
            temp[t++] = arr[j++];
        }
        // 设置原始数组
        t = 0;
        i = left;
        while (i <= right) {
            arr[i++] = temp[t++];
        }
    }

    /**
     * 从大到小合并的方法
     *
     * @param arr   排序的原始数组
     * @param left  左边有序序列的初始索引
     * @param mid   中间索引
     * @param right 右边索引
     * @param temp  做中转的数组
     */
    public static void mergeLargeToSmall(int[] arr, int left, int mid, int right, int[] temp) {

        /**  初始化i, 左边有序序列的初始索引 */
        int i = left;
        /** 初始化j, 右边有序序列的初始索引 */
        int j = mid + 1;
        /** 指向temp数组的当前索引 */
        int t = 0;
        // 合并冲突数据
        while (i <= mid && j <= right) {
            if (arr[i] >= arr[j]) {
                temp[t++] = arr[i++];
            } else {
                temp[t++] = arr[j++];
            }
        }
        // 合并剩余数据
        //左边的有序序列还有剩余的元素，就全部填充到temp
        while (i <= mid) {
            temp[t++] = arr[i++];
        }

        //右边的有序序列还有剩余的元素，就全部填充到temp
        while (j <= right) {
            temp[t++] = arr[j++];
        }
        // 设置原始数组
        t = 0;
        i = left;
        while (i <= right) {
            arr[i++] = temp[t++];
        }
    }

    public static void sortLargeToSmall(int[] arr) {
        mergeSortLargeToSmall(arr, 0, arr.length - 1, new int[arr.length]);
    }

    /**
     * 从小到大排序
     *
     * @param arr
     * @return
     */
    public static void sortSmallToLarge(int[] arr) {
        mergeSortSmallToLarge(arr, 0, arr.length - 1, new int[arr.length]);
    }

    private static void mergeSortSmallToLarge(int[] arr, int left, int right, int[] temp) {
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSortSmallToLarge(arr, left, mid, temp);
            mergeSortSmallToLarge(arr, mid + 1, right, temp);
            mergeSmallToLarge(arr, left, mid, right, temp);
        }
    }


    public static void main(String[] args) {
        MergeSort mergeSort = new MergeSort(10);
        mergeSort.setStart(LocalTime.now());
        MergeSort.sortLargeToSmall(mergeSort.getArray());
        mergeSort.setEnd(LocalTime.now());
        System.out.println(mergeSort);

        mergeSort.setStart(LocalTime.now());
        MergeSort.sortSmallToLarge(mergeSort.getArray());
        mergeSort.setEnd(LocalTime.now());
        System.out.println(mergeSort);
    }

}