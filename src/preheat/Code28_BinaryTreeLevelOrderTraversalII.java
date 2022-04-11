package preheat;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @Author: duccio
 * @Date: 24, 03, 2022
 * @Description: Given the root of a binary tree, return the bottom-up level order traversal of its nodes' values.
 *      i.e., from left to right, level by level from leaf to root.
 *      https://leetcode.com/problems/binary-tree-level-order-traversal-ii
 * @Note:
 */
public class Code28_BinaryTreeLevelOrderTraversalII {

    public static class TreeNode {
        public int val;
        public TreeNode left;
        public TreeNode right;

        TreeNode(int val) {
            this.val = val;
        }
    }

    public List<List<Integer>> levelOrderBottom(TreeNode root) {
        List<List<Integer>> ret = new LinkedList<>();
        if (root == null) {
            return ret;
        }
        Queue<TreeNode> q = new LinkedList<>();
        q.add(root);
        while (!q.isEmpty()) {
            List<Integer> level = new LinkedList<>();
            int size = q.size();
            for (int i = 0; i < size; i++) {
                TreeNode cur = q.poll();
                level.add(cur.val);
                if (cur.left != null) {
                    q.add(cur.left);
                }
                if (cur.right != null) {
                    q.add(cur.right);
                }
            }
            ret.add(0, level);
        }
        return ret;
    }

}
