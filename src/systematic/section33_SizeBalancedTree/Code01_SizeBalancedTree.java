package systematic.section33_SizeBalancedTree;

/**
 * @Author: duccio
 * @Date: 19, 05, 2022
 * @Description: Implementation of Size Balanced Tree.
 * @Note:   Extra operations for AVL Tree:
 *          1. to define balance: size(left_child) >= all size(right_child's child), and
 *                                size(right_child) >= all size(left_child's child).
 *          2. the tolerance of size difference between left and right subtrees is at most left/right subtree size * 2.
 *          3. maintaining is for four scenarios similar to AVL, while is called recursively.
 *          4. checking balance at each delete is unnecessary, because add will recursively maintain the balance of
 *             all involved nodes, thus reducing IO frequency.
 */
public class Code01_SizeBalancedTree {

    public static class SBTNode<K extends Comparable<K>, V> {
        K k;
        V v;
        SBTNode<K, V> l;
        SBTNode<K, V> r;
        int size;

        public SBTNode(K key, V val) {
            k = key;
            v = val;
            size = 1;
        }
    }

    public static class SBT<K extends Comparable<K>, V> {
        SBTNode<K, V> root;

        public SBT() {
            root = null;
        }

        private SBTNode<K, V> rightRotate(SBTNode<K, V> cur) {
            SBTNode<K, V> left = cur.l;
            cur.l = left.r;
            left.r = cur;
            left.size = cur.size;
            cur.size = (cur.l == null ? 0 : cur.l.size) + (cur.r == null ? 0 : cur.r.size) + 1;
            return left;
        }

        private SBTNode<K, V> leftRotate(SBTNode<K, V> cur) {
            SBTNode<K, V> right = cur.r;
            cur.r = right.l;
            right.l = cur;
            right.size = cur.size;
            cur.size = (cur.l == null ? 0 : cur.l.size) + (cur.r == null ? 0 : cur.r.size) + 1;
            return right;
        }

        private SBTNode<K, V> maintain(SBTNode<K, V> cur) {
            if (cur == null) {
                return null;
            }
            int LSize = cur.l == null ? 0 : cur.l.size;
            int RSize = cur.r == null ? 0 : cur.r.size;
            int LLSize = cur.l == null || cur.l.l == null ? 0 : cur.l.l.size;
            int LRSize = cur.l == null || cur.l.r == null ? 0 : cur.l.r.size;
            int RRSize = cur.r == null || cur.r.r == null ? 0 : cur.r.r.size;
            int RLSize = cur.r == null || cur.r.l == null ? 0 : cur.r.l.size;
            if (LLSize > RSize) {
                cur = rightRotate(cur);
                cur.r = maintain(cur.r);
                cur = maintain(cur);
            } else if (LRSize > RSize) {
                cur.l = leftRotate(cur.l);
                cur = rightRotate(cur);
                cur.l = maintain(cur.l);
                cur.r = maintain(cur.r);
                cur = maintain(cur);
            } else if (RRSize > LSize) {
                cur = leftRotate(cur);
                cur.l = maintain(cur.l);
                cur = maintain(cur);
            } else if (RLSize > LSize) {
                cur.r = rightRotate(cur.r);
                cur = leftRotate(cur);
                cur.r = maintain(cur.r);
                cur.l = maintain(cur.l);
                cur = maintain(cur);
            }
            return cur;
        }

        private SBTNode<K, V> add(SBTNode<K, V> cur, K key, V val) {
            if (cur == null) {
                return new SBTNode<>(key, val);
            }
            cur.size++;
            if (key.compareTo(cur.k) < 0) {
                cur.l = add(cur.l, key, val);
            } else {
                cur.r = add(cur.r, key, val);
            }
            return maintain(cur);
        }

        // delete does not need to perform maintain
        private SBTNode<K, V> delete(SBTNode<K, V> cur, K key) {
            cur.size--;
            if (key.compareTo(cur.k) < 0) {
                cur.l = delete(cur.l, key);
            } else if (key.compareTo(cur.k) > 0) {
                cur.r = delete(cur.r, key);
            } else {
                if (cur.l == null && cur.r == null) {
                    cur = null;
                } else if (cur.l == null) {
                    cur = cur.r;
                } else if (cur.r == null) {
                    cur = cur.l;
                } else {
                    SBTNode<K, V> pre = null;
                    SBTNode<K, V> replace = cur.r;
                    replace.size--;
                    while (replace.l != null) {
                        pre = replace;
                        replace = replace.l;
                        replace.size--;
                    }
                    if (pre != null) {
                        pre.l = replace.r;
                        replace.r = cur.r;
                    }
                    replace.l = cur.l;
                    replace.size = replace.l.size + (replace.r == null ? 0 : replace.r.size) + 1;
                    cur = replace;
                }
            }
            // cur = maintain(cur);
            return cur;
        }

        // return the left-to-right idx-th node
        private SBTNode<K, V> getIndexNode(SBTNode<K, V> cur, int idx) {
            int leftSize = cur.l == null ? 0 : cur.l.size;
            if (idx == leftSize + 1) {
                return cur;
            } else if (idx <= leftSize) {
                return getIndexNode(cur.l, idx);
            } else {
                return getIndexNode(cur.r, idx - leftSize - 1);
            }
        }

        private SBTNode<K, V> findLastNode(K key) {
            SBTNode<K, V> pre = root;
            SBTNode<K, V> cur = root;
            while (cur != null) {
                pre = cur;
                if (cur.k.compareTo(key) == 0) {
                    break;
                } else if (cur.k.compareTo(key) > 0) {
                    cur = cur.l;
                } else {
                    cur = cur.r;
                }
            }
            return pre;
        }

        private SBTNode<K, V> findLastNoSmallNode(K key) {
            SBTNode<K, V> ret = null;
            SBTNode<K, V> cur = root;
            while (cur != null) {
                if (key.compareTo(cur.k) == 0) {
                    ret = cur;
                    break;
                } else if (key.compareTo(cur.k) < 0) {
                    ret = cur;
                    cur = cur.l;
                } else {
                    cur = cur.r;
                }
            }
            return ret;
        }

        private SBTNode<K, V> findLastNoBigNode(K key) {
            SBTNode<K, V> ret = null;
            SBTNode<K, V> cur = root;
            while (cur != null) {
                if (key.compareTo(cur.k) == 0) {
                    ret = cur;
                    break;
                } else if (key.compareTo(cur.k) < 0) {
                    cur = cur.l;
                } else {
                    ret = cur;
                    cur = cur.r;
                }
            }
            return ret;
        }

        public int size() {
            return root == null ? 0 : root.size;
        }

        public boolean containsKey(K key) {
            if (key == null) {
                return false;
            }
            SBTNode<K, V> lastNode = findLastNode(key);
            return lastNode != null && lastNode.k.compareTo(key) == 0;
        }

        public void put(K key, V val) {
            if (key == null) {
                return;
            }
            SBTNode<K, V> lastNode = findLastNode(key);
            if (lastNode != null && lastNode.k.compareTo(key) == 0) {
                lastNode.v = val;
            } else {
                root = add(root, key, val);
            }
        }

        public void remove(K key) {
            if (key == null) {
                return;
            }
            if (containsKey(key)) {
                root = delete(root, key);
            }
        }

        public V get(K key) {
            if (key == null) {
                return null;
            }
            SBTNode<K, V> lastNode = findLastNode(key);
            if (lastNode != null && lastNode.k.compareTo(key) == 0) {
                return lastNode.v;
            } else {
                return null;
            }
        }

        public K getIndexKey(int idx) {
            if (idx < 0 || idx >= this.size()) {
                return null;
            }
            return getIndexNode(root, idx + 1).k;
        }

        public V getIndexValue(int idx) {
            if (idx < 0 || idx >= this.size()) {
                return null;
            }
            return getIndexNode(root, idx + 1).v;
        }

        public K firstKey() {
            if (root == null) {
                return null;
            }
            SBTNode<K, V> cur = root;
            while (cur.l != null) {
                cur = cur.l;
            }
            return cur.k;
        }

        public K lastKey() {
            if (root == null) {
                return null;
            }
            SBTNode<K, V> cur = root;
            while (cur.r != null) {
                cur = cur.r;
            }
            return cur.k;
        }

        public K floorKey(K key) {
            if (key == null) {
                return null;
            }
            SBTNode<K, V> lastNoBigNode = findLastNoBigNode(key);
            return lastNoBigNode == null ? null : lastNoBigNode.k;
        }

        public K ceilingKey(K key) {
            if (key == null) {
                return null;
            }
            SBTNode<K, V> lastNoSmallNode = findLastNoSmallNode(key);
            return lastNoSmallNode == null ? null : lastNoSmallNode.k;
        }
    }


    // for test
    public static void printAll(SBTNode<String, Integer> head) {
        System.out.println("Binary Tree:");
        printInOrder(head, 0, "H", 17);
        System.out.println();
    }

    // for test
    public static void printInOrder(SBTNode<String, Integer> head, int height, String to, int len) {
        if (head == null) {
            return;
        }
        printInOrder(head.r, height + 1, "v", len);
        String val = to + "(" + head.k + "," + head.v + ")" + to;
        int lenM = val.length();
        int lenL = (len - lenM) / 2;
        int lenR = len - lenM - lenL;
        val = getSpace(lenL) + val + getSpace(lenR);
        System.out.println(getSpace(height * len) + val);
        printInOrder(head.l, height + 1, "^", len);
    }

    // for test
    public static String getSpace(int num) {
        String space = " ";
        StringBuffer buf = new StringBuffer("");
        for (int i = 0; i < num; i++) {
            buf.append(space);
        }
        return buf.toString();
    }

    public static void main(String[] args) {
        SBT<String, Integer> sbt = new SBT<>();
        sbt.put("d", 4);
        sbt.put("c", 3);
        sbt.put("a", 1);
        sbt.put("b", 2);
        // sbt.put("e", 5);
        sbt.put("g", 7);
        sbt.put("f", 6);
        sbt.put("h", 8);
        sbt.put("i", 9);
        sbt.put("a", 111);
        System.out.println(sbt.get("a"));
        sbt.put("a", 1);
        System.out.println(sbt.get("a"));
        for (int i = 0; i < sbt.size(); i++) {
            System.out.println(sbt.getIndexKey(i) + " , " + sbt.getIndexValue(i));
        }
        printAll(sbt.root);
        System.out.println(sbt.firstKey());
        System.out.println(sbt.lastKey());
        System.out.println(sbt.floorKey("g"));
        System.out.println(sbt.ceilingKey("g"));
        System.out.println(sbt.floorKey("e"));
        System.out.println(sbt.ceilingKey("e"));
        System.out.println(sbt.floorKey(""));
        System.out.println(sbt.ceilingKey(""));
        System.out.println(sbt.floorKey("j"));
        System.out.println(sbt.ceilingKey("j"));
        sbt.remove("d");
        printAll(sbt.root);
        sbt.remove("f");
        printAll(sbt.root);

    }

}
