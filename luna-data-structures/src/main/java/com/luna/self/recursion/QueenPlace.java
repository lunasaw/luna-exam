package com.luna.self.recursion;

/**
 * @author luna@mac
 * @className QueenPlace.java
 * @description TODO
 * @createTime 2021年02月21日 14:10:00
 */
public class QueenPlace {

    private int queen;

    /**
     * 位置
     */
    private int[] array;

    /**
     * 计数
     */
    private int count;

    private int checkCount;

    public int getQueen() {
        return queen;
    }

    public void setQueen(int queen) {
        this.queen = queen;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCheckCount() {
        return checkCount;
    }

    public void setCheckCount(int checkCount) {
        this.checkCount = checkCount;
    }

    public QueenPlace(int queen) {
        this.queen = queen;
        this.array = new int[queen];
    }

    /**
     * 放置皇后
     *
     * @param n 当前放置第几个皇后
     */
    public void placeQueen(int n) {
        // 如果当前放置完成
        if (n == queen) {
            display();
            return;
        }
        for (int i = 0; i < queen; i++) {
            array[n] = i;
            if (checkQueen(n)) {
                placeQueen(n + 1);
            }
        }
    }

    /**
     * 位置判断
     *
     * @param n
     * @return
     */
    public boolean checkQueen(int n) {
        for (int i = 0; i < n; i++) {
            if (array[n] == array[i] || Math.abs(n - i) == Math.abs(array[n] - array[i])) {
                return false;
            }
        }
        checkCount++;
        return true;
    }

    /**
     * 展示当前皇后摆放位置
     */
    public void display() {
        count++;
        System.out.printf("第%d次皇后摆放如下\n", count);
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        QueenPlace queenPlace = new QueenPlace(4);
        queenPlace.placeQueen(0);
        System.out.println(queenPlace.getCount());
        System.out.println(queenPlace.getCheckCount());
    }
}
