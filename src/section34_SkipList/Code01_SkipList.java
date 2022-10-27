package section34_SkipList;

import java.util.ArrayList;

/**
 * @Author: duccio
 * @Date: 20, 05, 2022
 * @Description: Implementation of Skip List.
 * @Note:   1. Use probability to control the level of each node.
 *          2. The number of pointers of each node depends on its level, and nodes on the same level are linked in
 *             ascending order.
 *          3. When looking for a key, from the highest level of head node, forward to the end node of current level,
 *             and then continue to the next level of this node.
 */
public class Code01_SkipList {

    public static class SLNode<K extends Comparable<K>, V> {
        K k;
        V v;
        ArrayList<SLNode<K, V>> nexts;

        public SLNode(K key, V val) {
            k = key;
            v = val;
            nexts = new ArrayList<>();
        }

        // return whether the key of current node is less than a given key
        public boolean isKeyLess(K otherKey) {
            return otherKey != null && (k == null || k.compareTo(otherKey) < 0);
        }

        // return whether the key of current node is equal to a given key
        public boolean isKeyEqual(K otherKey) {
            if (k == null) {
                return otherKey == null;
            } else {
                return otherKey != null && k.compareTo(otherKey) == 0;
            }
        }
    }

    public static class SkipList<K extends Comparable<K>, V> {
        private static final double PROB = 0.5;
        private SLNode<K, V> head;
        private int size;
        private int maxLevel;

        public SkipList() {
            head = new SLNode<>(null, null);
            head.nexts.add(null);
            size = 0;
            maxLevel = 0;
        }

        private SLNode<K, V> rightmostLessNodeInTree(K key) {
            if (key == null) {
                return null;
            }
            SLNode<K, V> cur = head;
            int level = maxLevel;
            while (level >= 0) {
                cur = rightMostLessNodeInLevel(key, cur, level--);
            }
            return cur;
        }

        private SLNode<K, V> rightMostLessNodeInLevel(K key, SLNode<K, V> cur, int level) {
            SLNode<K, V> next = cur.nexts.get(level);
            while (next != null && next.isKeyLess(key)) {
                cur = next;
                next = cur.nexts.get(level);
            }
            return cur;
        }

        public int size() {
            return size;
        }

        public boolean containsKey(K key) {
            if (key == null) {
                return false;
            }
            SLNode<K, V> lastNode = rightmostLessNodeInTree(key);
            SLNode<K, V> nextNode = lastNode.nexts.get(0);
            return nextNode != null && nextNode.isKeyEqual(key);
        }

        public void put(K key, V val) {
            if (key == null) {
                return;
            }
            SLNode<K, V> lastNode = rightmostLessNodeInTree(key);
            SLNode<K, V> nextNode = lastNode.nexts.get(0);
            if (nextNode != null && nextNode.isKeyEqual(key)) {
                nextNode.v = val;
            } else {
                size++;
                // roll the level for current node
                int newNodeLevel = 0;
                while (Math.random() < PROB) {
                    newNodeLevel++;
                }
                // update head node and maxLevel
                while (maxLevel < newNodeLevel) {
                    head.nexts.add(null);
                    maxLevel++;
                }
                // create new node
                SLNode<K, V> newNode = new SLNode<>(key, val);
                for (int i = 0; i <= newNodeLevel; i++) {
                    newNode.nexts.add(null);
                }
                // link nodes
                int level = maxLevel;
                SLNode<K, V> pre = head;
                while (level >= 0) {
                    pre = rightMostLessNodeInLevel(key, pre, level);
                    if (level <= newNodeLevel) {
                        newNode.nexts.set(level, pre.nexts.get(level));
                        pre.nexts.set(level, newNode);
                    }
                    level--;
                }
            }
        }

        public void remove(K key) {
            if (containsKey(key)) {
                size--;
                SLNode<K, V> pre = head;
                SLNode<K, V> next = null;
                int level = maxLevel;
                while (level >= 0) {
                    pre = rightMostLessNodeInLevel(key, pre, level);
                    next = pre.nexts.get(level);
                    if (next != null && next.isKeyEqual(key)) {
                        pre.nexts.set(level, next.nexts.get(level));
                    }
                    if (level > 0 && pre == head && pre.nexts.get(level) == null) {
                        head.nexts.remove(level);
                        maxLevel--;
                    }
                    level--;
                }
            }
        }

        public V get(K key) {
            if (key == null) {
                return null;
            }
            SLNode<K, V> lastNode = rightmostLessNodeInTree(key);
            SLNode<K, V> nextNode = lastNode.nexts.get(0);
            return nextNode != null && nextNode.isKeyEqual(key) ? nextNode.v : null;
        }

        public K firstKey() {
            return head.nexts.get(0) == null ? null : head.nexts.get(0).k;
        }

        public K lastKey() {
            SLNode<K, V> cur = head;
            int level = maxLevel;
            while (level >= 0) {
                SLNode<K, V> nextNode = cur.nexts.get(level);
                while (nextNode != null) {
                    cur = nextNode;
                    nextNode = cur.nexts.get(level);
                    ;
                }
                level--;
            }
            return cur.k;
        }

        public K ceilingKey(K key) {
            if (key == null) {
                return null;
            }
            SLNode<K, V> lastNode = rightmostLessNodeInTree(key);
            SLNode<K, V> nextNode = lastNode.nexts.get(0);
            return nextNode == null ? null : nextNode.k;
        }

        public K floorKey(K key) {
            if (key == null) {
                return null;
            }
            SLNode<K, V> lastNode = rightmostLessNodeInTree(key);
            SLNode<K, V> nextNode = lastNode.nexts.get(0);
            return nextNode != null && nextNode.isKeyEqual(key) ? nextNode.k : lastNode.k;
        }
    }


    // for test
    public static void printAll(SkipList<String, String> obj) {
        for (int i = obj.maxLevel; i >= 0; i--) {
            System.out.print("Level " + i + " : ");
            SLNode<String, String> cur = obj.head;
            while (cur.nexts.get(i) != null) {
                SLNode<String, String> next = cur.nexts.get(i);
                System.out.print("(" + next.k + " , " + next.v + ") ");
                cur = next;
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        SkipList<String, String> test = new SkipList<>();
        printAll(test);
        System.out.println("======================");
        test.put("A", "10");
        printAll(test);
        System.out.println("======================");
        test.remove("A");
        printAll(test);
        System.out.println("======================");
        test.put("E", "E");
        test.put("B", "B");
        test.put("A", "A");
        test.put("F", "F");
        test.put("C", "C");
        test.put("D", "D");
        printAll(test);
        System.out.println("======================");
        System.out.println(test.containsKey("B"));
        System.out.println(test.containsKey("Z"));
        System.out.println(test.firstKey());
        System.out.println(test.lastKey());
        System.out.println(test.floorKey("D"));
        System.out.println(test.ceilingKey("D"));
        System.out.println("======================");
        test.remove("D");
        printAll(test);
        System.out.println("======================");
        System.out.println(test.floorKey("D"));
        System.out.println(test.ceilingKey("D"));
    }

}
