package section12_BinaryTreeDP;

import java.util.ArrayList;

/**
 * @Author: duccio
 * @Date: 13, 04, 2022
 * @Description: Given a binary tree, return the head of its max sub BST.
 * @Note:   - Info: max, min, maxSize, maxHead.
 *          - To see if a left/right subtree is BST, check its maxHead == its head.
 *          - For info processing:
 *              1. return null for null node.
 *              2. process max and min.
 *              3. process left maxSize and left maxHead.
 *              4. process right maxSize and right maxHead.
 *              5. process current maxSize and current maxHead:
 *                  a) check if left subtree is bst and if its max is smaller.
 *                  b) check if right subtree is bst and if its min is bigger.
 *                  c) set maxSize and maxNode accordingly.
 */
public class Code07_MaxSubBSTHead {

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
        int max;
        int min;
        int maxSize;
        Node maxHead;

        public Info(int a, int i, int s, Node h) {
            max = a;
            min = i;
            maxSize = s;
            maxHead = h;
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

        return new Info(max, min, maxSize, maxHead);
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

    public static void main(String[] args) {
        int numTest = 10000;
        int maxL = 4;
        int maxV = 100;
        System.out.println("Test begin...");
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
