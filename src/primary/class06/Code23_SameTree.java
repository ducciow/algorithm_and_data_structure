package primary.class06;

/**
 * @Author: duccio
 * @Date: 23, 03, 2022
 * @Description: Check if two binary trees are the same
 *      https://leetcode.com/problems/same-tree
 * @Note:
 */
public class Code23_SameTree {

    public static class TreeNode {
        public int val;
        public TreeNode left;
        public TreeNode right;
    }

    public static boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null ^ q == null) {
            return false;
        } else if (p == null && q == null) {
            return true;
        }
        return p.val == q.val && isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
    }
}
