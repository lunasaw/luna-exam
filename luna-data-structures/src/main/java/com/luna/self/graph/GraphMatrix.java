package com.luna.self.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author luna@mac
 * @className GraphMatrix.java
 * @description TODO 无向图
 * @createTime 2021年03月20日 14:03:00
 */
public class GraphMatrix {

    /** 顶点集合 */
    private List<String> vertexList;

    /** 邻接矩阵 存储图 0不通 1能通 */
    private int[][]      array;

    /** 边对个数 */
    private int          numberOfEdges;

    /** 访问记录 */
    private boolean[]    isVisited;

    public GraphMatrix(int nodeNumber) {
        this.numberOfEdges = 0;
        array = new int[nodeNumber][nodeNumber];
        isVisited = new boolean[nodeNumber];
        vertexList = new ArrayList<>(nodeNumber);
    }

    /**
     * 添加结点
     *
     * @param vertex
     */
    public void insert(String vertex) {
        vertexList.add(vertex);
    }

    /**
     * 添加边
     * eg. 0 1 1 表示第0到1一个为通路
     *
     * @param v1 第几个顶点
     * @param v2 第几个顶点
     * @param weight 是否通
     */
    public void insertEdge(int v1, int v2, int weight) {
        array[v1][v2] = weight;
        array[v2][v1] = weight;
        numberOfEdges++;
    }

    /**
     * 结点个数
     *
     * @return
     */
    public int getNumberOfVertex() {
        return vertexList.size();
    }

    /**
     * 边的树木
     *
     * @return
     */
    public int getNumberOfEdge() {
        return numberOfEdges;
    }

    /**
     * 获取结点值
     *
     * @param i
     * @return
     */
    public String getValueByIndex(int i) {
        return vertexList.get(i);
    }

    /**
     * 返回v1-v2的权值
     *
     * @param v1
     * @param v2
     * @return
     */
    public int getWeightOfNode(int v1, int v2) {
        return array[v1][v2];
    }

    /**
     * 显示矩阵
     */
    public void display() {
        for (int[] ints : array) {
            System.out.println(Arrays.toString(ints));
        }
    }

    /**
     * 获取第一个邻接结点的下标 不通则返回-1
     * 
     * @param index
     * @return
     */
    public int getFirstNeighbor(int index) {
        for (int i = 0; i < vertexList.size(); i++) {
            if (array[index][i] > 0) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 根据前一个邻接结点的下标获取下一个邻接结点的下标
     * 
     * @param v1
     * @param v2
     * @return
     */
    public int getNextNeighbor(int v1, int v2) {
        for (int i = v2 + 1; i < vertexList.size(); i++) {
            if (array[v1][v2] > 0) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 对所有结点进行搜索
     */
    public void dfsAllNodes() {
        restoreVisited();
        for (int i = 0; i < getNumberOfVertex(); i++) {
            if (!isVisited[i]) {
                dfs(isVisited, i);
            }
        }
    }

    /**
     * 深度优先遍历无向图
     * 
     * @param isVisited 是否访问过
     * @param i 从0开始 递归遍历
     */
    private void dfs(boolean[] isVisited, int i) {
        System.out.print(vertexList.get(i) + ">>");
        isVisited[i] = true;
        int w = getFirstNeighbor(i);
        while (w != -1) {
            if (!isVisited[w]) {
                dfs(isVisited, w);
            }
            // 如果w已经访问过 回溯
            w = getNextNeighbor(i, w);
        }
    }

    /**
     * 广度优先遍历
     * 
     * @param isVisited
     * @param i
     */
    private void bfs(boolean[] isVisited, int i) {
        /** 队列头结点 */
        int u;
        /** 下一个 邻接结点 */
        int w;
        LinkedList<Integer> queue = new LinkedList<>();
        System.out.print(getValueByIndex(i) + ">>");
        isVisited[i] = true;
        // 将结点加入队列
        queue.addLast(i);
        while (!queue.isEmpty()) {
            // 取出头
            u = queue.removeFirst();
            w = getFirstNeighbor(u);
            if (w != -1) {
                if (!isVisited[w]) {
                    System.out.print(getValueByIndex(w) + ">>");
                    isVisited[w] = true;
                    // 入队列
                    queue.addLast(w);
                }
            }
            // 以u为前结点 找w后面下一个对邻接结点 广度在此
            w = getNextNeighbor(u, w);
        }
    }

    public void bfsAllNode() {
        restoreVisited();
        for (int i = 0; i < getNumberOfVertex(); i++) {
            if (!isVisited[i]) {
                bfs(isVisited, i);
            }
        }
    }

    public void restoreVisited() {
        for (int i = 0; i < isVisited.length; i++) {
            isVisited[i] = false;
        }
    }

    public static void main(String[] args) {
        String[] vertexs = {"A", "B", "C", "D", "E"};
        GraphMatrix graphMatrix = new GraphMatrix(5);
        for (String vertex : vertexs) {
            graphMatrix.insert(vertex);
        }
        // A-B A-C B-C B-D B-E 0-A 1-B 2C 3-D 4-E
        graphMatrix.insertEdge(0, 1, 1);
        graphMatrix.insertEdge(0, 2, 1);
        graphMatrix.insertEdge(1, 2, 1);
        graphMatrix.insertEdge(1, 3, 1);
        graphMatrix.insertEdge(1, 4, 1);
        graphMatrix.display();
        System.out.println("深度优先遍历");
        graphMatrix.dfsAllNodes();
        System.out.println("\n广度优先遍历");
        graphMatrix.bfsAllNode();
    }
}
