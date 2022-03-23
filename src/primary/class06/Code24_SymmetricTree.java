package primary.class06;

/**
 * @Author: duccio
 * @Date: 23, 03, 2022
 * @Description: Check if a given binary tree is symmetric
 *      https://leetcode.com/problems/symmetric-tree
 * @Note:
 */
public class Code24_SymmetricTree {

    public static class TreeNode {
        public int val;
        public TreeNode left;
        public TreeNode right;
    }

    public static boolean isSymmetric(TreeNode root) {
        return process(root, root);
    }

    public static boolean process(TreeNode r1, TreeNode r2) {
        if (r1 == null ^ r2 == null) {
            return false;
        }
        if (r1 == null && r2 == null) {
            return true;
        }
        return r1.val == r2.val && process(r1.left, r2.right) && process(r1.right, r2.left);
    }
}
