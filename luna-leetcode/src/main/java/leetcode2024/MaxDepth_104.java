package leetcode2024;

/**
 * 二叉树的 最大深度 是指从根节点到最远叶子节点的最长路径上的节点数。
 * Definition for a binary tree node.
 * public class TreeNode {
 * int val;
 * TreeNode left;
 * TreeNode right;
 * TreeNode() {}
 * TreeNode(int val) { this.val = val; }
 * TreeNode(int val, TreeNode left, TreeNode right) {
 * this.val = val;
 * this.left = left;
 * this.right = right;
 * }
 * }
 *
 * @author luna
 * @date 2024/1/10
 */
public class MaxDepth_104 {

    public static void main(String[] args) {
        // -8,-6,7,6,null,null,null,null,5
        int i = new MaxDepth_104().maxDepth(new TreeNode(-8, new TreeNode(-6, new TreeNode(6, null, new TreeNode(5)), null),
                new TreeNode(7)));
        System.out.println(i);
    }

    public int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int left = maxDepth(root.left) + 1;
        int right = maxDepth(root.right) + 1;
        return Math.max(left, right);
    }

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }
}
