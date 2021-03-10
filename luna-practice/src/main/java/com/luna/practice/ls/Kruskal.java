package com.luna.practice.ls;

import java.util.ArrayList;
import java.util.Scanner;


public class Kruskal {
    // 内部类，其对象表示连通图中一条边
    class edge {
        public int a;   //  开始顶点
        public int b;   // 结束顶点
        public int value;   // 权值

        edge(int a, int b, int value) {
            this.a = a;
            this.b = b;
            this.value = value;
        }
    }

    // 使用合并排序，把数组A按照其value值进行从小到大排序
    public void edgeSort(edge[] A) {
        if (A.length > 1) {
            edge[] leftA = getHalfEdge(A, 0);
            edge[] rightA = getHalfEdge(A, 1);
            edgeSort(leftA);
            edgeSort(rightA);
            mergeEdgeArray(A, leftA, rightA);
        }
    }

    // judge = 0返回数组A的左半边元素，否则返回右半边元素
    public edge[] getHalfEdge(edge[] A, int judge) {
        edge[] half;
        if (judge == 0) {
            half = new edge[A.length / 2];
            for (int i = 0; i < A.length / 2; i++)
                half[i] = A[i];
        } else {
            half = new edge[A.length - A.length / 2];
            for (int i = 0; i < A.length - A.length / 2; i++)
                half[i] = A[A.length / 2 + i];
        }
        return half;
    }

    // 合并leftA和rightA，并按照从小到大顺序排列
    public void mergeEdgeArray(edge[] A, edge[] leftA, edge[] rightA) {
        int i = 0;
        int j = 0;
        int len = 0;
        while (i < leftA.length && j < rightA.length) {
            if (leftA[i].value < rightA[j].value) {
                A[len++] = leftA[i++];
            } else {
                A[len++] = rightA[j++];
            }
        }
        while (i < leftA.length) {
            A[len++] = leftA[i++];
        }
        while (j < rightA.length) {
            A[len++] = rightA[j++];
        }
    }

    // 获取节点a的根节点编号
    public int find(int[] id, int a) {
        int i, root, k;
        root = a;
        while (id[root] >= 0) {
            root = id[root];  // 此处，若id[root] >= 0，说明此时的a不是根节点，因为唯有根节点的值小于0
        }
        k = a;
        while (k != root) {  // 将a节点所在树的所有节点，都变成root的直接子节点
            i = id[k];
            id[k] = root;
            k = i;
        }
        return root;
    }

    // 判断顶点a和顶点b的根节点大小，根节点值越小，代表其对应树的节点越多，将节点少的树的节点添加到节点多的树上
    public void union(int[] id, int a, int b) {
        int ida = find(id, a);   // 得到顶点a的根节点
        int idb = find(id, b);   // 得到顶点b的根节点
        int num = id[ida] + id[idb];  // 由于根节点值必定小于0，此处num值必定小于零
        if (id[ida] < id[idb]) {
            id[idb] = ida;    // 将顶点b作为顶点a根节点的直接子节点
            id[ida] = num;   // 更新根节点的id值
        } else {
            id[ida] = idb;    // 将顶点a作为顶点b根节点的直接子节点
            id[idb] = num;    // 更新根节点的id值
        }
    }

    // 获取图A的最小生成树
    public ArrayList<edge> getMinSpanTree(int n, edge[] A) {
        ArrayList<edge> list = new ArrayList<edge>();
        int[] id = new int[n];
        for (int i = 0; i < n; i++) {
            id[i] = -1;        //  初始化id(x)，令所有顶点的id值为-1，即表示为根节点
        }
        edgeSort(A);   //  使用合并排序，对于图中所有边权值进行从小到大排序
        int count = 0;
        for (int i = 0; i < A.length; i++) {
            int a = A[i].a;
            int b = A[i].b;
            int ida = find(id, a - 1);
            int idb = find(id, b - 1);
            if (ida != idb) {
                list.add(A[i]);
                count++;
                union(id, a - 1, b - 1);
            }
            // 输出每一次添加完一条边后的所有顶点id值

            for (int j = 0; j < id.length; j++)
                System.out.print(id[j] + " ");
            System.out.println();

            if (count >= n - 1) {
                break;
            }
        }
        return list;
    }

    public static void main(String[] args) {
        Kruskal test = new Kruskal();
        Scanner in = new Scanner(System.in);
        System.out.println("请输入顶点数a和具体边数p：");
        int n = in.nextInt();
        int p = in.nextInt();
        edge[] A = new edge[p];
        System.out.println("请依次输入具体边对于的顶点和权值：");
        for (int i = 0; i < p; i++) {
            int a = in.nextInt();
            int b = in.nextInt();
            int value = in.nextInt();
            A[i] = test.new edge(a, b, value);
        }
        System.out.println("\n==================分隔符==================");
        ArrayList<edge> list = test.getMinSpanTree(n, A);
        System.out.println("使用Kruskal算法得到的最小生成树具体边和权值分别为：");
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i).a + "——>" + list.get(i).b + ", " + list.get(i).value);
        }
    }
}
