package com.luna.self.sort;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.Random;

/**
 * @author luna@mac
 * @className QuickSort.java
 * @description TODO 快速排序 递归选择基准 左边小于（大于）基准， 右边大于（小于）基准
 * 时间复杂度 O(n^2) 最差时间复杂度 O(n^2) 空间复杂度 O(n)
 * @createTime 2021年02月22日 14:19:00
 */
public class QuickSort {

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
        return "QuickSort {" +
                "array=" + Arrays.toString(array) +
                ", start=" + start +
                ", end=" + end +
                '}';
    }

    public QuickSort(int n) {
        this.array = new int[n];
        Random random = new Random();
        // 随机初始化数组
        for (int i = 0; i < n; i++) {
            array[i] = random.nextInt(n);
        }
    }

    public static int[] sort(int[] sort, int left, int right) {
        // [4, 2, 3, 3, 2], 0, 4
        System.out.println("待排序数组: " + Arrays.toString(sort) + " left:" + left + "  right:" + right);
        // 0
        int l = left;
        // 4
        int r = right;
        int count = 0;
        // 2
        int mid = (left + right) / 2;
        // 3
        int pivot = sort[mid];
        while (l < r) {
            while (sort[l] < pivot) {
                l++;
            }
            while (sort[r] > pivot) {
                r--;
            }
            // 如果左右都满足
            if (l == r) {
                break;
            }
            swap(sort, l, r);
            if (sort[l] == pivot) {
                r--;
            }
            if (sort[r] == pivot) {
                l++;
            }
            System.out.println("快速排序第" + (++count) + "轮 mid=" + mid + " arr=" + Arrays.toString(sort));
        }
        System.out.println("快速排序第" + (++count) + "轮  mid=" + mid + " arr=" + Arrays.toString(sort));
        if (l == r) {
            l++;
            r--;
        }
        // 左递归
        if (left < r) {
            sort(sort, left, r);
        }
        // 右递归
        if (right > l) {
            sort(sort, l, right);
        }
        return sort;
    }

    public static void swap(int[] array, int i, int j) {
        int temp = array[j];
        array[j] = array[i];
        array[i] = temp;
    }

    public static void main(String[] args) {
        QuickSort quickSort = new QuickSort(5);
        quickSort.setStart(LocalTime.now());
        sort(quickSort.getArray(), 0, quickSort.getArray().length - 1);
        quickSort.setEnd(LocalTime.now());
        System.out.println(quickSort);
    }
}
