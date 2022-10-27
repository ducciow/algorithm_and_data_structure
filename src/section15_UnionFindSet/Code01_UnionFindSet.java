package section15_UnionFindSet;

import java.util.HashMap;
import java.util.List;
import java.util.Stack;

/**
 * @Author: duccio
 * @Date: 14, 04, 2022
 * @Description: Implementation of UnionFindSet, where two functions -- isSameSet() and union() are mandatory,
 *      and the expected time complexity is O(1).
 * @Note:   - Three HashMaps are required:
 *              1. nodeMap, which maps any given value into a wrapper Node class.
 *              2. parentMap, which maps every node into its parent, and the parent of a head node is itself.
 *              3. sizeMap, which maps every head node into the size of the set it represents.
 *          - The key to achieve expected O(1) lies in findHead(), which flattens the inner structure of a set.
 */
public class Code01_UnionFindSet {

    public static class UnionFindSet<V> {

        // wrapper class
        private static class Node<V> {
            V val;

            private Node(V v) {
                val = v;
            }
        }

        HashMap<V, Node<V>> nodeMap;
        HashMap<Node<V>, Node<V>> parentMap;
        HashMap<Node<V>, Integer> sizeMap;

        public UnionFindSet(List<V> values) {
            nodeMap = new HashMap<>();
            parentMap = new HashMap<>();
            sizeMap = new HashMap<>();
            for (V v : values) {
                Node<V> node = new Node<>(v);
                nodeMap.put(v, node);
                parentMap.put(node, node);  // init the parent to be itself
                sizeMap.put(node, 1);
            }
        }

        public Node<V> findHead(Node<V> node) {
            // store all nodes along the path
            Stack<Node<V>> stack = new Stack<>();
            while (parentMap.get(node) != node) {
                stack.push(node);
                node = parentMap.get(node);
            }
            // reassign every node on the path to be the direct child of the first head
            while (!stack.isEmpty()) {
                parentMap.put(stack.pop(), node);
            }
            return node;
        }

        public boolean isSameSet(V a, V b) {
            Node<V> headA = findHead(nodeMap.get(a));
            Node<V> headB = findHead(nodeMap.get(b));
            return headA == headB;
        }


        public void union(V a, V b) {
            Node<V> headA = findHead(nodeMap.get(a));
            Node<V> headB = findHead(nodeMap.get(b));
            if (headA != headB) {
                int sizeA = sizeMap.get(headA);
                int sizeB = sizeMap.get(headB);
                Node<V> big = sizeA >= sizeB ? headA : headB;
                Node<V> small = headA == big ? headB : headA;
                parentMap.put(small, big);
                sizeMap.put(big, sizeA + sizeB);
                sizeMap.remove(small);
            }
        }

        public int numSets() {
            return sizeMap.size();
        }

    }

}
