package leetcode;

import java.util.ArrayList;

/**
 * @author luna@mac
 * @className leetcode.InsertArray.java
 * @description TODO 给出一个无重叠的 ，按照区间起始端点排序的区间列表。
 *
 * 在列表中插入一个新的区间，你需要确保列表中的区间仍然有序且不重叠（如果有必要的话，可以合并区间）。
 *
 * 输入：intervals = [[1,3],[6,9]], newInterval = [2,5]
 * 输出：[[1,5],[6,9]]
 *
 * 输入：intervals = [[1,2],[3,5],[6,7],[8,10],[12,16]], newInterval = [4,8]
 * 输出：[[1,2],[3,10],[12,16]]
 * 解释：这是因为新的区间 [4,8] 与 [3,5],[6,7],[8,10] 重叠。
 *
 * @createTime 2021年01月13日 21:05:00
 */
public class InsertArray {

    public int[][] insert(int[][] intervals, int[] newInterval) {
        int left = newInterval[0];
        int right = newInterval[1];

        boolean placed = false;

        ArrayList<int[]> ints = new ArrayList<>();

        for (int[] interval : intervals) {
            if (interval[0] > right) {
                if (!placed) {
                    ints.add(new int[]{left, right});
                    placed = true;
                }
                ints.add(interval);
            } else if (interval[1] < left) {
                ints.add(interval);
            } else {
                left = Math.min(left, interval[0]);
                right = Math.max(right, interval[1]);
            }

        }

        if (!placed) {
            ints.add(new int[]{left, right});
        }

        int ans[][] = new int[ints.size()][2];

        for (int i = 0; i < ints.size(); i++) {
            ans[i] = ints.get(i);
        }

        return ans;
    }
}
