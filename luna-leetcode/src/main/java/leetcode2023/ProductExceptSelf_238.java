package leetcode2023;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ProductExceptSelf_238 {

    static Map<String, Integer> map = new HashMap<>();

    public static int[] productExceptSelf(int[] nums) {
        int[] L = new int[nums.length];
        int[] R = new int[nums.length];
        int[] res = new int[nums.length];

        for (int i = 0; i < nums.length; i++) {
            if (i <= 0) {
                res[i] = 1;
                continue;
            }
            res[i] = nums[i - 1] * res[i - 1];
        }

        for (int i = nums.length - 1; i >= 0; i--) {
            if (i == nums.length - 1) {
                R[nums.length - 1] = 1;
                continue;
            }
            R[i] = nums[i + 1] * R[i + 1];
        }

        for (int i = 0; i < nums.length; i++) {
            res[i] = res[i] * R[i];
        }

        return res;
    }

    public static int[] productExceptSelf2(int[] nums) {
        int[] res = new int[nums.length];

        for (int i = 0; i < nums.length; i++) {
            if (i <= 0) {
                res[i] = 1;
                continue;
            }
            res[i] = nums[i - 1] * res[i - 1];
        }

        int temp = 1;
        for (int i = nums.length - 2; i >= 0; i--) {
            temp = nums[i + 1] * temp;
            res[i] = res[i] * temp;
        }

        return res;
    }

    public static void main(String[] args) {
        int[] nums = new int[]{1, 2, 3, 4};
        int[] ints = productExceptSelf2(nums);
        System.out.println(Arrays.toString(ints));
    }
}
