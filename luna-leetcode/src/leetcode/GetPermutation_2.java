package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author luna@mac
 * @className leetcode.GetPermutation_2.java
 * @description TODO
 * 给出集合 [1,2,3,...,n]，其所有元素共有 n! 种排列。
 *
 * 按大小顺序列出所有排列情况，并一一标记，当 n = 3 时, 所有排列如下：
 *
 * "123"
 * "132"
 * "213"
 * "231"
 * "312"
 * "321"
 * 给定 n 和 k，返回第 k 个排列。
 *
 * @createTime 2021年01月16日 23:02:00
 */
public class GetPermutation_2 {

    public List<List<Integer>> res = new ArrayList<>();

    public int count = 0;

    public int locate = 0;


    /*
	优化思路主要是排除前面不可能的情况，剪枝
	然后从应该开始的地方开始
	比如
	1 2 3 4  #1
	1 2 4 3  #2
	1 3 2 4  #3
	1 3 4 2  #4
	1 4 2 3  #5
	1 4 3 2  #6
	//------
	2 1 3 4  #7
	2 1 4 3  #8
	2 3 1 4  #9
	2 3 4 1  #10
	2 4 1 3  #11
	2 4 3 1  #12
	//----
	3 1 2 4

	1 2 3 4   N = 4 K 9
*/
    public String getPermutation(int n, int k) {

        Integer[] sequence = new Integer[n];
        for (int i = 0; i < n; i++) {
            sequence[i] = i + 1;
        }

        if (k == 1) {
            return toStr(Arrays.asList(sequence));
        }

        // 每组的个数
        int arrayNum = 1;

        for (int i = n - 1; i >= 1; i--) {
            arrayNum *= i;   // n =4  n！=6
        }

        // n!

        while (locate < k) {
            locate += arrayNum; // k=9 locate =12
        }

        // 12/6=2
        locate /= arrayNum;
        // 确定那个数字开始

        // (locate -1) * arrayNum -1排除的个数
        // 9 - （2 - 1） * 6 -1 = 2
        count = k - (locate - 1) * arrayNum - 1;

        ArrayList<Integer> path = new ArrayList<>();
        path.add(sequence[locate - 1]); // 确定第一个数
        backTrack(sequence, path);
        return toStr(res.get(0));
    }

    private void backTrack(Integer[] sequence, List<Integer> path) {
        if (path.size() == sequence.length) {
            if (count == 0) {
                res.add(new ArrayList<>(path));
            } else {
                count--;
            }
            return;
        }

        for (int i = 0; i < sequence.length; i++) {
            if (!path.contains(sequence[i])) {
                path.add(sequence[i]);
                backTrack(sequence, path);
                path.remove(path.size() - 1);
            }
        }
    }

    private static String toStr(int[] sequence) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < sequence.length; i++) {
            sb.append(sequence[i]);
        }
        return sb.toString();
    }

    private static String toStr(List<Integer> path) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < path.size(); i++) {
            sb.append(path.get(i));
        }
        return sb.toString();
    }
}
