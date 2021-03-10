package com.luna.self.sort;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.Random;

/**
 * @author luna@mac
 * @className SelectSort.java
 * @description TODO 选择排序 每次循环找出最小或者额最大的一个数放置于第当前次数的i上
 * 时间复杂度 O(n^2) 最差时间复杂度 O(n^2) 空间复杂度 O(n)
 * @createTime 2021年02月22日 14:19:00
 */
public class SelectSort {

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
        return "SelectSort {" +
                "array=" + Arrays.toString(array) +
                ", start=" + start +
                ", end=" + end +
                '}';
    }

    public SelectSort(int n) {
        this.array = new int[n];
        Random random = new Random();
        // 随机初始化数组
        for (int i = 0; i < n; i++) {
            array[i] = random.nextInt(n);
        }
    }

    /**
     * 从大到小排序
     *
     * @param sort
     */
    public static int[] sortLagrToSmall(int[] sort) {
        System.out.println("待排序数组");
        System.out.println(Arrays.toString(sort));
        for (int i = 0; i < sort.length - 1; i++) {
            int maxIndex = i;
            for (int j = i + 1; j < sort.length; j++) {
                if (sort[j] > sort[maxIndex]) {
                    maxIndex = j;
                }
            }
            swap(sort, maxIndex, i);
            System.out.println("第" + (i + 1) + "趟排序后的数组");
            System.out.println(Arrays.toString(sort));
        }
        return sort;
    }


    public static void swap(int[] array, int i, int j) {
        int temp = 0;
        temp = array[j];
        array[j] = array[i];
        array[i] = temp;
    }

    /**
     * 从小到大选择排序
     *
     * @param sort
     * @return
     */
    public static int[] sortSamllToLarge(int[] sort) {
        System.out.println("待排序数组");
        System.out.println(Arrays.toString(sort));
        for (int i = 0; i < sort.length - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < sort.length; j++) {
                if (sort[minIndex] > sort[j]) {
                    minIndex = j;
                }
            }
            swap(sort, minIndex, i);
            System.out.println("第" + (i + 1) + "趟排序后的数组");
            System.out.println(Arrays.toString(sort));

        }
        return sort;
    }

    public static void main(String[] args) {
        SelectSort selectSort = new SelectSort(10);
        selectSort.setStart(LocalTime.now());
        sortSamllToLarge(selectSort.getArray());
        selectSort.setEnd(LocalTime.now());
        System.out.println(selectSort);

        selectSort.setStart(LocalTime.now());
        sortLagrToSmall(selectSort.getArray());
        selectSort.setEnd(LocalTime.now());
        System.out.println(selectSort);
    }
}
