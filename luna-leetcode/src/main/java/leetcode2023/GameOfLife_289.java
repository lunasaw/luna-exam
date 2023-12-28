package leetcode2023;

import java.util.Arrays;

/**
 * @author luna
 * @date 2023/12/28
 */
public class GameOfLife_289 {

    /**
     * 根据 百度百科 ， 生命游戏 ，简称为 生命 ，是英国数学家约翰·何顿·康威在 1970 年发明的细胞自动机。
     *
     * 给定一个包含 m × n 个格子的面板，每一个格子都可以看成是一个细胞。每个细胞都具有一个初始状态： 1 即为 活细胞 （live），或 0 即为 死细胞 （dead）。
     * 每个细胞与其八个相邻位置（水平，垂直，对角线）的细胞都遵循以下四条生存定律：
     *
     * 如果活细胞周围八个位置的活细胞数少于两个，则该位置活细胞死亡；
     * 如果活细胞周围八个位置有两个或三个活细胞，则该位置活细胞仍然存活；
     * 如果活细胞周围八个位置有超过三个活细胞，则该位置活细胞死亡；
     * 如果死细胞周围正好有三个活细胞，则该位置死细胞复活；
     * 下一个状态是通过将上述规则同时应用于当前状态下的每个细胞所形成的，其中细胞的出生和死亡是同时发生的。给你 m x n 网格面板 board 的当前状态，返回下一个状态。
     *
     *
     * @param board
     */
    public static void gameOfLife(int[][] board) {
        int rows = board.length;
        int cols = board[0].length;

        // 创建复制数组 copyBoard
        int[][] boardCopy = new int[rows][cols];

        // 从原数组复制一份到 copyBoard 中
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                boardCopy[row][col] = board[row][col];
            }
        }

        for (int[] num : boardCopy) {
            System.out.println(Arrays.toString(num));
        }
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                int total = getNext(boardCopy, i, j);
                if (total < 2){
                    board[i][j] = 0;
                } else if (board[i][j] == 1 && (total == 2 || total == 3)){
                    board[i][j] = 1;
                } else if (total > 3){
                    board[i][j] = 0;
                } else if (board[i][j] == 0 && total == 3){
                    board[i][j] = 1;
                }
            }
        }
        System.out.println("-------");
        for (int[] num : boardCopy) {
            System.out.println(Arrays.toString(num));
        }
    }

    public int RoundLifeNum(int i, int j, int[][] board){
        return check(i-1,j-1,board) + check(i-1,j,board) + check(i-1,j+1,board) + check(i,j-1,board) + check(i,j+1,board) + check(i+1,j-1,board) + check(i+1,j,board) + check(i+1,j+1,board);
    }

    public int check(int i, int j, int[][] board){
        if(i < 0 || i >= board.length || j < 0 || j >= board[0].length || (board[i][j] & 1) == 0){
            return 0;
        }
        return 1;
    }

    public static int getNext(int[][] board, int i, int j) {
        Integer i1 = null;
        Integer i2 = null;
        Integer i3 = null;
        Integer i4 = null;
        Integer i5 = null;
        Integer i6 = null;
        Integer i7 = null;
        Integer i8 = null;
        Integer i9 = null;
        if (i - 1 < 0){
             i1 = 0;
             i4 = 0;
             i7 = 0;
        }

        if (j - 1 < 0){
             i1 = 0;
             i2 = 0;
             i3 = 0;
        }

        if (i + 1 >= board.length){
            i3 = 0;
            i6 = 0;
            i9 = 0;
        }

        if (j + 1 >= board[i].length){
             i7 = 0;
             i8 = 0;
             i9 = 0;
        }

        if (i1 == null){
            i1 = board[i - 1][j - 1];
        }

        if (i2 == null){
            i2 = board[i][j - 1];
        }

        if (i3 == null){
            i3 = board[i + 1][j - 1];
        }

        if (i4 == null){
            i4 = board[i - 1][j];
        }

        i5 = board[i][j];

        if (i6 == null){
            i6 =  board[i + 1][j] ;
        }

        if (i7 == null){
            i7 = board[i - 1][j + 1];
        }

        if (i8 == null){
            i8 = board[i][j + 1];
        }

        if (i9 == null){
            i9 = board[i + 1][j + 1];
        }

//       i1 = board[i - 1][j - 1];
//       i2 = board[i][j - 1];
//       i3 = board[i + 1][j - 1];
//
//       i4 = board[i - 1][j];
//       i5 = board[i][j];
//       i6 =  board[i + 1][j] ;
//
//       i7 = board[i - 1][j + 1];
//       i8 = board[i][j + 1];
//       i9 = board[i + 1][j + 1];

        int total = i1 + i2 + i3 + i4 + i6 + i7 + i8 + i9;

        return total;
    }

    public static void main(String[] args) {
        // [[0,1,0],[0,0,1],[1,1,1],[0,0,0]]
        int[][] nums = new int[][]{
                new int[]{0,1,0},
                new int[]{0,0,1},
                new int[]{1,1,1},
                new int[]{0,0,0}
        };
        gameOfLife(nums);
        System.out.println("---------");
        for (int[] num : nums) {
            System.out.println(Arrays.toString(num));
        }
    }

}
