package leetcode;

/**
 * @author Luna@win10
 * @date 2020/6/11 20:49
 */
public class ValidSudoku {

    /**
     * 判断一个 9x9 的数独是否有效。只需要根据以下规则，验证已经填入的数字是否有效即可。
     *
     * 数字 1-9 在每一行只能出现一次。
     * 数字 1-9 在每一列只能出现一次。
     * 数字 1-9 在每一个以粗实线分隔的 3x3 宫内只能出现一次。
     *
     * @param board
     * @return
     */
    public boolean isValidSudoku(char[][] board) {
        // should be 9*9
        if (board == null || board.length != 9 || board[0].length != 9) {
            return false;
        }
        // check each row
        for (int row = 0; row < 9; row++) {
            boolean[] booleans = new boolean[9];
            for (int idx = 0; idx < 9; idx++) {
                char c = board[row][idx];
                if (c != '.') {
                    int num = c - '1';
                    if (booleans[num]) {
                        return false;
                    } else {
                        booleans[num] = true;
                    }
                }
            }
        }
        // check each column
        for (int column = 0; column < 9; column++) {
            boolean[] booleans = new boolean[9];
            for (int idx = 0; idx < 9; idx++) {
                char c = board[idx][column];
                if (c != '.') {
                    int num = c - '1';
                    if (booleans[num]) {
                        return false;
                    } else {
                        booleans[num] = true;
                    }
                }
            }
        }
        // check 3*3
        for (int box = 0; box < 9; box++) {
            boolean[] booleans = new boolean[9];
            for (int row = 0; row < 3; row++) {
                for (int cloumn = 0; cloumn < 3; cloumn++) {
                    char c = board[row + 3 * (box / 3)][cloumn + 3 * (box % 3)];
                    System.out.println(c);
                    if (c != '.') {
                        int num = c - '1';
                        if (booleans[num]) {
                            return false;
                        } else {
                            booleans[num] = true;
                        }
                    }
                }

            }
        }
        return true;
    }

}
