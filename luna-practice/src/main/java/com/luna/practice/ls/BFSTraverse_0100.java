package com.luna.practice.ls;

import java.util.LinkedList;
import java.util.Queue;

public class BFSTraverse_0100 {
    // 构造图的边
    private int[][] edges = {{0, 1, 0, 0, 0, 1, 0, 0, 0},
        {1, 0, 1, 0, 0, 0, 1, 0, 1}, {0, 1, 0, 1, 0, 0, 0, 0, 1},
        {0, 0, 1, 0, 1, 0, 1, 1, 1}, {0, 0, 0, 1, 0, 1, 0, 1, 0},
        {1, 0, 0, 0, 1, 0, 1, 0, 0}, {0, 1, 0, 1, 0, 1, 0, 1, 0},
        {0, 0, 0, 1, 1, 0, 1, 0, 0}, {0, 1, 1, 1, 0, 0, 0, 0, 0}};
    // 构造图的顶点
    private String[] vertexs = {"A", "B", "C", "D", "E", "F", "G", "H", "I"};
    // 记录被访问顶点
    private boolean[] verStatus;
    // 顶点个数
    private int vertexsNum = vertexs.length;

    // 广搜
    private void BFS() {
        verStatus = new boolean[vertexsNum];
        Queue<Integer> temp = new LinkedList<Integer>();
        for (int i = 0; i < vertexsNum; i++) {
            if (!verStatus[i]) {
                System.out.print(vertexs[i] + " ");
                verStatus[i] = true;
                temp.offer(i);
                while (!temp.isEmpty()) {
                    int j = temp.poll();
                    for (int k = firstAdjvex(j); k >= 0; k = nextAdjvex(j, k)) {
                        if (!verStatus[k]) {
                            System.out.print(vertexs[k] + " ");
                            verStatus[k] = true;
                            temp.offer(k);
                        }
                    }
                }
            }
        }
    }

    // 返回与i相连的第一个顶点
    private int firstAdjvex(int i) {
        for (int j = 0; j < vertexsNum; j++) {
            if (edges[i][j] > 0) {
                return j;
            }
        }
        return -1;
    }

    // 返回与i相连的下一个顶点
    private int nextAdjvex(int i, int k) {
        for (int j = (k + 1); j < vertexsNum; j++) {
            if (edges[i][j] > 0) {
                return j;
            }
        }
        return -1;
    }

    // 测试
    public static void main(String args[]) {
        new BFSTraverse_0100().BFS();
    }
}
