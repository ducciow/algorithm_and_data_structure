package systematic.section12_BinaryTreeDP;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * @Author: duccio
 * @Date: 13, 04, 2022
 * @Description: Given two nodes a and b in a binary tree, return their lowest common ancestor.
 * @Note:   Sol1. Not use BinaryTreeDP:
 *                1. Construct a parent indexing HashMap.
 *                2. Put node a and all its ancestors into a HashSet.
 *                3. Iterate from node b upwards, and return the first element appearing in the HashSet.
 *          Sol2. Use BinaryTreeDP:
 *                - Info: Node ans, boolean findA, findB.
 *                - findA/findB means having encountered a/b.
 *                - ans refers to the lca, will be signed once findA && findB.
 *                - For info processing:
 *                  a) process findA/findB by checking leftInfo/rightInfo's findA/findB, and if the current node
 *                     itself is a/b.
 *                  b) process ans by checking leftInfo's ans, rightInfo's ans, or findA && findB.
 */
public class Code08_LowestCommonAncestor {

    public static class Node {
        int val;
        Node left;
        Node right;

        public Node(int v) {
            val = v;
        }
    }

    public static Node lca1(Node root, Node a, Node b) {
        if (root == null) {
            return null;
        }
        // map for parent indexing
        HashMap<Node, Node> map = new HashMap<>();
        map.put(root, null);
        fillParentMap(root, map);
        // get all ancestors of a
        HashSet<Node> set = new HashSet<>();
        Node cur1 = a;
        set.add(cur1);
        while (map.get(cur1) != null) {
            set.add(map.get(cur1));
            cur1 = map.get(cur1);
        }
        // check ancestors of b
        Node cur2 = b;
        while (!set.contains(cur2)) {
            cur2 = map.get(cur2);
        }
        return cur2;
    }

    private static void fillParentMap(Node node, HashMap<Node, Node> map) {
        if (node.left != null) {
            map.put(node.left, node);
            fillParentMap(node.left, map);
        }
        if (node.right != null) {
            map.put(node.right, node);
            fillParentMap(node.right, map);
        }
    }

    public static class Info {
        Node ans;
        boolean findA;
        boolean findB;

        public Info(Node a, boolean fa, boolean fb) {
            ans = a;
            findA = fa;
            findB = fb;
        }
    }

    public static Node lca2(Node root, Node a, Node b) {
        if (root == null) {
            return null;
        }
        return process(root, a, b).ans;
    }

    public static Info process(Node node, Node a, Node b) {
        if (node == null) {
            return new Info(null, false, false);
        }
        Info leftInfo = process(node.left, a, b);
        Info rightInfo = process(node.right, a, b);

        boolean findA = node == a || leftInfo.findA || rightInfo.findA;
        boolean findB = node == b || leftInfo.findB || rightInfo.findB;

        Node ans = null;
        if (leftInfo.ans != null) {
            ans = leftInfo.ans;
        } else if (rightInfo.ans != null) {
            ans = rightInfo.ans;
        } else if (findA && findB) {
            ans = node;
        }

        return new Info(ans, findA, findB);
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

    public static Node pickRandNode(Node root) {
        if (root == null) {
            return null;
        }
        ArrayList<Node> arr = new ArrayList<>();
        toArr(root, arr);
        int idx = (int) (Math.random() * arr.size());
        return arr.get(idx);
    }

    private static void toArr(Node root, ArrayList<Node> arr) {
        if (root == null) {
            return;
        }
        arr.add(root);
        toArr(root.left, arr);
        toArr(root.right, arr);
    }

    public static void main(String[] args) {
        int numTest = 10000;
        int maxL = 4;
        int maxV = 100;
        System.out.println("Test begin...");
        for (int i = 0; i < numTest; i++) {
            Node root = genRandBT(maxL, maxV);
            Node a = pickRandNode(root);
            Node b = pickRandNode(root);
            if (lca1(root, a, b) != lca2(root, a, b)) {
                System.out.println("Failed");
                return;
            }
        }
        System.out.println("Test passed!");
    }

}
