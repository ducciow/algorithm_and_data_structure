package systematic.section12_BinaryTreeDP;

/**
 * @Author: duccio
 * @Date: 12, 04, 2022
 * @Description: Check if a given binary tree is balanced, ie., for each subtree, |height(left) - height(right)| <= 1.
 *      https://leetcode.com/problems/balanced-binary-tree
 * @Note:   1. Define Info: isBalanced, height
 *          2. Check if left subtree is balanced, right subtree is balanced, and |height(left) - height(right)| <= 1.
 */
public class Code02_IsBalanced {

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        public TreeNode(int v) {
            val = v;
        }
    }

    public static boolean isBalanced(TreeNode root) {
        if (root == null) {
            return true;
        }
        return process(root).isBalanced;
    }

    public static class Info {
        boolean isBalanced;
        int height;

        public Info(boolean ib, int h) {
            isBalanced = ib;
            height = h;
        }
    }

    public static Info process(TreeNode node) {
        if (node == null) {
            return new Info(true, 0);
        }
        Info leftInfo = process(node.left);
        Info rightInfo = process(node.right);

        int height = Math.max(leftInfo.height, rightInfo.height) + 1;

        boolean isBalanced = true;
        if (!leftInfo.isBalanced || !rightInfo.isBalanced || Math.abs(leftInfo.height - rightInfo.height) > 1) {
            isBalanced = false;
        }

        return new Info(isBalanced, height);
    }

}
