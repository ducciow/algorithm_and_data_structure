package primary.class07;

/**
 * @Author: duccio
 * @Date: 24, 03, 2022
 * @Description: Given the root of a binary tree and an integer targetSum, return true if the tree has a
 *      root-to-leaf path such that adding up all the values along the path equals targetSum.
 *      https://leetcode.com/problems/path-sum/
 * @Note:   1. Use a global variable to track status
 *          2. Base case is leaf node, not null node. So need to check if child trees are null when recursively call
 */
public class Code31_PathSum {

    public static class TreeNode {
        public int val;
        public TreeNode left;
        public TreeNode right;

        TreeNode(int val) {
            this.val = val;
        }
    }

    static boolean met = false;

    public boolean hasPathSum(TreeNode root, int targetSum) {
        if (root == null) {
            return false;
        }
        met = false;
        process(root, targetSum, 0);
        return met;
    }

    public static void process(TreeNode root, int target, int curSum) {
        if (root.left == null && root.right == null) {
            if (curSum + root.val == target) {
                met = true;
            }
            return;
        }
        if (root.left != null) {
            process(root.left, target, curSum + root.val);
        }
        if (root.right != null) {
            process(root.right, target, curSum + root.val);
        }
    }
}
