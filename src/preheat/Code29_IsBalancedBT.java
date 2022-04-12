package preheat;

/**
 * @Author: duccio
 * @Date: 24, 03, 2022
 * @Description: Check if a given binary tree is balanced, ie., for each subtree, |height(left) - height(right)| <= 1.
 *      https://leetcode.com/problems/balanced-binary-tree
 * @Note:
 */
public class Code29_IsBalancedBT {
    public static class TreeNode {
        public int val;
        public TreeNode left;
        public TreeNode right;

        TreeNode(int val) {
            this.val = val;
        }
    }

    public static boolean isBalanced(TreeNode root) {
        return process(root).isBalanced;
    }

    public static class Info {
        boolean isBalanced;
        int height;

        public Info(boolean isB, int h) {
            isBalanced = isB;
            height = h;
        }
    }

    public static Info process(TreeNode root) {
        if (root == null) {
            return new Info(true, 0);
        }

        Info leftInfo = process(root.left);
        Info rightInfo = process(root.right);

        int diffChildHeight = Math.abs(leftInfo.height - rightInfo.height);
        boolean isBalanced = leftInfo.isBalanced && rightInfo.isBalanced && diffChildHeight < 2;
        int height = Math.max(leftInfo.height, rightInfo.height) + 1;

        return new Info(isBalanced, height);
    }

}
