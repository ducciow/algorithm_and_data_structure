package section12_BinaryTreeDP;

/**
 * @Author: duccio
 * @Date: 12, 04, 2022
 * @Description: Given a binary tree, check if it is full.
 * @Note:   Ver1. 1. Info: height, nodes.
 *                2. For a full binary tree, nodes = 2 ** height - 1.
 *          ======
 *          Ver2. 1. Info: isFull, height.
 *                2. Check if left subtree is full, right subtree is full, and left_height == right_height.
 */
public class Code04_IsFull {

    public static class Node {
        int val;
        Node left;
        Node right;

        public Node(int v) {
            val = v;
        }
    }

    public static boolean isFull1(Node root) {
        if (root == null) {
            return true;
        }
        Info1 info = process1(root);
        int height = info.height;
        int nodes = info.nodes;
        return (1 << height) - 1 == nodes;
    }

    public static class Info1 {
        int height;
        int nodes;

        public Info1(int h, int n) {
            height = h;
            nodes = n;
        }
    }

    public static Info1 process1(Node node) {
        // base case
        if (node == null) {
            return new Info1(0, 0);
        }
        // collect info
        Info1 leftInfo = process1(node.left);
        Info1 rightInfo = process1(node.right);
        // process info
        int height = Math.max(leftInfo.height, rightInfo.height) + 1;
        int nodes = leftInfo.nodes + rightInfo.nodes + 1;

        return new Info1(height, nodes);
    }

    public static boolean isFull2(Node root) {
        if (root == null) {
            return true;
        }
        return process2(root).isFull;
    }

    public static class Info2 {
        boolean isFull;
        int height;

        public Info2(boolean is, int h) {
            isFull = is;
            height = h;
        }
    }

    public static Info2 process2(Node node) {
        // base case
        if (node == null) {
            return new Info2(true, 0);
        }
        // collect info
        Info2 leftInfo = process2(node.left);
        Info2 rightInfo = process2(node.right);
        // process info
        int height = Math.max(leftInfo.height, rightInfo.height) + 1;
        boolean isFull = leftInfo.isFull && rightInfo.isFull && leftInfo.height == rightInfo.height;

        return new Info2(isFull, height);
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

    public static void main(String[] args) {
        int numTest = 10000;
        int maxL = 4;
        int maxV = 100;
        System.out.println("Test begin...");
        for (int i = 0; i < numTest; i++) {
            Node root = genRandBT(maxL, maxV);
            if (isFull1(root) != isFull2(root)) {
                System.out.println("Failed");
                return;
            }
        }
        System.out.println("Test passed!");
    }

}
