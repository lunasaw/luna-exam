package com.luna.practice.ls;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class a4 {
    static Scanner  cin   = new Scanner(System.in);
    static T        t;
    static int      count = 1;
    static Queue<T> q     = new LinkedList<T>();
    static Queue<T> Q     = new LinkedList<T>();

    public static void main(String[] args) {
        // 1
        // 2 3
        // 4 5 6 7
        // 8 9 10 11 12

        int n = cin.nextInt(); // 树里面有几个元素
        T t = new T(cin.nextInt()); // 先赋值根元素
        Q.add(t); // 把根元素放到队列Q
        fuzhi(n); // 进行树的赋值

        System.out.print("dfs: "); // 递归深搜/先序
        dfs(t);
        System.out.println();

        System.out.print("bfs: "); // 循环广搜
        bfs(t);
        System.out.println();

        System.out.print("Bfs: "); // 递归广搜
        q.add(t);
        Bfs();
        System.out.println();

    }

    public static void dfs(T t) { // 深搜
        System.out.print(t.val + " ");
        if (t.left != null)
            dfs(t.left);
        if (t.right != null)
            dfs(t.right);
    }

    private static void bfs(T t) { // 循环广搜
        Queue<T> q1 = new LinkedList<T>();
        q1.add(t);
        while (!q1.isEmpty()) {
            t = q1.poll();
            System.out.print(t.val + " ");
            if (t.left != null)
                q1.add(t.left);
            if (t.right != null)
                q1.add(t.right);
        }
    }

    public static void Bfs() { // 递归广搜
        t = q.poll();
        if (t == null)
            return;
        System.out.print(t.val + " ");
        if (t.left != null)
            q.add(t.left);
        if (t.right != null)
            q.add(t.right);
        Bfs();
    }

    public static void fuzhi(int n) { // 利用递归广搜的原理，进行树的赋值
        t = Q.poll();

        t.left = new T(cin.nextInt()); // 赋值左元素
        count++;
        Q.add(t.left);
        if (n == count)
            return; // 当输入元素达到指定值后，直接返回

        t.right = new T(cin.nextInt()); // 赋值右元素
        count++;
        Q.add(t.right);
        if (n == count)
            return;

        fuzhi(n);
    }
}

class T { // 树的结构体
    int val;
    T   left;
    T   right;

    public T(int x) {
        this.val = x;
    }
}