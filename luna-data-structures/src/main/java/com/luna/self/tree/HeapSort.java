package com.luna.self.tree;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.Random;

/**
 * @author luna@mac
 * @className BubbleSort.java
 * @description TODO 堆排序
 * @createTime 2021年02月22日 14:19:00
 */
public class HeapSort {

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

    public HeapSort(int n) {
        this.array = new int[n];
        Random random = new Random();
        // 随机初始化数组
        for (int i = 0; i < n; i++) {
            array[i] = random.nextInt(n);
        }
    }

    /**
     * 升序排序 大顶堆
     * 降序 小顶堆
     *
     * @param sort
     * @return
     */
    public static int[] sort(int[] sort) {
        System.out.println("待排序数组");
        System.out.println(Arrays.toString(sort));
        // 将一个数组 调整为一个大顶堆
        for (int i = sort.length / 2 - 1; i >= 0; i--) {
            adjustHeap(sort, i, sort.length);
        }
        System.out.println("第一次大顶堆构造完成" + Arrays.toString(sort));
        for (int j = sort.length - 1; j > 0; j--) {
            swap(sort, j, 0);
            adjustHeap(sort, 0, j);
        }
        return sort;
    }

    /**
     * 将i指向的非叶子结点树调整为大顶堆
     *
     * @param arr    待跳转数组
     * @param i      非叶子结点在数组中的索引
     * @param length 对多少个元素进行调整的长度 逐渐减少
     */
    public static void adjustHeap(int[] arr, int i, int length) {
        int temp = arr[i];
        // k = 2 * i + 1 i指向的非叶子结点的下一个左节点
        for (int k = 2 * i + 1; k < length; k = 2 * k + 1) {
            if (k + 1 < length && arr[k] < arr[k + 1]) {
                // k 指向右结点
                k++;
            }
            if (arr[k] > temp) {
                // 子节点大于父节点
                arr[i] = arr[k];
                i = k;
            } else {
                break;
            }
        }
        // 将temp 放回交换的位置
        arr[i] = temp;
        // 局部大顶堆
        System.out.println(" 局部大顶堆：" + Arrays.toString(arr));
    }

    public static void swap(int[] array, int i, int j) {
        int temp = 0;
        temp = array[j];
        array[j] = array[i];
        array[i] = temp;
    }

    public static void main(String[] args) {
        HeapSort heapSort = new HeapSort(10);
        heapSort.setStart(LocalTime.now());
        sort(heapSort.getArray());
        heapSort.setEnd(LocalTime.now());
        System.out.println(heapSort);
    }
}
