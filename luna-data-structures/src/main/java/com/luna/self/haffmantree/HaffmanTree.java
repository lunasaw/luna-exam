package com.luna.self.haffmantree;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author luna@mac
 * @className HaffmanTree.java
 * @description TODO
 * @createTime 2021年03月04日 16:47:00
 */
public class HaffmanTree {

    public static List<Node> createTree(int[] arr) {
        ArrayList<Node> list = Lists.newArrayList();
        for (int i = 0; i < arr.length; i++) {
            list.add(new Node(arr[i]));
        }
        return list;
    }

    public static void preOrder(Node root) {
        if (root != null) {
            root.preOrder();
        } else {
            System.out.println("空树");
        }
    }


    public static Node haffmanTree(List<Node> tree) {
        while (tree.size() > 1) {
            Collections.sort(tree);
            // 取出权值最小的两个树
            Node left = tree.get(0);
            Node right = tree.get(1);
            // 构建新二叉树
            Node parent = new Node(left.value + right.value);
            parent.left = left;
            parent.right = right;

            tree.remove(left);
            tree.remove(right);
            tree.add(parent);
        }
        return tree.get(0);
    }

    public static void main(String[] args) {
        List<Node> tree = createTree(new int[]{13, 7, 8, 3, 29, 6, 1});
        Collections.sort(tree);
        System.out.println(tree);
        Node node = haffmanTree(tree);
        preOrder(node);
    }

}

class Node implements Comparable<Node> {
    int value;

    Node left;

    Node right;

    public Node(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Node{" +
                "value=" + value +
                '}';
    }

    @Override
    public int compareTo(Node o) {
        return this.value - o.value;
    }

    public void preOrder() {
        System.out.println(this);
        if (this.left != null) {
            this.left.preOrder();
        }
        if (this.right != null) {
            this.right.preOrder();
        }
    }
}