package leetcode2023;

public class CanJump_55 {

    public static boolean canJump(int[] nums) {
        if (nums.length == 1) {
            return true;
        }
        int maxIndex = 0;
        for (int i = 0; i < nums.length - 1; i++) {
            if (nums[i] <= 0 && maxIndex - i <= nums[i]) {
                return false;
            }
            maxIndex = Math.max(nums[i] + i, maxIndex);
            if (maxIndex >= nums.length - 1) {
                return true;
            }

        }

        return false;
    }

    public static void main(String[] args) {
        int[] nums = new int[]{1, 0, 1, 0};
        System.out.println(canJump(nums));
    }
}
