package section26_Morris;

/**
 * @Author: duccio
 * @Date: 12, 05, 2022
 * @Description: The minimum depth of a binary tree is the number of nodes along the shortest path from the root node
 *      down to the nearest leaf node. A leaf is a node with no children.
 *      https://leetcode.com/problems/minimum-depth-of-binary-tree/
 * @Note:   Ver1. Binary tree DP: base case is leaf node rather than null node.
 *          Ver2. Morris:
 *              - Needs to keep tracking the level of each node, which is hard for right nodes due to redirection
 *              - Needs to identify leaf nodes, which can only be done when visit-twice nodes are visited the second
 *                time by checking if their rightmost node is a leaf.
 *              ======
 *              - So: a) track the current level for each node, increase when first visit, and decrease when second
 *                       visit by the value of the height of the corresponding right subtree.
 *                    b) when second visit, check if the rightmost node is a leaf node, and get the current min depth.
 *                    c) finally, check the height of the right boundary of entire tree and see if it is a leaf node.
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
        // check leaf node
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
                int rightLevel = 1;  // track the level of right subtree
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
                    if (rightMost.left == null) {  // check if its rightMost is a leaf node
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
        cur = root;
        level = 1;
        while (cur.right != null) {
            level++;
            cur = cur.right;
        }
        if (cur.left == null) {  // check if it is a leaf node
            min = Math.min(min, level);
        }
        return min;
    }

}
