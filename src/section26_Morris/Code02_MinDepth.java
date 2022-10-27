package section26_Morris;

/**
 * @Author: duccio
 * @Date: 12, 05, 2022
 * @Description: The minimum depth of a binary tree is the number of nodes along the shortest path from the root node
 *      down to the nearest leaf node.
 *      https://leetcode.com/problems/minimum-depth-of-binary-tree/
 * @Note:   Ver1. Binary tree DP: base case is leaf node.
 *          Ver2. Morris: a) track the current level for each node, increase if first visit, and decrease the height
 *                           of corresponding left subtree if second visit.
 *                        b) when second visit, check leaf node, and get the current min depth.
 *                        c) finally, check the right boundary of entire tree.
 */
public class Code02_MinDepth {

    public static class TreeNode {
        public int val;
        public TreeNode left;
        public TreeNode right;

        public TreeNode(int x) {
            val = x;
        }
    }

    public static int minDepth1(TreeNode root) {
        if (root == null) {
            return 0;
        }
        return process(root);
    }

    private static int process(TreeNode node) {
        if (node.left == null && node.right == null) {
            return 1;
        }
        int leftMin = Integer.MAX_VALUE;
        if (node.left != null) {
            leftMin = process(node.left);
        }
        int rightMin = Integer.MAX_VALUE;
        if (node.right != null) {
            rightMin = process(node.right);
        }
        return 1 + Math.min(leftMin, rightMin);
    }

    public static int minDepth2(TreeNode root) {
        if (root == null) {
            return 0;
        }
        TreeNode cur = root;
        TreeNode rightMost = null;
        int level = 0;
        int min = Integer.MAX_VALUE;
        while (cur != null) {
            rightMost = cur.left;
            if (rightMost != null) {
                int rightLevel = 1;
                while (rightMost.right != null && rightMost.right != cur) {
                    rightLevel++;
                    rightMost = rightMost.right;
                }
                if (rightMost.right == null) {  // visit-twice nodes, first time
                    level++;
                    rightMost.right = cur;
                    cur = cur.left;
                    continue;
                } else {  // visit-twice nodes, second time
                    if (rightMost.left == null) {
                        min = Math.min(min, level);
                    }
                    level -= rightLevel;
                    rightMost.right = null;
                }
            } else {  // visit-once nodes
                level++;
            }
            cur = cur.right;
        }
        // finally check the right boundary of entire tree
        int finalRight = 1;
        cur = root;
        while (cur.right != null) {
            finalRight++;
            cur = cur.right;
        }
        if (cur.left == null) {
            min = Math.min(min, finalRight);
        }
        return min;
    }

}
