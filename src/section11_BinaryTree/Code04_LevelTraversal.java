package section11_BinaryTree;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @Author: duccio
 * @Date: 11, 04, 2022
 * @Description: Given the root of a binary tree, return the level order traversal of its nodes' values, i.e., from
 *      left to right, level by level from root to leaf.
 * @Note:   - Breadth-first search using a queue:
 *              1. Add root to the queue.
 *              2. while queue is not empty:
 *                  a) Poll and print.
 *                  b) If it has left node, add left node.
 *                  c) If it has right node, add right node.
 */
public class Code04_LevelTraversal {

    public static void main(String[] args) {
        Node root = new Node(1);
        root.left = new Node(2);
        root.right = new Node(3);
        root.left.left = new Node(4);
        root.left.right = new Node(5);
        root.right.left = new Node(6);
        root.right.right = new Node(7);

        levelTraverse(root);
    }

    public static class Node {
        int value;
        Node left;
        Node right;

        public Node(int val) {
            value = val;
        }
    }

    public static void levelTraverse(Node root) {
        if (root == null) {
            return;
        }
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            Node cur = queue.poll();
            System.out.println(cur.value);
            if (cur.left != null) {
                queue.add(cur.left);
            }
            if (cur.right != null) {
                queue.add(cur.right);
            }
        }
    }
}
