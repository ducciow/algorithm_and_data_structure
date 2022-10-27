package section12_BinaryTreeDP;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * @Author: duccio
 * @Date: 12, 04, 2022
 * @Description: Given a binary tree, return the maximum distance between any pair of nodes in it.
 * @Note:   1. Info: maxDist, height.
 *          2. If the max path does not cover current node, the maxDist is the bigger of left maxDist and right maxDist.
 *          3. If the max path covers current node, thr maxDist is left height + right height + 1.
 */
public class Code05_MaxDistance {

    public static class Node {
        int val;
        Node left;
        Node right;

        public Node(int v) {
            val = v;
        }
    }

    public static int maxDistance(Node root) {
        if (root == null) {
            return 0;
        }
        return process(root).maxDist;
    }

    public static class Info {
        int maxDist;
        int height;

        public Info(int d, int h) {
            maxDist = d;
            height = h;
        }
    }

    public static Info process(Node node) {
        if (node == null) {
            return new Info(0, 0);
        }
        Info leftInfo = process(node.left);
        Info rightInfo = process(node.right);

        int height = Math.max(leftInfo.height, rightInfo.height) + 1;

        // the max path does not cover current node
        int maxDist = Math.max(leftInfo.maxDist, rightInfo.maxDist);
        // the max path covers current node
        maxDist = Math.max(maxDist, leftInfo.height + rightInfo.height + 1);

        return new Info(maxDist, height);
    }


    // validation method copied from zuo
    public static int naiveMaxDistance(Node head) {
        if (head == null) {
            return 0;
        }
        ArrayList<Node> arr = getPrelist(head);
        HashMap<Node, Node> parentMap = getParentMap(head);
        int max = 0;
        for (int i = 0; i < arr.size(); i++) {
            for (int j = i; j < arr.size(); j++) {
                max = Math.max(max, distance(parentMap, arr.get(i), arr.get(j)));
            }
        }
        return max;
    }

    public static ArrayList<Node> getPrelist(Node head) {
        ArrayList<Node> arr = new ArrayList<>();
        fillPrelist(head, arr);
        return arr;
    }

    public static void fillPrelist(Node head, ArrayList<Node> arr) {
        if (head == null) {
            return;
        }
        arr.add(head);
        fillPrelist(head.left, arr);
        fillPrelist(head.right, arr);
    }

    public static HashMap<Node, Node> getParentMap(Node head) {
        HashMap<Node, Node> map = new HashMap<>();
        map.put(head, null);
        fillParentMap(head, map);
        return map;
    }

    public static void fillParentMap(Node head, HashMap<Node, Node> parentMap) {
        if (head.left != null) {
            parentMap.put(head.left, head);
            fillParentMap(head.left, parentMap);
        }
        if (head.right != null) {
            parentMap.put(head.right, head);
            fillParentMap(head.right, parentMap);
        }
    }

    public static int distance(HashMap<Node, Node> parentMap, Node o1, Node o2) {
        HashSet<Node> o1Set = new HashSet<>();
        Node cur = o1;
        o1Set.add(cur);
        while (parentMap.get(cur) != null) {
            cur = parentMap.get(cur);
            o1Set.add(cur);
        }
        cur = o2;
        while (!o1Set.contains(cur)) {
            cur = parentMap.get(cur);
        }
        Node lowestAncestor = cur;
        cur = o1;
        int distance1 = 1;
        while (cur != lowestAncestor) {
            cur = parentMap.get(cur);
            distance1++;
        }
        cur = o2;
        int distance2 = 1;
        while (cur != lowestAncestor) {
            cur = parentMap.get(cur);
            distance2++;
        }
        return distance1 + distance2 - 1;
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
            if (maxDistance(root) != naiveMaxDistance(root)) {
                System.out.println("Failed");
                return;
            }
        }
        System.out.println("Test passed!");
    }

}
