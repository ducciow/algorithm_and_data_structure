package section11_BinaryTree;

/**
 * @Author: duccio
 * @Date: 08, 04, 2022
 * @Description: Pre-, In-, and Post-order traversal of a binary tree.
 * @Note:
 */
public class Code01_Traversal {

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
}
