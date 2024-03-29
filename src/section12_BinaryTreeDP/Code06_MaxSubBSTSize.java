package section12_BinaryTreeDP;

import java.util.ArrayList;

/**
 * @Author: duccio
 * @Date: 12, 04, 2022
 * @Description: Given a binary tree, return the size of its max sub BST.
 * @Note:   - Info: max, min, maxSize, allSize.
 *          - To see if a left/right subtree is BST, check if its maxSize == allSize or is null.
 *          - For info processing:
 *              1. return null for null node.
 *              2. process max and min.
 *              3. process allSize.
 *              4. process maxSize:
 *                  a) check if left subtree is bst and if its max is smaller.
 *                  b) check if right subtree is bst and if its min is bigger
 *                  c) set current maxSize accordingly.
 */
public class Code06_MaxSubBSTSize {

    public static class Node {
        int val;
        Node left;
        Node right;

        public Node(int v) {
            val = v;
        }
    }

    public static int maxSubBSTSize(Node root) {
        if (root == null) {
            return 0;
        }
        return process(root).maxSize;
    }

    public static class Info {
        int maxSize;
        int allSize;
        int max;
        int min;

        public Info(int ms, int s, int a, int i) {
            maxSize = ms;
            allSize = s;
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
            max = Math.max(max, leftInfo.max);
            min = Math.min(min, leftInfo.min);
        }
        if (rightInfo != null) {
            max = Math.max(max, rightInfo.max);
            min = Math.min(min, rightInfo.min);
        }

        int leftSize = leftInfo == null ? 0 : leftInfo.allSize;
        int rightSize = rightInfo == null ? 0 : rightInfo.allSize;
        int allSize = leftSize + rightSize + 1;

        boolean isLeftBST = leftInfo == null || leftInfo.maxSize == leftInfo.allSize;
        boolean isLeftSmaller = leftInfo == null || leftInfo.max < node.val;

        boolean isRightBST = rightInfo == null || rightInfo.maxSize == rightInfo.allSize;
        boolean isRightBigger = rightInfo == null || rightInfo.min > node.val;

        int validLeftMaxSize = leftInfo == null ? 0 : leftInfo.maxSize;
        int validRightMaxSize = rightInfo == null ? 0 : rightInfo.maxSize;
        int maxSize = isLeftBST && isRightBST && isLeftSmaller && isRightBigger ?
                allSize : Math.max(validLeftMaxSize, validRightMaxSize);

        return new Info(maxSize, allSize, max, min);
    }


    // validation method copied from zuo
    public static int naiveMaxSubBSTSize(Node head) {
        if (head == null) {
            return 0;
        }
        int h = getBSTSize(head);
        if (h != 0) {
            return h;
        }
        return Math.max(naiveMaxSubBSTSize(head.left), naiveMaxSubBSTSize(head.right));
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
            if (maxSubBSTSize(root) != naiveMaxSubBSTSize(root)) {
                System.out.println("Failed");
                return;
            }
        }
        System.out.println("Test passed!");
    }
}
