package systematic.section32_AVLTree;

/**
 * @Author: duccio
 * @Date: 18, 05, 2022
 * @Description: Implementation of AVL Tree.
 * @Note:   Operations for BST:
 *          1. add
 *             just find the first null along the direction and add to it.
 *          2. delete
 *             a) no left, no right: just make it null.
 *             b) has left, no right: replace it using left.
 *             c) no left, has right: replace it using right.
 *             d) has left and right: replace it using the leftmost of its right subtree.
 *          3. etc.
 *          ======
 *          Extra operations for AVL Tree:
 *          1. to define balance: |height(left) - height(right)| <= 1
 *          2. to maintain the balance, according to four scenarios:
 *             a) LL (left subtree's left subtree is higher): right rotation.
 *             b) LR (left subtree's right subtree is higher): left then right rotation.
 *             c) LL && LR: right rotation.
 *             d) RR (right subtree's right subtree is higher): left rotation.
 *             e) RL (right subtree's left subtree is higher): right then left rotation.
 *             f) RR && RL: left rotation.
 *          3. to maintain every node on the path involved in add/delete in O(logN).
 */
public class Code01_AVLTree {

    public static class AVLNode<K extends Comparable<K>, V> {
        K k;
        V v;
        AVLNode<K, V> l;
        AVLNode<K, V> r;
        int h;

        public AVLNode(K key, V val) {
            k = key;
            v = val;
        }
    }

    public static class AVLTree<K extends Comparable<K>, V> {
        private AVLNode<K, V> root;
        private int size;

        public AVLTree() {
            root = null;
            size = 0;
        }

        private AVLNode<K, V> rightRotate(AVLNode<K, V> cur) {
            AVLNode<K, V> left = cur.l;
            cur.l = left.r;
            left.r = cur;
            cur.h = Math.max(cur.l == null ? 0 : cur.l.h, cur.r == null ? 0 : cur.r.h) + 1;
            left.h = Math.max(left.l == null ? 0 : left.l.h, left.r.h) + 1;
            return left;
        }

        private AVLNode<K, V> leftRotate(AVLNode<K, V> cur) {
            AVLNode<K, V> right = cur.r;
            cur.r = right.l;
            right.l = cur;
            cur.h = Math.max(cur.l == null ? 0 : cur.l.h, cur.r == null ? 0 : cur.r.h) + 1;
            right.h = Math.max(right.l.h, right.r == null ? 0 : right.r.h) + 1;
            return right;
        }

        private AVLNode<K, V> maintain(AVLNode<K, V> cur) {
            if (cur == null) {
                return null;
            }
            int LH = cur.l == null ? 0 : cur.l.h;
            int RH = cur.r == null ? 0 : cur.r.h;
            if (Math.abs(LH - RH) > 1) {
                if (LH > RH) {
                    int LLH = cur.l == null || cur.l.l == null ? 0 : cur.l.l.h;
                    int LRH = cur.l == null || cur.l.r == null ? 0 : cur.l.r.h;
                    if (LLH >= LRH) {  // LL or LL and LR
                        cur = rightRotate(cur);
                    } else {  // LR
                        cur.l = leftRotate(cur.l);
                        cur = rightRotate(cur);
                    }
                } else {
                    int RLH = cur.r == null || cur.r.l == null ? 0 : cur.r.l.h;
                    int RRH = cur.r == null || cur.r.r == null ? 0 : cur.r.r.h;
                    if (RRH >= RLH) {
                        cur = leftRotate(cur);
                    } else {
                        cur.r = rightRotate(cur.r);
                        cur = leftRotate(cur);
                    }
                }
            }
            return cur;
        }

        private AVLNode<K, V> add(AVLNode<K, V> cur, K key, V val) {
            if (cur == null) {
                return new AVLNode<>(key, val);
            }
            if (key.compareTo(cur.k) < 0) {
                cur.l = add(cur.l, key, val);
            } else {
                cur.r = add(cur.r, key, val);
            }
            cur.h = Math.max(cur.l == null ? 0 : cur.l.h, cur.r == null ? 0 : cur.r.h) + 1;
            return maintain(cur);
        }

        private AVLNode<K, V> delete(AVLNode<K, V> cur, K key) {
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
                    AVLNode<K, V> replace = cur.r;
                    while (replace.l != null) {
                        replace = replace.l;
                    }
                    cur.r = delete(cur.r, replace.k);
                    replace.l = cur.l;
                    replace.r = cur.r;
                    cur = replace;
                }
            }
            if (cur != null) {
                cur.h = Math.max(cur.l == null ? 0 : cur.l.h, cur.r == null ? 0 : cur.r.h) + 1;
            }
            return maintain(cur);
        }

        // return the node with the nearest key
        private AVLNode<K, V> findLastNode(K key) {
            AVLNode<K, V> pre = root;
            AVLNode<K, V> cur = root;
            while (cur != null) {
                pre = cur;
                if (key.compareTo(cur.k) == 0) {
                    break;
                } else if (key.compareTo(cur.k) < 0) {
                    cur = cur.l;
                } else {
                    cur = cur.r;
                }
            }
            return pre;
        }

        // return the node with the nearest >= key
        private AVLNode<K, V> findLastNoSmallNode(K key) {
            AVLNode<K, V> ret = null;
            AVLNode<K, V> cur = root;
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

        // return the node with the nearest <= key
        private AVLNode<K, V> findLastNoBigNode(K key) {
            AVLNode<K, V> ret = null;
            AVLNode<K, V> cur = root;
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
            return size;
        }

        public boolean containsKey(K key) {
            if (key == null) {
                return false;
            }
            AVLNode<K, V> lastNode = findLastNode(key);
            return lastNode != null && lastNode.k.compareTo(key) == 0;
        }

        public void put(K key, V val) {
            if (key == null) {
                return;
            }
            AVLNode<K, V> lastNode = findLastNode(key);
            if (lastNode != null && lastNode.k.compareTo(key) == 0) {
                lastNode.v = val;
            } else {
                root = add(root, key, val);
                size++;
            }
        }

        public void remove(K key) {
            if (key == null) {
                return;
            }
            if (containsKey(key)) {
                root = delete(root, key);
                size--;
            }
        }

        public V get(K key) {
            if (key == null) {
                return null;
            }
            AVLNode<K, V> lastNode = findLastNode(key);
            if (lastNode != null && key.compareTo(lastNode.k) == 0) {
                return lastNode.v;
            }
            return null;
        }

        public K firstKey() {
            if (root == null) {
                return null;
            }
            AVLNode<K, V> cur = root;
            while (cur.l != null) {
                cur = cur.l;
            }
            return cur.k;
        }

        public K lastKey() {
            if (root == null) {
                return null;
            }
            AVLNode<K, V> cur = root;
            while (cur.r != null) {
                cur = cur.r;
            }
            return cur.k;
        }

        public K ceilingKey(K key) {
            if (key == null) {
                return null;
            }
            AVLNode<K, V> lastNoSmallNode = findLastNoSmallNode(key);
            return lastNoSmallNode == null ? null : lastNoSmallNode.k;
        }

        public K floorKey(K key) {
            if (key == null) {
                return null;
            }
            AVLNode<K, V> lastNoBigNode = findLastNoBigNode(key);
            return lastNoBigNode == null ? null : lastNoBigNode.k;
        }
    }

}
