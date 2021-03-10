package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Package: PACKAGE_NAME
 * @ClassName: leetcode.CombinationSumII
 * @Author: luna
 * @CreateTime: 2020/6/16 23:41
 * @Description:
 */
public class CombinationSumII {

    /**
     * 给定一个数组 candidates 和一个目标数 target ，找出 candidates 中所有可以使数字和为 target 的组合。
     * <p>
     * candidates 中的每个数字在每个组合中只能使用一次。
     * <p>
     * 说明：
     * <p>
     * 所有数字（包括目标数）都是正整数。
     * 解集不能包含重复的组合。 
     * 示例 1:
     * <p>
     * 输入: candidates = [10,1,2,7,6,1,5], target = 8,
     * 所求解集为:
     * [
     * [1, 7],
     * [1, 2, 5],
     * [2, 6],
     * [1, 1, 6]
     * ]
     * 示例 2:
     * <p>
     * 输入: candidates = [2,5,2,1,2], target = 5,
     * 所求解集为:
     * [
     *   [1,2,2],
     *   [5]
     * ]
     *
     * @param candidates
     * @param target
     * @return
     */
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        List<List<Integer>> res = new ArrayList<>();
        if (candidates == null || candidates.length == 0) {
            return res;
        }
        Arrays.sort(candidates);
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
            if (i != start && candidates[i] == candidates[i - 1]) {
                continue;
            }
            // 每次将数放入
            list.add(candidates[i]);
            // 调用减去数剩下的继续作为targe
            heler(res, list, candidates, targer - candidates[i], i + 1);
            list.remove(list.size() - 1);
        }
    }
}
