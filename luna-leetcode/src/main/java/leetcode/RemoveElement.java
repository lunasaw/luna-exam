package leetcode;

/**
 * @author Luna@win10
 * @date 2020/5/29 23:01
 */
public class RemoveElement {

    /**
     * 移除目标元素
     * 
     * @param nums
     * @param val
     * @return
     */
    public int removeElement(int[] nums, int val) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int count = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != val) {
                nums[count++] = nums[i];
            }
        }
        return count;
    }

}
