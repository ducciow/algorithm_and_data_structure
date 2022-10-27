package section35_OrderedList;

import java.util.ArrayList;

/**
 * @Author: duccio
 * @Date: 23, 05, 2022
 * @Description: Implement a list, whose add, remove and getIndex are all O(logN).
 * @Note:   1. Use Size Balanced Tree, where items are compared based on their index in the original list.
 *          2. Wrap values to treat same item value differently.
 */
public class Code03_AddRemoveGetIndexGreat {

    public static class SBTNode<V> {
        public V value;
        public SBTNode<V> l;
        public SBTNode<V> r;
        public int size;

        public SBTNode(V v) {
            value = v;
            size = 1;
        }
    }

    public static class SBTList<V> {
        private SBTNode<V> root;

        private SBTNode<V> rightRotate(SBTNode<V> cur) {
            SBTNode<V> leftNode = cur.l;
            cur.l = leftNode.r;
            leftNode.r = cur;
            leftNode.size = cur.size;
            cur.size = (cur.l != null ? cur.l.size : 0) + (cur.r != null ? cur.r.size : 0) + 1;
            return leftNode;
        }

        private SBTNode<V> leftRotate(SBTNode<V> cur) {
            SBTNode<V> rightNode = cur.r;
            cur.r = rightNode.l;
            rightNode.l = cur;
            rightNode.size = cur.size;
            cur.size = (cur.l != null ? cur.l.size : 0) + (cur.r != null ? cur.r.size : 0) + 1;
            return rightNode;
        }

        private SBTNode<V> maintain(SBTNode<V> cur) {
            if (cur == null) {
                return null;
            }
            int leftSize = cur.l != null ? cur.l.size : 0;
            int leftLeftSize = cur.l != null && cur.l.l != null ? cur.l.l.size : 0;
            int leftRightSize = cur.l != null && cur.l.r != null ? cur.l.r.size : 0;
            int rightSize = cur.r != null ? cur.r.size : 0;
            int rightLeftSize = cur.r != null && cur.r.l != null ? cur.r.l.size : 0;
            int rightRightSize = cur.r != null && cur.r.r != null ? cur.r.r.size : 0;
            if (leftLeftSize > rightSize) {
                cur = rightRotate(cur);
                cur.r = maintain(cur.r);
                cur = maintain(cur);
            } else if (leftRightSize > rightSize) {
                cur.l = leftRotate(cur.l);
                cur = rightRotate(cur);
                cur.l = maintain(cur.l);
                cur.r = maintain(cur.r);
                cur = maintain(cur);
            } else if (rightRightSize > leftSize) {
                cur = leftRotate(cur);
                cur.l = maintain(cur.l);
                cur = maintain(cur);
            } else if (rightLeftSize > leftSize) {
                cur.r = rightRotate(cur.r);
                cur = leftRotate(cur);
                cur.l = maintain(cur.l);
                cur.r = maintain(cur.r);
                cur = maintain(cur);
            }
            return cur;
        }

        private SBTNode<V> add(SBTNode<V> cur, int index, SBTNode<V> addNode) {
            if (cur == null) {
                return addNode;
            }
            cur.size++;
            int leftAndHeadSize = (cur.l != null ? cur.l.size : 0) + 1;
            if (index < leftAndHeadSize) {
                cur.l = add(cur.l, index, addNode);
            } else {
                cur.r = add(cur.r, index - leftAndHeadSize, addNode);
            }
            cur = maintain(cur);
            return cur;
        }

        private SBTNode<V> remove(SBTNode<V> cur, int index) {
            cur.size--;
            int curIdx = cur.l != null ? cur.l.size : 0;
            if (index != curIdx) {
                if (index < curIdx) {
                    cur.l = remove(cur.l, index);
                } else {
                    cur.r = remove(cur.r, index - curIdx - 1);
                }
                return cur;
            }
            // index == curIdx
            if (cur.l == null && cur.r == null) {
                return null;
            }
            if (cur.l == null) {
                return cur.r;
            }
            if (cur.r == null) {
                return cur.l;
            }
            // has both left and right child
            SBTNode<V> pre = null;
            SBTNode<V> suc = cur.r;
            suc.size--;
            while (suc.l != null) {
                pre = suc;
                suc = suc.l;
                suc.size--;
            }
            if (pre != null) {
                pre.l = suc.r;
                suc.r = cur.r;
            }
            suc.l = cur.l;
            suc.size = suc.l.size + (suc.r == null ? 0 : suc.r.size) + 1;
            return suc;
        }

        private SBTNode<V> get(SBTNode<V> cur, int index) {
            int leftSize = cur.l != null ? cur.l.size : 0;
            if (index < leftSize) {
                return get(cur.l, index);
            } else if (index == leftSize) {
                return cur;
            } else {
                return get(cur.r, index - leftSize - 1);
            }
        }

        public void add(int index, V num) {
            SBTNode<V> addNode = new SBTNode<>(num);
            if (root == null) {
                root = addNode;
            } else {
                if (index <= root.size) {
                    root = add(root, index, addNode);
                }
            }
        }

        public void remove(int index) {
            if (index >= 0 && size() > index) {
                root = remove(root, index);
            }
        }

        public V get(int index) {
            SBTNode<V> ans = get(root, index);
            return ans.value;
        }

        public int size() {
            return root == null ? 0 : root.size;
        }

    }


    public static void main(String[] args) {
        // functionality test
        int test = 50000;
        int max = 1000000;
        boolean pass = true;
        ArrayList<Integer> list = new ArrayList<>();
        SBTList<Integer> sbtList = new SBTList<>();
        for (int i = 0; i < test; i++) {
            if (list.size() != sbtList.size()) {
                pass = false;
                break;
            }
            if (list.size() > 1 && Math.random() < 0.5) {
                int removeIndex = (int) (Math.random() * list.size());
                list.remove(removeIndex);
                sbtList.remove(removeIndex);
            } else {
                int randomIndex = (int) (Math.random() * (list.size() + 1));
                int randomValue = (int) (Math.random() * (max + 1));
                list.add(randomIndex, randomValue);
                sbtList.add(randomIndex, randomValue);
            }
        }
        for (int i = 0; i < list.size(); i++) {
            if (!list.get(i).equals(sbtList.get(i))) {
                pass = false;
                break;
            }
        }
        System.out.println("Is functionality test passed : " + pass);

        // performance test
        test = 500000;
        list = new ArrayList<>();
        sbtList = new SBTList<>();
        long start = 0;
        long end = 0;

        start = System.currentTimeMillis();
        for (int i = 0; i < test; i++) {
            int randomIndex = (int) (Math.random() * (list.size() + 1));
            int randomValue = (int) (Math.random() * (max + 1));
            list.add(randomIndex, randomValue);
        }
        end = System.currentTimeMillis();
        System.out.println("ArrayList add() ： " + (end - start));

        start = System.currentTimeMillis();
        for (int i = 0; i < test; i++) {
            int randomIndex = (int) (Math.random() * (i + 1));
            list.get(randomIndex);
        }
        end = System.currentTimeMillis();
        System.out.println("ArrayList get() : " + (end - start));

        start = System.currentTimeMillis();
        for (int i = 0; i < test; i++) {
            int randomIndex = (int) (Math.random() * list.size());
            list.remove(randomIndex);
        }
        end = System.currentTimeMillis();
        System.out.println("ArrayList remove() : " + (end - start));

        start = System.currentTimeMillis();
        for (int i = 0; i < test; i++) {
            int randomIndex = (int) (Math.random() * (sbtList.size() + 1));
            int randomValue = (int) (Math.random() * (max + 1));
            sbtList.add(randomIndex, randomValue);
        }
        end = System.currentTimeMillis();
        System.out.println("SbtList add() : " + (end - start));

        start = System.currentTimeMillis();
        for (int i = 0; i < test; i++) {
            int randomIndex = (int) (Math.random() * (i + 1));
            sbtList.get(randomIndex);
        }
        end = System.currentTimeMillis();
        System.out.println("SbtList get() :  " + (end - start));

        start = System.currentTimeMillis();
        for (int i = 0; i < test; i++) {
            int randomIndex = (int) (Math.random() * sbtList.size());
            sbtList.remove(randomIndex);
        }
        end = System.currentTimeMillis();
        System.out.println("SbtList remove() :  " + (end - start));

    }

}
