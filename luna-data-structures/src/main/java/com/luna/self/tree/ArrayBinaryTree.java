package com.luna.self.tree;

/**
 * @author luna@mac
 * @className ArrayBinaryTree.java
 * @description TODO
 * @createTime 2021年03月03日 15:45:00
 */
public class ArrayBinaryTree {

    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5, 6, 7};
        ArrayTree arrayTree = new ArrayTree(arr);
        arrayTree.preOrder(0);
    }

}

class ArrayTree {
    private int[] arr;

    public ArrayTree(int[] arr) {
        this.arr = arr;
    }

    public void preOrder(int index) {
        if (arr == null || arr.length == 0) {
            System.out.println("数组为空");
        }
        System.out.println(arr[index]);
        // 左
        if ((index * 2 + 1) < arr.length) {
            preOrder(2 * index + 1);
        }
        // 右
        if ((index * 2 + 2) < arr.length) {
            preOrder(2 * index + 2);
        }
    }

}
