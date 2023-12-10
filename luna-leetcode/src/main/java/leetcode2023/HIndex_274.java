package leetcode2023;

import java.lang.reflect.Array;
import java.util.Arrays;

public class HIndex_274 {
    public static int hIndex(int[] citations) {
        Arrays.sort(citations);
        int h = 0;
        for (int i = citations.length - 1; i >= 0; i--) {
            if (citations[i] > h) {
                h++;
            }
        }
        return h;
    }

    public static int hIndex2(int[] citations) {
        int mid = 0;
        int left = 0;
        int right = citations.length;
        // 个数
        int cnt = 0;

        while (left < right) {

            mid = (left + right + 1) >> 1;
            cnt = 0;

            for (int i = 0; i < citations.length; i++) {
                if (citations[i] >= mid) {
                    cnt++;
                }
            }

            if (cnt >= mid) {
                left = mid;
            } else {
                right = mid - 1;
            }

        }

        return left;
    }

    public static void main(String[] args) {
        int[] nums = new int[]{1, 2, 3, 4, 5};
        System.out.println(hIndex2(nums));
    }
}
