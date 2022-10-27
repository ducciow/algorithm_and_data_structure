package section11_BinaryTree;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: duccio
 * @Date: 11, 04, 2022
 * @Description: Given an N-ary tree, encode it to a binary tree, and then decode back to the original.
 *      https://leetcode.com/problems/encode-n-ary-tree-to-binary-tree (locked)
 * @Note:   - For N-ary to Binary:
 *              a) For every n-ary node, make its first child node as its left node, and put all the other child nodes
 *                  to the left node's right, ie., all of its children form the right boundary of the left subtree of
 *                  this n-ary node.
 *              b) Recursively generate the right boundary of left subtree of every child node from the children list.
 *          - For Binary to N-ary:
 *              a) For every binary node, add its right boundary of left subtree to its children list.
 *              b) Recursively doing so for every child node encountered.
 */
public class Code06_EncodeNaryTreeToBinaryTree {

    // N-ary Node
    public static class NNode {
        public int val;
        public List<NNode> children;

        public NNode() {
        }

        public NNode(int _val) {
            val = _val;
        }

        public NNode(int _val, List<NNode> _children) {
            val = _val;
            children = _children;
        }
    }

    // Binary Node
    public static class BNode {
        int val;
        BNode left;
        BNode right;

        BNode(int v) {
            val = v;
        }
    }


    // encode an n-ary tree to a binary tree
    public static BNode encode(NNode nRoot) {
        if (nRoot == null) {
            return null;
        }
        BNode bRoot = new BNode(nRoot.val);
        bRoot.left = encodeProcess(nRoot.children);
        return bRoot;
    }

    private static BNode encodeProcess(List<NNode> nChildren) {
        BNode root = null;
        BNode cur = null;
        for (NNode nChild : nChildren) {
            BNode bNode = new BNode(nChild.val);
            if (root == null) {
                root = bNode;
            } else {
                cur.right = bNode;
            }
            cur = bNode;
            cur.left = encodeProcess(nChild.children);
        }
        return root;
    }

    // decode back to the n-ary tree
    public static NNode decode(BNode bRoot) {
        if (bRoot == null) {
            return null;
        }
        NNode nRoot = new NNode(bRoot.val);
        nRoot.children = decodeProcess(bRoot.left);
        return nRoot;
    }

    private static List<NNode> decodeProcess(BNode bRoot) {
        List<NNode> children = new ArrayList<>();
        BNode cur = bRoot;
        while (cur != null) {
            NNode child = new NNode(cur.val);
            children.add(child);
            child.children = decodeProcess(cur);
            cur = cur.right;
        }
        return children;
    }

}
