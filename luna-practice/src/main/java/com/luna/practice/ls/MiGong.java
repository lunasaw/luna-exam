package com.luna.practice.ls;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

class point {
    public int x, y;
}

public class MiGong {
    /**
     * 5 5
     * 0 0 0 1 0
     * 0 1 0 0 0
     * 0 0 1 0 1
     * 1 1 0 1 0
     * 0 1 1 1 0
     */

    static Scanner cin = new Scanner(System.in);
    public static int hang;
    public static int lie;
    public static int[][] migong;
    public static point[][] biaoji;                      // 记录上一步

    public void creat(int hang, int lie) {
        // 将迷宫外围围上一层墙，则不用担心边界问题
        int i, j;
        for (i = 0; i < hang + 2; i++)
            migong[i][0] = migong[i][lie + 1] = 1;
        for (i = 0; i < lie + 2; i++) {
            migong[0][i] = migong[hang + 1][i] = 1;
        }
        for (i = 1; i <= hang; i++) {
            for (j = 1; j <= lie; j++) {
                migong[i][j] = cin.nextInt();
            }
        }
    }

    public boolean bfs(int h, int l, int x, int y, point p[]) {
        if (x == h && y == l)
            return true;
        Queue<point> queue = new LinkedList<point>();// 建立存放坐标的队列
        point now = new point();// 当前位置
        now.x = x;
        now.y = y;
        queue.offer(now);
        migong[now.x][now.y] = -1;// 标记为已走过

        while (!queue.isEmpty()) {
            now = queue.poll();// 返回并去除队列中第一个元素

            for (int i = 0; i < 8; i++) {
                if (now.x + p[i].x == h && now.y + p[i].y == l) {
                    migong[now.x + p[i].x][now.y + p[i].y] = -1;// 设置下一步访问过
                    biaoji[h][l] = now;// 记录最后一步的上一步
                    return true;
                }
                if (migong[now.x + p[i].x][now.y + p[i].y] == 0)// 下一步未走过
                {
                    point p2 = new point();
                    p2.x = now.x + p[i].x;
                    p2.y = now.y + p[i].y;
                    queue.offer(p2);// 加入队列
                    migong[p2.x][p2.y] = -1;// 设置下一步访问过
                    biaoji[p2.x][p2.y] = now;// 记录这一步的上一步
                }
            }
        }

        return false;
    }

    public void print(int h, int l) {
        Stack stack = new Stack();
        point p2 = new point();
        p2.x = h;
        p2.y = l;
        while (p2.x != 1 || p2.y != 1)// 当坐标为（1，1）时停止
        {
            stack.push(p2);
            p2 = biaoji[p2.x][p2.y];
        }
        System.out.print("(1,1)");
        while (!stack.isEmpty()) {
            p2 = (point) stack.pop();
            System.out.print(" (" + p2.x + "," + p2.y + ")");
        }
        System.out.println();
    }

    public static void main(String[] args) {

        // 八方向坐标
        point[] p = new point[8];
        for (int i = 0; i < 8; i++) {
            p[i] = new point();
        }
        p[0].x = -1;
        p[0].y = -1;
        p[1].x = 0;
        p[1].y = -1;
        p[2].x = 1;
        p[2].y = -1;
        p[3].x = 1;
        p[3].y = 0;
        p[4].x = 1;
        p[4].y = 1;
        p[5].x = 0;
        p[5].y = 1;
        p[6].x = -1;
        p[6].y = 1;
        p[7].x = -1;
        p[7].y = 0;

        MiGong mg = new MiGong();
        // 迷宫初始化
        hang = cin.nextInt();
        lie = cin.nextInt();
        migong = new int[hang + 2][lie + 2];
        mg.creat(hang, lie);

        // bfs广度优先遍历
        biaoji = new point[hang + 2][lie + 2];
        if (mg.bfs(hang, lie, 1, 1, p)) {
            System.out.println("YES");
            mg.print(hang, lie);
            // 打印输出
        } else {
            System.out.println("No");
        }
    }

}