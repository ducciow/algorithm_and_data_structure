package section12_BinaryTreeDP;

import java.util.ArrayList;

/**
 * @Author: duccio
 * @Date: 12, 04, 2022
 * @Description: Check if a given binary tree is a binary search tree, ie., for each subtree, left < root < right.
 *       https://leetcode.com/problems/validate-binary-search-tree/
 * @Note:   Sol1. Its in-order traversal is strictly increasing.
 *          Sol2. DP: 1. Info: isBST, max, min.
 *                    2. Check if left subtree is BST, right subtree is BST, and left.max < node.val < right.min.
 *                    3. Null node returns null, and take care in upstream.
 */
public class Code03_IsBST {

    public static class TreeNode {
        public int val;
        public TreeNode left;
        public TreeNode right;

        TreeNode(int val) {
            this.val = val;
        }
    }

    public static boolean isValidBST1(TreeNode root) {
        if (root == null) {
            return true;
        }
        ArrayList<Integer> inTraversal = new ArrayList<>();
        process1(root, inTraversal);
        for (int i = 0; i < inTraversal.size() - 1; i++) {
            if (inTraversal.get(i) >= inTraversal.get(i + 1)) {
                return false;
            }
        }
        return true;
    }

    public static void process1(TreeNode root, ArrayList<Integer> inTraversal) {
        if (root == null) {
            return;
        }
        process1(root.left, inTraversal);
        inTraversal.add(root.val);
        process1(root.right, inTraversal);
    }

    public static boolean isValidBST2(TreeNode root) {
        if (root == null) {
            return true;
        }
        return process2(root).isBST;
    }

    public static class Info {
        boolean isBST;
        int max;
        int min;

        public Info(boolean is, int a, int i) {
            isBST = is;
            max = a;
            min = i;
        }
    }

    public static Info process2(TreeNode node) {
        // base case
        if (node == null) {
            return null;
        }
        // collect info
        Info leftInfo = process2(node.left);
        Info rightInfo = process2(node.right);
        // process info
        int max = node.val;
        int min = node.val;
        if (leftInfo != null) {
            max = Math.max(max, leftInfo.max);
            min = Math.min(min, leftInfo.min);
        }
        if (rightInfo != null) {
            max = Math.max(max, rightInfo.max);
            min = Math.min(min, rightInfo.min);
        }
        boolean isBST = true;
        if (leftInfo != null && (!leftInfo.isBST || leftInfo.max >= node.val)) {
            isBST = false;
        }
        if (rightInfo != null && (!rightInfo.isBST || rightInfo.min <= node.val)) {
            isBST = false;
        }

        return new Info(isBST, max, min);
    }


    public static TreeNode genRandBT(int maxL, int maxV) {
        return genRandBTProcess(1, maxL, maxV);
    }

    private static TreeNode genRandBTProcess(int i, int maxL, int maxV) {
        if (i > maxL || Math.random() < 0.5) {
            return null;
        }
        TreeNode node = new TreeNode((int) (Math.random() * (maxV + 1)));
        node.left = genRandBTProcess(i + 1, maxL, maxV);
        node.right = genRandBTProcess(i + 1, maxL, maxV);
        return node;
    }

    public static void main(String[] args) {
        int numTest = 10000;
        int maxL = 4;
        int maxV = 100;
        System.out.println("Test begin...");
        for (int i = 0; i < numTest; i++) {
            TreeNode root = genRandBT(maxL, maxV);
            if (isValidBST1(root) != isValidBST2(root)) {
                System.out.println("Failed");
                return;
            }
        }
        System.out.println("Test passed!");
    }
}
