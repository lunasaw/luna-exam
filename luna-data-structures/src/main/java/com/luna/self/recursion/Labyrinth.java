package com.luna.self.recursion;

import java.util.Random;

/**
 * @author luna@mac
 * @className Labyrinth.java
 * @description TODO
 * @createTime 2021年02月20日 14:16:00
 */
public class Labyrinth {

    private int[][] labyrinth;

    public Labyrinth(int length) {
        this.labyrinth = new int[length][length];
        for (int i = 0; i < labyrinth.length; i++) {
            labyrinth[0][i] = 1;
            labyrinth[length - 1][i] = 1;
            labyrinth[i][0] = 1;
            labyrinth[i][length - 1] = 1;

            // 随机设置挡板为1
            if (Math.random() < 0.5) {
                labyrinth[new Random().nextInt(length - 2)][new Random().nextInt(length - 2)] = 1;
            }
        }
        // 起始点
        labyrinth[1][1] = 0;
        // 结束点
        labyrinth[length - 2][length - 2] = 0;
    }

    /**
     * 寻路 0未走 1为墙 2可走 3已走过但不通
     *
     * @param map 迷宫
     * @param i   下一步 i
     * @param j   下一步 j
     * @return
     */
    public boolean getWay(int[][] map, int i, int j) {
        if (map[map.length - 2][map.length - 2] == 2) {
            return true;
        } else {
            if (map[i][j] == 0) {
                // 假定可以走
                map[i][j] = 2;
                // 下->右->上->左
                if (getWay(map, i + 1, j)) {
                    return true;
                } else if (getWay(map, i, j + 1)) {
                    return true;
                } else if (getWay(map, i - 1, j)) {
                    return true;
                } else if (getWay(map, i, j - 1)) {
                    return true;
                } else {
                    map[i][j] = 3;
                    return false;
                }
            } else {
                return false;
            }
        }
    }

    public void display() {
        for (int[] ints : labyrinth) {
            for (int anInt : ints) {
                System.out.print(anInt + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Labyrinth labyrinth = new Labyrinth(6);
        labyrinth.getWay(labyrinth.labyrinth, 1, 1);
        labyrinth.display();
    }
}
