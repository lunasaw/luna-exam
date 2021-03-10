package com.luna.self.sort;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.Random;

/**
 * @author luna@mac
 * @className InsertSort.java
 * @description TODO 插入排序 每次依次拿出数插入到有序数组的相应位置
 * @createTime 2021年02月23日 12:10:00
 */
public class InsertSort {

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
        return "InsertSort {" +
                "array=" + Arrays.toString(array) +
                ", start=" + start +
                ", end=" + end +
                '}';
    }

    public InsertSort(int n) {
        this.array = new int[n];
        Random random = new Random();
        // 随机初始化数组
        for (int i = 0; i < n; i++) {
            array[i] = random.nextInt(n);
        }
    }

    public static int[] sortLargeToSmall(int[] arr) {
        System.out.println("待排序数组");
        System.out.println(Arrays.toString(arr));

        int insertVal = 0;
        int insertIndex = 0;
        //使用for循环来把代码简化
        for (int i = 1; i < arr.length; i++) {
            // 定义待插入的数
            insertVal = arr[i];
            // 即arr[1]的前面这个数的下标
            insertIndex = i - 1;

            // 给insertVal 找到插入的位置
            // 说明
            // 1. insertIndex >= 0 保证在给insertVal 找插入位置，不越界
            // 2. insertVal < arr[insertIndex] 待插入的数，还没有找到插入位置
            // 3. 就需要将 arr[insertIndex] 后移
            while (insertIndex >= 0 && insertVal > arr[insertIndex]) {
                arr[insertIndex + 1] = arr[insertIndex];
                insertIndex--;
            }

            if (insertIndex + 1 != i) {
                arr[insertIndex + 1] = insertVal;
            }

            System.out.println("第" + i + "轮插入");
            System.out.println(Arrays.toString(arr));
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

    public static void backward(int[] arr, int start, int interval) {
        //1:先把最后一位保存在临时变量中
        int temp = arr[start + interval];

        //2:依次循环数组向右移位
        for (int i = start + interval; i >= start; i--) {
            arr[i] = arr[i - 1];
        }
        //3 将最后一位的值放入第一位
        arr[start] = temp;
    }

    public static void main(String[] args) {
        InsertSort insertSort = new InsertSort(10);
        insertSort.setStart(LocalTime.now());
        InsertSort.sortLargeToSmall(insertSort.getArray());
        insertSort.setEnd(LocalTime.now());
        System.out.println(insertSort);

        insertSort.setStart(LocalTime.now());
        InsertSort.sortSmallToLarge(insertSort.getArray());
        insertSort.setEnd(LocalTime.now());
        System.out.println(insertSort);
    }

}