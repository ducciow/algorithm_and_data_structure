package primary.class07;

/**
 * @Author: duccio
 * @Date: 24, 03, 2022
 * @Description: Check if a given binary tree is a binary search tree
 *      https://leetcode.com/problems/validate-binary-search-tree/
 * @Note:   "Binary search tree": for each subtree, left < root < right
 *          Sol1. Its in-order traversal is strictly increasing
 *          Sol2. Recursion <---
 */
public class Code30_BinarySearchTree {

    public static class TreeNode {
        public int val;
        public TreeNode left;
        public TreeNode right;

        TreeNode(int val) {
            this.val = val;
        }
    }

    public static class Info {
        boolean isBST;
        int max;
        int min;

        public Info(boolean isBST, int max, int min) {
            this.isBST = isBST;
            this.max = max;
            this.min = min;
        }
    }

    public boolean isValidBST(TreeNode root) {
        return process(root).isBST;
    }

    public static Info process(TreeNode root) {
        if (root == null) {
            return null;
        }
        Info leftInfo = process(root.left);
        Info rightInfo = process(root.right);
        boolean isBST = true;
        if (leftInfo != null && (!leftInfo.isBST || leftInfo.max >= root.val)) {
            isBST = false;
        }
        if (rightInfo != null && (!rightInfo.isBST || rightInfo.min <= root.val)) {
            isBST = false;
        }
        int max = root.val;
        int min = root.val;
        if (leftInfo != null) {
            max = Math.max(max, leftInfo.max);
            min = Math.min(min, leftInfo.min);
        }
        if (rightInfo != null) {
            max = Math.max(max, rightInfo.max);
            min = Math.min(min, rightInfo.min);
        }
        return new Info(isBST, max, min);
    }
}

