package leetcode;

/**
 * @author luna@mac
 * @className leetcode.CanJump.java
 * @description TODO
 * 给定一个非负整数数组，你最初位于数组的第一个位置。
 * <p>
 * 数组中的每个元素代表你在该位置可以跳跃的最大长度。
 * <p>
 * 判断你是否能够到达最后一个位置。
 * <p>
 * 示例 1:
 * <p>
 * 输入: [2,3,1,1,4]
 * 输出: true
 * 解释: 我们可以先跳 1 步，从位置 0 到达 位置 1, 然后再从位置 1 跳 3 步到达最后一个位置。
 * 示例 2:
 * <p>
 * 输入: [3,2,1,0,4]
 * 输出: false
 * 解释: 无论怎样，你总会到达索引为 3 的位置。但该位置的最大跳跃长度是 0 ， 所以你永远不可能到达最后一个位置。
 * @createTime 2021年01月11日 22:01:00
 */
public class CanJump {

    public boolean canJump1(int[] nums) {
        if (nums == null || nums.length < 0) {
            return false;
        }
        int n = nums.length;
        int reach = 0;
        for (int i = 0; i < n; i++) {
            if (i > reach) {
                return false;
            }
            reach = Math.max(i + nums[i], reach);
        }
        return true;
    }

    public boolean canJump2(int[] nums) {
        if (nums == null || nums.length < 0) {
            return false;
        }
        int n = nums.length;
        int reach = 0;
        for (int i = 0; i <= reach && reach < n - 1; ++i ) {
            reach = Math.max(i + nums[i], reach);
        }
        return reach >= n - 1;
    }


    public boolean canJump3(int[] nums){
        int n = nums.length;
        int rightmost = 0;
        for (int i = 0; i < nums.length; i++) {
            if (i <= rightmost){
                rightmost = Math.max(i + nums[i],rightmost);
                if (rightmost >= n - 1 ){
                    return true;
                }
            }
        }
        return false;
    }
}
