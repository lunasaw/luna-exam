package leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * @Package: PACKAGE_NAME
 * @ClassName: leetcode.Permutations
 * @Author: luna
 * @CreateTime: 2020/6/25 23:24
 * @Description:
 */
public class Permutations {

    /**
     * 给定一个 没有重复 数字的序列，返回其所有可能的全排列。
     * <p>
     * 示例:
     * <p>
     * 输入: [1,2,3]
     * 输出:
     * [
     * [1,2,3],
     * [1,3,2],
     * [2,1,3],
     * [2,3,1],
     * [3,1,2],
     * [3,2,1]
     * ]
     *
     * @param nums
     * @return
     */
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        if (nums == null || nums.length == 0) {
            return res;
        }
        helper(nums, new ArrayList<Integer>(), res);
        return res;
    }

    private void helper(int[] nums, ArrayList<Integer> arrayList, List<List<Integer>> res) {
        if (arrayList.size() == nums.length) {
            res.add(new ArrayList<>(arrayList));
            return;
        }
        for (int i = 0; i < nums.length; i++) {
            if (arrayList.contains(nums[i])) {
                continue;
            }
            arrayList.add(nums[i]);
            helper(nums, arrayList, res);
            arrayList.remove(arrayList.size() - 1);
        }
    }
}
