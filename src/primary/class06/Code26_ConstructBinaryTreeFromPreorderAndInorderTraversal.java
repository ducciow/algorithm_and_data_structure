package primary.class06;

import java.util.HashMap;

/**
 * @Author: duccio
 * @Date: 23, 03, 2022
 * @Description: Construct a binary tree from its pre-order and in-order traversal, where there is no repeated values
 *      https://leetcode.com/problems/construct-binary-tree-from-preorder-and-inorder-traversal
 * @Note:
 */
public class Code26_ConstructBinaryTreeFromPreorderAndInorderTraversal {

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int val) {
            this.val = val;
        }
    }

    public static TreeNode buildTree(int[] preorder, int[] inorder) {
        if (preorder == null || inorder == null) {
            return null;
        }
        if (preorder.length == 0 || inorder.length == 0) {
            return null;
        }

        // Map for quick indexing
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < inorder.length; i++) {
            map.put(inorder[i], i);
        }

        int N = preorder.length;
        return process(preorder, 0, N - 1, inorder, 0, N - 1, map);
    }

    // process on preorder[L1 : R1 + 1] and inorder[L2 : R2 + 1]
    public static TreeNode process(int[] preorder, int L1, int R1, int[] inorder, int L2, int R2,
                                   HashMap<Integer, Integer> map) {
        if (L1 > R1) {
            return null;
        }

        TreeNode root = new TreeNode(preorder[L1]);

        if (L1 == R1) {
            return root;
        }

        int rootIdxOfIn = map.get(root.val);
        int leftSize = rootIdxOfIn - L2;

        root.left = process(preorder, L1 + 1, L1 + leftSize, inorder, L2, rootIdxOfIn - 1, map);
        root.right = process(preorder, L1 + leftSize + 1, R1, inorder, rootIdxOfIn + 1, R2, map);

        return root;
    }
}
