package systematic.section11_BinaryTree;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @Author: duccio
 * @Date: 11, 04, 2022
 * @Description: Given a binary tree, return its maximum width, which is the maximum size of its levels.
 * @Note:   Ver1. Use a HashMap to record the level number of each node. An extra checking for maximum is needed for the
 *                  last level when the queue is empty.
 *          Ver2. Use two pointers to track the end nodes of current level and next level. Update maximum width when
 *                  current node meets current end. Reassign the next end once the current node has left/right child.
 */
public class Code07_TreeMaxWidth {

    public static void main(String[] args) {
        validate();
    }

    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int v) {
            this.value = v;
        }
    }

    public static int maxWidth1(Node root) {
        if (root == null) {
            return 0;
        }
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        // <Node, level>
        HashMap<Node, Integer> map = new HashMap<>();
        map.put(root, 1);
        int workLevel = 1;
        int levelSize = 0;
        int maxSize = 0;
        while (!queue.isEmpty()) {
            Node cur = queue.poll();
            int curLevel = map.get(cur);
            if (cur.left != null) {
                queue.add(cur.left);
                map.put(cur.left, curLevel + 1);
            }
            if (cur.right != null) {
                queue.add(cur.right);
                map.put(cur.right, curLevel + 1);
            }
            if (curLevel == workLevel) {
                levelSize++;
            } else {
                maxSize = Math.max(maxSize, levelSize);
                workLevel = curLevel;
                levelSize = 1;
            }
        }
        // an extra comparision is needed
        maxSize = Math.max(maxSize, levelSize);
        return maxSize;
    }

    public static int maxWidth2(Node root) {
        if (root == null) {
            return 0;
        }
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        Node curEnd = root;
        Node nextEnd = null;
        int levelSize = 0;
        int maxSize = 0;
        while (!queue.isEmpty()) {
            Node cur = queue.poll();
            levelSize++;
            if (cur.left != null) {
                queue.add(cur.left);
                nextEnd = cur.left;
            }
            if (cur.right != null) {
                queue.add(cur.right);
                nextEnd = cur.right;
            }
            if (cur == curEnd) {
                maxSize = Math.max(maxSize, levelSize);
                levelSize = 0;
                curEnd = nextEnd;
            }
        }
        return maxSize;
    }


    public static Node genRandBT(int maxL, int maxV) {
        return genRandBTProcess(maxL, maxV, 1);
    }

    private static Node genRandBTProcess(int maxL, int maxV, int i) {
        if (i > maxL || Math.random() < 0.5) {
            return null;
        }
        Node node = new Node((int) (Math.random() * (maxV + 1)));
        node.left = genRandBTProcess(maxL, maxV, i + 1);
        node.right = genRandBTProcess(maxL, maxV, i + 1);
        return node;
    }

    public static void validate() {
        int numTest = 10000;
        int maxL = 5;
        int maxV = 100;
        for (int i = 0; i < numTest; i++) {
            Node root = genRandBT(maxL, maxV);
            int ans1 = maxWidth1(root);
            int ans2 = maxWidth2(root);
            if (ans1 != ans2) {
                System.out.println("Failed");
                return;
            }
        }
        System.out.println("Test passed!");
    }

}
