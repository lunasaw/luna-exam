package com.luna.self.sort;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.Random;

/**
 * @author luna@mac
 * @className RadixSort.java
 * @description TODO 基数排序  每次对数位上对值大小放桶中排序  从最低位到最高位依次更新
 * @createTime 2021年02月23日 12:10:00
 */
public class RadixSort {

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
        return "RadixSort {" +
                "array=" + Arrays.toString(array) +
                ", start=" + start +
                ", end=" + end +
                '}';
    }

    public RadixSort(int n, int max) {
        this.array = new int[n];
        Random random = new Random();
        // 随机初始化数组
        for (int i = 0; i < n; i++) {
            array[i] = random.nextInt(max);
        }
    }

    /**
     * 找到数组中到最大值
     *
     * @param arr
     * @return
     */
    public static int findMax(int[] arr) {
        int max = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > max) {
                max = arr[i];
            }
        }
        return max;
    }

    public static int[] sortLargeToSmall(int[] arr) {
        System.out.println("待排序数组");
        System.out.println(Arrays.toString(arr));
        //得到数组中最大的数的位数
        int max = findMax(arr);
        //得到最大数是几位数
        int maxLength = (max + "").length();

        //定义一个二维数组，表示10个桶, 每个桶就是一个一维数组
        //说明
        //1. 二维数组包含10个一维数组
        //2. 为了防止在放入数的时候，数据溢出，则每个一维数组(桶)，大小定为arr.length
        //3. 名明确，基数排序是使用空间换时间的经典算法
        int[][] bucket = new int[10][arr.length];

        //为了记录每个桶中，实际存放了多少个数据,我们定义一个一维数组来记录各个桶的每次放入的数据个数
        //可以这里理解
        //比如：bucketElementCounts[0] , 记录的就是  bucket[0] 桶的放入数据个数
        int[] bucketElementCounts = new int[10];


        //这里我们使用循环将代码处理
        for (int i = 0, n = 1; i < maxLength; i++, n *= 10) {
            //(针对每个元素的对应位进行排序处理)， 第一次是个位，第二次是十位，第三次是百位..
            for (int j = 0; j < arr.length; j++) {
                //取出每个元素的对应位的值
                int digitOfElement = arr[j] / n % 10;
                //放入到对应的桶中
                bucket[digitOfElement][bucketElementCounts[digitOfElement]] = arr[j];
                bucketElementCounts[digitOfElement]++;
            }
            //按照这个桶的顺序(一维数组的下标依次取出数据，放入原来数组)
            int index = 0;
            //遍历每一桶，并将桶中是数据，放入到原数组
            for (int k = 0; k < bucketElementCounts.length; k++) {
                //如果桶中，有数据，我们才放入到原数组
                if (bucketElementCounts[k] != 0) {
                    //循环该桶即第k个桶(即第k个一维数组), 放入
                    for (int l = 0; l < bucketElementCounts[k]; l++) {
                        //取出元素放入到arr
                        arr[index++] = bucket[k][l];
                    }
                }
                //第i+1轮处理后，需要将每个 bucketElementCounts[k] = 0 ！！！！
                bucketElementCounts[k] = 0;

            }
            System.out.println("第" + (i + 1) + "轮，对个位的排序处理 arr =" + Arrays.toString(arr));
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
        int max = findMax(arr);
        int maxLength = (max + "").length();
        int[] bucketElementCounts = new int[10];
        int[][] bucket = new int[10][arr.length];
        // 外层位数循环
        for (int i = 0, n = 1; i < maxLength; i++, n *= 10) {
            // 数组元素循环
            for (int j = 0; j < arr.length; j++) {
                int digitOfElement = arr[j] / n % 10;
                //放入到对应的桶中
                bucket[digitOfElement][bucketElementCounts[digitOfElement]] = arr[j];
                bucketElementCounts[digitOfElement]++;
            }
        }
        // 收数据
        int index = 0;
        //遍历每一桶，并将桶中是数据，放入到原数组
        for (int k = 0; k < bucketElementCounts.length; k++) {
            //如果桶中，有数据，我们才放入到原数组
            if (bucketElementCounts[k] != 0) {
                //循环该桶即第k个桶(即第k个一维数组), 放入
                for (int l = 0; l < bucketElementCounts[k]; l++) {
                    //取出元素放入到arr
                    arr[index++] = bucket[k][l];
                }
            }
            //第i+1轮处理后，需要将每个 bucketElementCounts[k] = 0 ！！！！
            bucketElementCounts[k] = 0;
        }
        return arr;
    }


    public static void main(String[] args) {
        RadixSort radixSort = new RadixSort(10, 500);
        radixSort.setStart(LocalTime.now());
        RadixSort.sortLargeToSmall(radixSort.getArray());
        radixSort.setEnd(LocalTime.now());
        System.out.println(radixSort);

    }

}