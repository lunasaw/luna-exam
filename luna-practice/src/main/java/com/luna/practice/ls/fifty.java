package com.luna.practice.ls;

import java.util.Scanner;

/**
 * Created by Blue2Te on 16/10/31.
 */

public class fifty {

    public static int move[][] = {{-1, 0}, {0, -1}, {0, 1}, {1, 0}};//Up 0 Left 1 Right 2 Bottom 3
    public static int initList[] = new int[16];
    public static int goalList[] = new int[16];
    public static final int maxLevel = 60;
    public static int leafLevel, limit;
    public static int rec[] = new int[maxLevel + 10];//0,1,2,3


    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        while (true) {
            leafLevel = -1;
            System.out.println("Input:请输入初始矩阵(16个数据，空格分隔)");
            for (int i = 0; i < 16; i++) {
                initList[i] = in.nextInt();
            }
            System.out.println("Input:请输入目标矩阵(16个数据，空格分隔)");
            for (int i = 0; i < 16; i++) {
                goalList[i] = in.nextInt();
            }
            System.out.println();
            ListAndPos initMatrix = new ListAndPos(initList);
            ListAndPos tempMatrix = new ListAndPos(initList);
            ListAndPos goalMatrix = new ListAndPos(goalList);
            if (tempMatrix.hasSolution(goalMatrix)) {
                limit = tempMatrix.fv(goalMatrix);
                while (limit <= maxLevel && !dfs(tempMatrix, goalMatrix, 0, 0)) {
                    limit++;    //迭代加深。
                }
                if (leafLevel != -1) {
                    print(initMatrix);
                } else {
                    System.out.println("在" + maxLevel + "步中找不到解，放弃寻找！");
                }
            } else {
                System.out.println("Problem has no solution!");
            }
        }
    }

    public static void print(ListAndPos lap) {
        lap.print();
        for (int i = 0; i < leafLevel; i++) {
            System.out.println("step:" + (i + 1));
            lap.swap(lap.val2pos[0][0], lap.val2pos[0][1], lap.val2pos[0][0] + move[rec[i]][0], lap.val2pos[0][1] + move[rec[i]][1]);
            lap.print();
        }
    }

    public static boolean dfs(ListAndPos lap, ListAndPos goalLap, int level, int pmove) {
        int val;
        if (level == limit) {
            val = lap.fv(goalLap);
            if (val == 0) {
                leafLevel = level;
                return true;
            }
            return false;
        }

        int newsr, newsc;
        int rawsr, rawsc;
        for (int i = 0; i < 4; i++) {
            if (pmove + i == 3 && level > 0) continue;
            rawsr = lap.val2pos[0][0];
            rawsc = lap.val2pos[0][1];
            newsr = rawsr + move[i][0];
            newsc = rawsc + move[i][1];
            if (0 <= newsr && newsr < 4 && 0 <= newsc && newsc < 4) {
                lap.swap(rawsr, rawsc, newsr, newsc);
                val = lap.fv(goalLap);
                if (level + val <= limit) {
                    rec[level] = i;
                    if (dfs(lap, goalLap, level + 1, i)) return true;
                }
                lap.swap(newsr, newsc, rawsr, rawsc);
            }
        }
        return false;
    }
}

class ListAndPos {

    public int list[] = new int[16];            //存放第i个位置的数码值
    public int val2pos[][] = new int[16][2];   //存放数码对应的r、c

    public int fv(ListAndPos lap) {//曼哈顿距离
        int cost = 0;
        for (int k = 1; k < 16; k++) {
            cost += Math.abs(val2pos[k][0] - lap.val2pos[k][0]) + Math.abs(val2pos[k][1] - lap.val2pos[k][1]);
        }
        return cost;
    }

    public ListAndPos(int arr[]) {
        for (int i = 0; i < 16; i++) {
            list[i] = arr[i];
        }
        initVal2Pos();
    }

    public void initVal2Pos() {                    //将list值的位置信息记录到val2pos中
        for (int i = 0; i < 16; i++) {
            val2pos[list[i]][0] = i / 4;
            val2pos[list[i]][1] = i % 4;
        }
    }

    public int getNiXu() {
        int nixu = 0;
        for (int i = 0; i < 16; i++) {
            if (list[i] == 0) continue;
            for (int j = i + 1; j < 16; j++) {
                if (list[j] == 0) continue;
                if (list[i] > list[j]) {
                    nixu++;
                }
            }
        }
        return nixu;
    }

    public boolean hasSolution(ListAndPos lap) {
        int nixu1 = getNiXu();
        int nixu2 = lap.getNiXu();
        int zeroXZ = Math.abs(val2pos[0][0] - lap.val2pos[0][0]);
        int nixu = nixu1 + nixu2;
        if (zeroXZ % 2 == 1) {
            if (nixu % 2 == 1)
                return true;
            else
                return false;
        } else {
            if (nixu % 2 == 0)
                return true;
            else
                return false;
        }
    }

    public void swap(int rawsr, int rawsc, int newsr, int newsc) {
        int listpos1 = rawsr * 4 + rawsc;
        int listpos2 = newsr * 4 + newsc;
        val2pos[list[listpos1]][0] = newsr;
        val2pos[list[listpos1]][1] = newsc;
        val2pos[list[listpos2]][0] = rawsr;
        val2pos[list[listpos2]][1] = rawsc;
        int temp = list[listpos1];
        list[listpos1] = list[listpos2];
        list[listpos2] = temp;
    }

    public void print() {
        for (int i = 0; i < 16; i = i + 4) {
            System.out.printf("%d %d %d %d%n", list[i], list[i + 1], list[i + 2], list[i + 3]);
        }
        System.out.println("----------------------");
    }
}