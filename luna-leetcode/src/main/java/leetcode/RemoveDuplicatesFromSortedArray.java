package leetcode;

/**
 * @author Luna@win10
 * @date 2020/5/28 20:47
 */
public class RemoveDuplicatesFromSortedArray {

    /**
     * 移除数组中的重复元素
     * @param nums
     * @return
     */
    public static int removeDuplicates(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int count = 1;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i - 1] != nums[i]) {
                nums[count++] = nums[i];
            }
        }
        return count;
    }

    public static void main(String[] args) {
        int i = removeDuplicates(new int[]{1, 1, 1, 2, 2, 3, 4});
        System.out.println(i);
    }
}
