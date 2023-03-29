package leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author luna@mac
 * @className leetcode.SpiraOrder.java
 * @description TODO
 * @createTime 2021年01月10日 21:31:00
 */
public class SpiraOrder {

    public List<Integer> spiralOrder(int[][] matrix) {
        ArrayList<Integer> orders = new ArrayList<>();
        if (matrix == null || matrix.length < 0 || matrix[0].length < 0) {
            return orders;
        }

        int rows = matrix.length;
        int columms = matrix[0].length;

        boolean visited[][] = new boolean[rows][columms];

        int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        int direction = 0;
        int total = rows * columms;
        int row = 0, column = 0;
        for (int i = 0; i < total; i++) {
            orders.add(matrix[row][column]);
            int nextRow = row + directions[direction][0];
            int nextColumn = column + directions[direction][1];
            visited[row][column] = true;
            if (nextColumn < 0 || nextColumn >= columms || nextRow < 0 || nextRow >= rows || visited[nextRow][nextColumn]) {
                direction = (direction + 1) % 4;
            }
            row += directions[direction][0];
            column += directions[direction][1];
        }
        return orders;

    }
}
