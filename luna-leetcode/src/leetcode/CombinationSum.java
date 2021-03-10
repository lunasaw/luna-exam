package leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * @Package: PACKAGE_NAME
 * @ClassName: leetcode.CombinationSum
 * @Author: luna
 * @CreateTime: 2020/6/15 21:55
 * @Description:
 */
public class CombinationSum {

    /**
     * 给定一个无重复元素的数组 candidates 和一个目标数 target ，找出 candidates 中所有可以使数字和为 target 的组合。
     *
     * candidates 中的数字可以无限制重复被选取。
     *
     * 说明：
     *
     * 所有数字（包括 target）都是正整数。
     * 解集不能包含重复的组合。 
     * 示例 1:
     *
     * 输入: candidates = [2,3,6,7], target = 7,
     * 所求解集为:
     * [
     * [7],
     * [2,2,3]
     * ]
     * 示例 2:
     *
     * 输入: candidates = [2,3,5], target = 8,
     * 所求解集为:
     * [
     *   [2,2,2,2],
     *   [2,3,3],
     *   [3,5]
     * ]
     * 
     * @param candidates
     * @param target
     * @return
     */
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> res = new ArrayList<>();
        if (candidates == null || candidates.length == 0) {
            return res;
        }
        heler(res, new ArrayList<>(), candidates, target, 0);
        return res;
    }

    private void heler(List<List<Integer>> res, List<Integer> list, int[] candidates, int targer, int start) {
        if (targer < 0) {
            return;
        }
        if (targer == 0) {
            res.add(new ArrayList<>(list));
            return;
        }

        for (int i = start; i < candidates.length; i++) {
            // 每次将数放入
            list.add(candidates[i]);
            // 调用减去数剩下的继续作为targe
            heler(res, list, candidates, targer - candidates[i], i);
            list.remove(list.size() - 1);
        }
    }
}
