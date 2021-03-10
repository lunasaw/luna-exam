package com.luna.self.sort;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.Random;

/**
 * @author luna@mac
 * @className InsertSort.java
 * @description TODO 希尔排序 先处理后使用选择排序
 * @createTime 2021年02月23日 12:10:00
 */
public class ShellSort {

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
        return "ShellSort {" +
                "array=" + Arrays.toString(array) +
                ", start=" + start +
                ", end=" + end +
                '}';
    }

    public ShellSort(int n) {
        this.array = new int[n];
        Random random = new Random();
        // 随机初始化数组
        for (int i = 0; i < n; i++) {
            array[i] = random.nextInt(n);
        }
    }

    /**
     * 从小到大前期准备
     *
     * @param arr
     */
    public static void preShellSmallToLarge(int[] arr) {
        System.out.println("待排序数组 arr=" + Arrays.toString(arr));
        int count = 0;
        for (int stepLength = arr.length / 2; stepLength > 0; stepLength /= 2) {
            for (int i = 0; i < arr.length; i++) {
                for (int j = i - stepLength; j >= 0; j -= stepLength) {
                    if (arr[j] > arr[j + stepLength]) {
                        int temp = arr[j];
                        arr[j] = arr[j + stepLength];
                        arr[j + stepLength] = temp;
                    }
                }
            }
            System.out.println("希尔排序第" + (++count) + "轮 stepLength=" + stepLength + " arr=" + Arrays.toString(arr));
        }
    }


    /**
     * 前期准备
     *
     * @param arr
     */
    public static void preShellLargeToSmall(int[] arr) {
        System.out.println("待排序数组 arr=" + Arrays.toString(arr));
        int count = 0;
        // 每个stepLength循环
        for (int stepLength = arr.length / 2; stepLength > 0; stepLength /= 2) {
            // 数组循环
            for (int i = 0; i < arr.length; i++) {
                // 单个stepLength下的组内循环
                for (int j = i - stepLength; j >= 0; j -= stepLength) {
                    // 从大到小  后面大于前面则交换
                    if (arr[j] < arr[stepLength + j]) {
                        int temp = arr[j];
                        arr[j] = arr[stepLength + j];
                        arr[stepLength + j] = temp;
                    }
                }
            }
            System.out.println("希尔排序第" + (++count) + "轮 stepLength=" + stepLength + " arr=" + Arrays.toString(arr));
        }
    }

    public static int[] sortLargeToSmall(int[] arr) {
        int insertVal = 0;
        int insertIndex = 0;
        for (int i = 1; i < arr.length; i++) {
            insertVal = arr[i];
            insertIndex = i - 1;
            while (insertIndex >= 0 && insertVal > arr[insertIndex]) {
                arr[insertIndex + 1] = arr[insertIndex];
                insertIndex--;
            }

            if (insertIndex + 1 != i) {
                arr[insertIndex + 1] = insertVal;
            }
        }
        return arr;
    }

    /**
     * 从小到大排序
     *
     * @param arr
     * @return
     */
    public static int[] sortSmallToLarge(int[] arr) {
        int temp;
        int index;
        for (int i = 1; i < arr.length; i++) {
            temp = arr[i];
            index = i - 1;
            while (index >= 0 && temp < arr[index]) {
                arr[index + 1] = arr[index];
                index--;
            }
            if (index + 1 != i) {
                arr[index + 1] = temp;
            }

        }
        return arr;
    }

    /**
     * shell 排序移位法
     *
     * @param arr
     */
    public static void shellSort(int[] arr) {
        System.out.println("待排序数组 arr=" + Arrays.toString(arr));
        int count = 0;
        // 每个stepLength循环
        for (int stepLength = arr.length / 2; stepLength > 0; stepLength /= 2) {
            for (int i = stepLength; i < arr.length; i++) {
                int temp = arr[i];
                int index = i;
                if (arr[index] > arr[index - stepLength]) {
                    while (index - stepLength >= 0 && temp > arr[index - stepLength]) {
                        arr[index] = arr[index - stepLength];
                        index -= stepLength;
                    }
                    arr[index] = temp;
                }
                System.out.println("希尔排序第" + (++count) + "轮 stepLength=" + stepLength + " arr=" + Arrays.toString(arr));
            }
        }
    }

    public static void main(String[] args) {
        ShellSort shellSort = new ShellSort(80);

        shellSort.setStart(LocalTime.now());
        ShellSort.preShellLargeToSmall(shellSort.getArray());
        ShellSort.sortLargeToSmall(shellSort.getArray());
        shellSort.setEnd(LocalTime.now());
        System.out.println(shellSort);

        shellSort.setStart(LocalTime.now());
        ShellSort.preShellSmallToLarge(shellSort.getArray());
        ShellSort.sortSmallToLarge(shellSort.getArray());
        shellSort.setEnd(LocalTime.now());
        System.out.println(shellSort);

        shellSort.setStart(LocalTime.now());
        ShellSort.shellSort(shellSort.getArray());
        shellSort.setEnd(LocalTime.now());
        System.out.println(shellSort);
    }

}