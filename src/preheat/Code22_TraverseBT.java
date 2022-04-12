package preheat;

/**
 * @Author: duccio
 * @Date: 23, 03, 2022
 * @Description: Pre-, In-, and Post-order traversal of a binary tree
 * @Note:
 */
public class Code22_TraverseBT {
    public static class Node {
        int val;
        Node left;
        Node right;

        public Node(int v) {
            val = v;
        }
    }

    public static void preOrder(Node root) {
        if (root == null) {
            return;
        }
        System.out.println(root.val);
        preOrder(root.left);
        preOrder(root.right);
    }

    public static void inOrder(Node root) {
        if (root == null) {
            return;
        }
        inOrder(root.left);
        System.out.println(root.val);
        inOrder(root.right);
    }

    public static void postOrder(Node root) {
        if (root == null) {
            return;
        }
        postOrder(root.left);
        postOrder(root.right);
        System.out.println(root.val);
    }


    public static void main(String[] args) {
        Node root = new Node(1);
        root.left = new Node(2);
        root.right = new Node(3);
        root.left.left = new Node(4);
        root.left.right = new Node(5);
        root.right.left = new Node(6);
        root.right.right = new Node(7);

        preOrder(root);
        System.out.println("========");
        inOrder(root);
        System.out.println("========");
        postOrder(root);
        System.out.println("========");

    }
}
