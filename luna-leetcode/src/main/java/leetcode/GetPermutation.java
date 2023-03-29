package leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author luna@mac
 * @className leetcode.GetPermutation.java
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
 * @createTime 2021年01月16日 22:40:00
 */
public class GetPermutation {

    public List<List<Integer>> res = new ArrayList<>();

    public int count = 0;

    public String getPermutation(int n, int k) {
        int[] sequence = new int[n];
        for (int i = 0; i < n; i++) {
            sequence[i] = i + 1;
        }

        count = k;

        ArrayList<Integer> path = new ArrayList<>();

        backTrack(sequence, path);

        return toStr(res.get(0));
    }

    public static void main(String[] args) {
        String permutation = new GetPermutation().getPermutation(4, 9);
        System.out.println(permutation);
    }



    private void backTrack(int[] sequence, List<Integer> path) {
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

    private static String toStr(List<Integer> path) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < path.size(); i++) {
            sb.append(path.get(i));
        }
        return sb.toString();
    }
}
