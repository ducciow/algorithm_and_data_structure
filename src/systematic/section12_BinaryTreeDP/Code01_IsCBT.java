package systematic.section12_BinaryTreeDP;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @Author: duccio
 * @Date: 12, 04, 2022
 * @Description: Given a binary tree, check if it is complete, ie., either it is full or going to be full.
 * @Note:   Sol1. Non-recursive: 1. Level traversal.
 *                               2. Any node having no left child cannot have right child.
 *                               3. When meet a node that does not have both children, all successor nodes must be
 *                                  leaf nodes.
 *          ======
 *          Sol2. BTDP: 1. Info: isComplete, isFull, height.
 *                      2. Four cases that a subtree is complete:
 *                          a) left is full, right is full, and left height == right height.
 *                          b) left is complete, right is full, and left height == right height + 1.
 *                          c) left is full, right is full, and left height == right height + 1.
 *                          d) left is full, right is complete, and left height == right height.
 */
public class Code01_IsCBT {

    public static void main(String[] args) {
        validate();
    }

    public static class Node {
        int val;
        Node left;
        Node right;

        public Node(int v) {
            val = v;
        }
    }

    public static boolean isCBT1(Node root) {
        if (root == null) {
            return true;
        }
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        boolean leaf = false;
        while (!queue.isEmpty()) {
            Node cur = queue.poll();
            if (leaf && (cur.left != null || cur.right != null)) {
                return false;
            }
            if (cur.left == null && cur.right != null) {
                return false;
            }
            if (cur.left == null || cur.right == null) {
                leaf = true;
            }
            if (cur.left != null) {
                queue.add(cur.left);
            }
            if (cur.right != null) {
                queue.add(cur.right);
            }
        }
        return true;
    }

    public static boolean isCBT2(Node root) {
        if (root == null) {
            return true;
        }
        return process(root).isComplete;
    }

    public static class Info {
        boolean isComplete;
        boolean isFull;
        int height;

        public Info(boolean c, boolean f, int h) {
            isComplete = c;
            isFull = f;
            height = h;
        }
    }

    public static Info process(Node node) {
        if (node == null) {
            return new Info(true, true, 0);
        }
        Info leftInfo = process(node.left);
        Info rightInfo = process(node.right);

        int height = Math.max(leftInfo.height, rightInfo.height) + 1;
        boolean isFull = false;
        boolean isComplete = false;
        if (leftInfo.isFull && rightInfo.isFull && leftInfo.height == rightInfo.height) {
            isFull = true;
            isComplete = true;
        } else if (leftInfo.isComplete && rightInfo.isFull && leftInfo.height == rightInfo.height + 1) {
            isComplete = true;
        } else if (leftInfo.isFull & rightInfo.isFull && leftInfo.height == rightInfo.height + 1) {
            isComplete = true;
        } else if (leftInfo.isFull && rightInfo.isComplete && leftInfo.height == rightInfo.height) {
            isComplete = true;
        }

        return new Info(isComplete, isFull, height);
    }


    public static Node genRandBT(int maxL, int maxV) {
        return genRandBTProcess(1, maxL, maxV);
    }

    private static Node genRandBTProcess(int i, int maxL, int maxV) {
        if (i > maxL || Math.random() < 0.5) {
            return null;
        }
        Node node = new Node((int) (Math.random() * (maxV + 1)));
        node.left = genRandBTProcess(i + 1, maxL, maxV);
        node.right = genRandBTProcess(i + 1, maxL, maxV);
        return node;
    }

    public static void validate() {
        int numTest = 10000;
        int maxL = 5;
        int maxV = 100;
        for (int i = 0; i < numTest; i++) {
            Node root = genRandBT(maxL, maxV);
            if (isCBT1(root) != isCBT2(root)) {
                System.out.println("Failed");
                return;
            }
        }
        System.out.println("Test passed!");
    }
}
