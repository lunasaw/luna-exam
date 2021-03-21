package com.luna.self.binarySearch;

/**
 * @author luna@mac
 * @className BinarySearchNoRecur.java
 * @description TODO 二分非递归查找
 * @createTime 2021年03月21日 15:14:00
 */
public class BinarySearchNoRecur {

    /**
     * 二分查找
     * 
     * @param array 待查找数组
     * @param target 待查找数据
     * @return
     */
    public static int binarySearch(int[] array, int target) {
        int left = 0;
        int right = array.length - 1;
        while (left <= right) {
            int mid = (left + right) / 2;
            if (array[mid] == target) {
                return mid;
            } else if (array[mid] > target) {
                // 左边查找
                right = mid - 1;
            } else {
                // 右边查找
                left = mid + 1;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] arr = {1, 3, 8, 10, 11, 67, 100};
        int index = binarySearch(arr, 100);
        System.out.println("index=" + index);
    }
}
