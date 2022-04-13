package systematic.section12_BinaryTreeDP;

import java.util.ArrayList;

/**
 * @Author: duccio
 * @Date: 13, 04, 2022
 * @Description: Given a binary tree, return the head of its max sub BST.
 * @Note:   1. Info: maxHead, maxSize, max, min.
 *          2. When update maxHead and maxSize, need to see if current subtree is BST.
 *          3. To see if a left/right subtree is BST, check left/right maxHead == left/right head.
 *          4. If current subtree is BST, maxSize = left maxSize + right maxSize + 1.
 *          5. Otherwise, maxSize = max(left maxSize, right maxSize).
 */
public class Code07_MaxSubBSTHead {

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

        @Override
        public String toString() {
            return String.valueOf(val);
        }
    }

    public static Node maxSubBSTHead(Node root) {
        if (root == null) {
            return null;
        }
        return process(root).maxHead;
    }

    public static class Info {
        Node maxHead;
        int maxSize;
        int max;
        int min;

        public Info(Node h, int s, int a, int i) {
            maxHead = h;
            maxSize = s;
            max = a;
            min = i;
        }
    }

    public static Info process(Node node) {
        if (node == null) {
            return null;
        }
        Info leftInfo = process(node.left);
        Info rightInfo = process(node.right);

        int max = node.val;
        int min = node.val;
        if (leftInfo != null) {
            max = Math.max(leftInfo.max, max);
            min = Math.min(leftInfo.min, min);
        }
        if (rightInfo != null) {
            max = Math.max(max, rightInfo.max);
            min = Math.min(min, rightInfo.min);
        }

        Node leftMaxHead = leftInfo == null ? null : leftInfo.maxHead;
        int leftMaxSize = leftInfo == null ? 0 : leftInfo.maxSize;
        Node rightMaxHead = rightInfo == null ? null : rightInfo.maxHead;
        int rightMaxSize = rightInfo == null ? 0 : rightInfo.maxSize;

        Node maxHead = leftMaxSize >= rightMaxSize ? leftMaxHead : rightMaxHead;  // >= means pick left if they're equal
        int maxSize = Math.max(leftMaxSize, rightMaxSize);

        boolean isLeftBST = leftInfo == null || leftInfo.maxHead == node.left;
        boolean isLeftSmaller = leftInfo == null || leftInfo.max < node.val;
        boolean isRightBST = rightInfo == null || rightInfo.maxHead == node.right;
        boolean isRightBigger = rightInfo == null || rightInfo.min > node.val;
        if (isLeftBST && isRightBST && isLeftSmaller && isRightBigger) {
            maxHead = node;
            maxSize = leftMaxSize + rightMaxSize + 1;
        }

        return new Info(maxHead, maxSize, max, min);
    }


    // validation method copied from zuo
    public static Node naiveMaxSubBSTHead(Node head) {
        if (head == null) {
            return null;
        }
        if (getBSTSize(head) != 0) {
            return head;
        }
        Node leftAns = naiveMaxSubBSTHead(head.left);
        Node rightAns = naiveMaxSubBSTHead(head.right);
        return getBSTSize(leftAns) >= getBSTSize(rightAns) ? leftAns : rightAns;
    }

    public static int getBSTSize(Node head) {
        if (head == null) {
            return 0;
        }
        ArrayList<Node> arr = new ArrayList<>();
        in(head, arr);
        for (int i = 1; i < arr.size(); i++) {
            if (arr.get(i).val <= arr.get(i - 1).val) {
                return 0;
            }
        }
        return arr.size();
    }

    public static void in(Node head, ArrayList<Node> arr) {
        if (head == null) {
            return;
        }
        in(head.left, arr);
        arr.add(head);
        in(head.right, arr);
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
        int maxL = 4;
        int maxV = 100;
        for (int i = 0; i < numTest; i++) {
            Node root = genRandBT(maxL, maxV);
            if (maxSubBSTHead(root) != naiveMaxSubBSTHead(root)) {
                System.out.println("Failed");
                return;
            }
        }
        System.out.println("Test passed!");
    }

}
