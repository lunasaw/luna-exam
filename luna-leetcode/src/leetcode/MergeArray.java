package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

/**
 * @author luna@mac
 * @className leetcode.MergeArray.java
 * @description TODO
 * @createTime 2021年01月12日 22:35:00
 */
public class MergeArray {

    public int[][] merge(int[][] intervals) {
        if(intervals.length ==0){
            return new int[0][2];
        }

        Arrays.sort(intervals, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[1] - o2[1];
            }
        });

        ArrayList<int[]> merged = new ArrayList<>();

        for (int i = 0; i < intervals.length; i++) {
            int L = intervals[i][0];
            int R = intervals[i][1];
            if (merged.size()==0 || merged.get(merged.size()-1)[1]<L){
                merged.add(new int[]{L,R});
            }else {
                merged.get(merged.size()-1)[1] = Math.max(merged.get(merged.size()-1)[1],R);
            }
        }

        return merged.toArray(new int[merged.size()][]);
    }
}
