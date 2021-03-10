package com.luna.self.sort;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.Random;

/**
 * @author luna@mac
 * @className BubbleSort.java
 * @description TODO 冒泡排序 对相邻对两个数进行比较交换
 * 时间复杂度 O(n^2) 最差时间复杂度 O(n^2) 空间复杂度 O(n)
 * @createTime 2021年02月22日 14:19:00
 */
public class BubbleSort {

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
        return "BubbleSort {" +
                "array=" + Arrays.toString(array) +
                ", start=" + start +
                ", end=" + end +
                '}';
    }

    public BubbleSort(int n) {
        this.array = new int[n];
        Random random = new Random();
        // 随机初始化数组
        for (int i = 0; i < n; i++) {
            array[i] = random.nextInt(n);
        }
    }

    public static int[] sort(int[] sort) {
        System.out.println("待排序数组");
        System.out.println(Arrays.toString(sort));
        // 如果某一趟排序 均未发生交换 表示已经有序 直接退出
        boolean flag = true;
        for (int i = 0; i < sort.length - 1; i++) {
            for (int j = 0; j < sort.length - 1; j++) {
                if (sort[j] > sort[j + 1]) {
                    swap(sort, j, j + 1);
                    flag = false;
                }
            }
            System.out.println("第" + (i + 1) + "趟排序后的数组");
            System.out.println(Arrays.toString(sort));
            if (flag) {
                break;
            } else {
                // 重置
                flag = true;
            }
        }
        return sort;
    }

    public static void swap(int[] array, int i, int j) {
        int temp = 0;
        temp = array[j];
        array[j] = array[i];
        array[i] = temp;
    }

    public static void main(String[] args) {
        BubbleSort bubbleSort = new BubbleSort(10);
        bubbleSort.setStart(LocalTime.now());
        sort(bubbleSort.getArray());
        bubbleSort.setEnd(LocalTime.now());
        System.out.println(bubbleSort);
    }
}
