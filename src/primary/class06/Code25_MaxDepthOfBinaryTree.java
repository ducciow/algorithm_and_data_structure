package primary.class06;

/**
 * @Author: duccio
 * @Date: 23, 03, 2022
 * @Description: Return the maximum depth of a binary tree
 *      https://leetcode.com/problems/maximum-depth-of-binary-tree
 * @Note:
 */
public class Code25_MaxDepthOfBinaryTree {

    public static class TreeNode {
        public int val;
        public TreeNode left;
        public TreeNode right;
    }

    public static int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        return Math.max(maxDepth(root.left), maxDepth(root.right)) + 1;
    }
}
