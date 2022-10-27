package section22_KMP;

import java.util.ArrayList;

/**
 * @Author: duccio
 * @Date: 09, 05, 2022
 * @Description: Given two binary trees, t1 and t2, return if t2 is a subtree of t1, which is true only if t2 is equal
 *      to a subtree in t1 from the subtree head all long to subtree leaves.
 * @Note:   1. Pre-serialize trees, and use kmp to check substring.
 *          2. Add null when serialize.
 */
public class Code03_IsSubtree {

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
    }

    public static boolean isSubtree(Node n1, Node n2) {
        if (n1 == null) {
            return false;
        }
        if (n2 == null) {
            return true;
        }
        ArrayList<String> str1 = preSerial(n1);
        ArrayList<String> str2 = preSerial(n2);
        String[] strings1 = new String[str1.size()];
        for (int i = 0; i < str1.size(); i++) {
            strings1[i] = str1.get(i);
        }
        String[] strings2 = new String[str2.size()];
        for (int i = 0; i < str2.size(); i++) {
            strings2[i] = str2.get(i);
        }
        return kmp(strings1, strings2) != -1;
    }

    public static ArrayList<String> preSerial(Node node) {
        ArrayList<String> res = new ArrayList<>();
        pre(node, res);
        return res;
    }

    private static void pre(Node node, ArrayList<String> list) {
        if (node == null) {
            list.add(null);
        } else {
            list.add(String.valueOf(node.val));
            pre(node.left, list);
            pre(node.right, list);
        }
    }

    public static int kmp(String[] strings1, String[] strings2) {
        if (strings2.length > strings1.length) {
            return -1;
        }
        int[] nextArr = getNextArray(strings2);
        int idx1 = 0;
        int idx2 = 0;
        while (idx1 < strings1.length && idx2 < strings2.length) {
            if (isEqual(strings1[idx1], strings2[idx2])) {
                idx1++;
                idx2++;
            } else if (nextArr[idx2] > -1) {
                idx2 = nextArr[idx2];
            } else {
                idx1++;
            }
        }
        return idx2 == strings2.length ? idx1 - idx2 : -1;
    }

    public static int[] getNextArray(String[] strings) {
        if (strings.length < 2) {
            return new int[]{-1};
        }
        int[] res = new int[strings.length];
        res[0] = -1;
        res[1] = 0;
        int compare = 0;
        int idx = 2;
        while (idx < strings.length) {
            if (isEqual(strings[idx - 1], strings[compare])) {
                res[idx++] = ++compare;
            } else if (compare > 0) {
                compare = res[compare];
            } else {
                res[idx++] = 0;
            }
        }
        return res;
    }

    public static boolean isEqual(String s1, String s2) {
        if (s1 == null && s2 == null) {
            return true;
        }
        if (s1 == null || s2 == null) {
            return false;
        } else {
            return s1.equals(s2);
        }
    }


    public static boolean naive(Node n1, Node n2) {
        if (n1 == null) {
            return false;
        }
        if (n2 == null) {
            return true;
        }
        if (isSameValueStructure(n1, n2)) {
            return true;
        }
        return naive(n1.left, n2) || naive(n1.right, n2);
    }

    public static boolean isSameValueStructure(Node n1, Node n2) {
        if (n1 == null ^ n2 == null) {
            return false;
        }
        if (n1 == null) {
            return true;
        }
        if (n1.val != n2.val) {
            return false;
        }
        return isSameValueStructure(n1.left, n2.left) && isSameValueStructure(n1.right, n2.right);
    }

    public static Node genRandBiTree(int maxL, int maxV) {
        return generate(1, maxL, maxV);
    }

    public static Node generate(int level, int maxL, int maxV) {
        if (level > maxL || Math.random() < 0.5) {
            return null;
        }
        Node node = new Node((int) (Math.random() * maxV));
        node.left = generate(level + 1, maxL, maxV);
        node.right = generate(level + 1, maxL, maxV);
        return node;
    }

    public static void validate() {
        int bigTreeLevel = 7;
        int smallTreeLevel = 4;
        int nodeMaxValue = 5;
        int testTimes = 100000;
        for (int i = 0; i < testTimes; i++) {
            Node big = genRandBiTree(bigTreeLevel, nodeMaxValue);
            Node small = genRandBiTree(smallTreeLevel, nodeMaxValue);
            boolean ans1 = isSubtree(big, small);
            boolean ans2 = naive(big, small);
            if (ans1 != ans2) {
                System.out.println("Failed");
                return;
            }
        }
        System.out.println("Test passed!");
    }

}
