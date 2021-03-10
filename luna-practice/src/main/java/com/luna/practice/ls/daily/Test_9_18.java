package com.luna.practice.ls.daily;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class Test_9_18 {

    public static void main(String[] args) {
        // perm(new int[] { 2, 1, 5, 8, 4 }, new Stack<>());
        // perm(new int[] { 1, 2, 3 }, 0, 2);
        System.out.println(perm(new int[]{2, 1, 5}));
        //System.out.println(perm(new int[] { }));
    }

    public static void perm(int[] array, Stack<Integer> stack) {
        if (array.length <= 0) {
            // 进入了叶子节点，输出栈中内容
            System.out.println(stack);
        } else {
            for (int i = 0; i < array.length; i++) {
                // tmepArray是一个临时数组，用于就是Ri
                // eg：1，2，3的全排列，先取出1，那么这时tempArray中就是2，3
                int[] tempArray = new int[array.length - 1];
                System.arraycopy(array, 0, tempArray, 0, i);
                System.arraycopy(array, i + 1, tempArray, i, array.length - i - 1);
                stack.push(array[i]);
                perm(tempArray, stack);
                stack.pop();
            }
        }
    }

    public static void perm(int[] array, int start, int end) {
        if (start == end) {
            System.out.println(Arrays.toString(array));
        } else {
            for (int i = start; i <= end; i++) {
                // 1，2，3的全排列这块相当于将其中一个提了出来，下次递归从start+1开始
                swap(array, start, i);
                perm(array, start + 1, end);
                // 这块是复原数组，为了保证下次另外的同级递归使用数组不会出错
                // 这块可以通过树来理解，每次回退一步操作，交换回去
                swap(array, start, i);
            }
        }
    }

    public static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    public static List<List<Integer>> perm(int[] nums) {
        // write your code here
        List<List<Integer>> list = new ArrayList<>();
        if (nums == null | nums.length == 0) {
            list.add(new ArrayList<Integer>());
            return list;
        }
        // 排序
        //insertionSort(nums);
        Arrays.sort(nums);
        List<Integer> array = new ArrayList<Integer>();
        for (int t : nums) {
            array.add(t);
        }
        list.add(array);
        int i;
        while ((i = hasNext(nums)) > 0) {
            int a = nums[i - 1];
            int b = nums[i];
            int k = i;
            int j = i;
            while (j < nums.length) {
                if (nums[j] > a & nums[j] <= b) {
                    b = nums[j];
                    k = j;
                }
                j++;
            }
            swap(nums, i - 1, k);
            // 反转
            reverse(nums, i, nums.length - 1);
            List<Integer> arr = new ArrayList<Integer>();
            for (int t : nums) {
                arr.add(t);
            }
            list.add(arr);
        }
        return list;
    }

    public static int hasNext(int[] nums) {
        for (int i = nums.length - 1; i > 0; i--) {
            if (nums[i] > nums[i - 1]) {
                return i;
            }
        }
        return 0;
    }

    public static void reverse(int[] nums, int i, int j) {
        while (i < j) {
            swap(nums, i++, j--);
        }
    }

    public static void insertionSort(int[] arr) {
        int len = arr.length;
        int preIndex, current;
        for (int i = 1; i < len; i++) {
            preIndex = i - 1;
            current = arr[i];
            while (preIndex >= 0 && arr[preIndex] > current) {
                arr[preIndex + 1] = arr[preIndex];
                preIndex--;
            }
            arr[preIndex + 1] = current;
        }
    }

}
