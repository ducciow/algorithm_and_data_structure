package systematic.section11_BinaryTree;

import java.util.Stack;

/**
 * @Author: duccio
 * @Date: 08, 04, 2022
 * @Description: Non-recursive traversal of a binary tree
 * @Note:   For Pre-order traversal: 1. Push root node to stack.
 *                                   2. Pop and print.
 *                                   3. If it has right node, push right node to stack.
 *                                   4. If it has left node, push left node to stack.
 *          ======
 *          For In-order traversal: 1. Push all left nodes (including current root) to stack.
 *                                  2. Pop and print.
 *                                  3. If it has right node, perform 1. on right subtree.
 *          ======
 *          For Post-order traversal: 1. Use two stacks. Push root node to stack1.
 *                                    2. Pop stack1 and push to stack2.
 *                                    3. If it has left node, push left node to stack1.
 *                                    4. If it has right node, push right node to stack1.
 *          ======
 *          Post-order traversal ver2: 1. Use one stack. Push root node to stack.
 *                                     2. Use two pointers -- 'top' for stack.peep, and 'tmp' for last printed item.
 *                                     3. If top has left, left != tmp, and right != tmp, then push left node to stack.
 *                                     4. Else if top has right, and right != tmp, then push right node to stack.
 *                                     5. Else, pop and print top, refer tmp to top.
 */
public class Code03_NonrecursiveTraversal {

    public static void main(String[] args) {
        validate();
    }

    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int v) {
            value = v;
        }
    }

    public static void preOrder(Node root) {
        if (root == null) {
            return;
        }
        Stack<Node> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            Node cur = stack.pop();
            System.out.println(cur.value);
            if (cur.right != null) {
                stack.push(cur.right);
            }
            if (cur.left != null) {
                stack.push(cur.left);
            }
        }
    }

    public static void inOrder(Node root) {
        if (root == null) {
            return;
        }
        Stack<Node> stack = new Stack<>();
        Node cur = root;
        while (!stack.isEmpty() || cur != null) {
            if (cur != null) {
                stack.push(cur);
                cur = cur.left;
            } else {
                cur = stack.pop();
                System.out.println(cur.value);
                cur = cur.right;
            }
        }
    }

    public static void postOrder(Node root) {
        if (root == null) {
            return;
        }
        Stack<Node> stack1 = new Stack<>();
        Stack<Node> stack2 = new Stack<>();
        stack1.push(root);
        while (!stack1.isEmpty()) {
            Node cur = stack1.pop();
            stack2.push(cur);
            if (cur.left != null) {
                stack1.push(cur.left);
            }
            if (cur.right != null) {
                stack1.push(cur.right);
            }
        }
        while (!stack2.isEmpty()) {
            System.out.println(stack2.pop().value);
        }
    }

    public static void postOrderOneStack(Node root) {
        if (root == null) {
            return;
        }
        Stack<Node> stack = new Stack<>();
        stack.push(root);
        Node tmp = root;
        Node top = null;
        while (!stack.isEmpty()) {
            top = stack.peek();
            if (top.left != null && top.left != tmp && top.right != tmp) {
                stack.push(top.left);
            } else if (top.right != null && top.right != tmp) {
                stack.push(top.right);
            } else {
                tmp = top;
                System.out.println(stack.pop().value);
            }
        }
    }


    public static void validate() {
        Node head = new Node(1);
        head.left = new Node(2);
        head.right = new Node(3);
        head.left.left = new Node(4);
        head.left.right = new Node(5);
        head.right.left = new Node(6);
        head.right.right = new Node(7);

        System.out.println("Pre-order:");
        preOrder(head);
        System.out.println("In-order:");
        inOrder(head);
        System.out.println("Post-order");
        postOrder(head);
        System.out.println("Post-order version2");
        postOrderOneStack(head);

    }
}
