package systematic.section12_BinaryTreeDP;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @Author: duccio
 * @Date: 12, 04, 2022
 * @Description: Given a binary tree, check if it is completed, ie., either it is full or going to be full.
 * @Note:   Sol1. Non-recursive: 1. Level traversal.
 *                               2. Any node having no left child cannot have right child.
 *                               3. When meet a node that does not have both children, all successor nodes must be
 *                                  leaf nodes.
 *          ======
 *          Sol2. BTDP:
 */
public class Code01_IsCBT {

    public static void main(String[] args) {

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
        //todo
        return true;
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
        // todo
    }
}
