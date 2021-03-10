package com.luna.practice.ls;

public class DFSTraverse {
    // 构造图的边
    private int[][]   edges      = {{0, 1, 0, 0, 0, 1, 0, 0, 0},
        {1, 0, 1, 0, 0, 0, 1, 0, 1}, {0, 1, 0, 1, 0, 0, 0, 0, 1},
        {0, 0, 1, 0, 1, 0, 1, 1, 1}, {0, 0, 0, 1, 0, 1, 0, 1, 0},
        {1, 0, 0, 0, 1, 0, 1, 0, 0}, {0, 1, 0, 1, 0, 1, 0, 1, 0},
        {0, 0, 0, 1, 1, 0, 1, 0, 0}, {0, 1, 1, 1, 0, 0, 0, 0, 0}};
    // 构造图的顶点
    private String[]  vertexs    = {"A", "B", "C", "D", "E", "F", "G", "H", "I"};
    // 记录被访问顶点
    private boolean[] verStatus;
    // 顶点个数
    private int       vertexsNum = vertexs.length;

    public void DFSTra() {
        verStatus = new boolean[vertexsNum];
        for (int i = 0; i < vertexsNum; i++) {
            if (verStatus[i] == false) {
                DFS(i);
            }
        }
    }

    // 递归深搜
    private void DFS(int i) {
        System.out.print(vertexs[i] + " ");
        verStatus[i] = true;
        for (int j = firstAdjVex(i); j >= 0; j = nextAdjvex(i, j)) {
            if (!verStatus[j]) {
                DFS(j);
            }
        }
    }

    // 返回与i相连的第一个顶点
    private int firstAdjVex(int i) {
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
            if (edges[i][j] == 1) {
                return j;
            }
        }
        return -1;
    }

    // 测试
    public static void main(String[] args) {
        new DFSTraverse().DFSTra();
    }

}
