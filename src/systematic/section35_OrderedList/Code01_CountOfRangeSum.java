package systematic.section35_OrderedList;

import java.util.HashSet;

/**
 * @Author: duccio
 * @Date: 23, 05, 2022
 * @Description: Given an integer array and two integers lower and upper, return the number of range sums that
 *      lie in [lower, upper] inclusive.
 *      https://leetcode.com/problems/count-of-range-sum/
 * @Note:   1. Key idea: for each element in array, get its preSum ps, and then for every element before it, find the
 *             preSums lie in [ps - upper, ps - lower].
 *          2. To modify Size Balanced Tree for quick finding the required number of preSums, add an extra
 *             node attribute for marking how many items are involved in this subtree.
 *          ======
 *          Another solution is to modify merge sort.
 */
public class Code01_CountOfRangeSum {

    public static int countRangeSum(int[] nums, int lower, int upper) {
        SBT sbt = new SBT();
        long preSum = 0;
        int ans = 0;
        sbt.put(0);
        for (int i = 0; i < nums.length; i++) {
            preSum += nums[i];
            long a = sbt.lessKeySize(preSum - upper);
            long b = sbt.lessKeySize(preSum - lower);
            ans += b - a + 1;
            sbt.put(preSum);
        }
        return ans;
    }

    public static class SBTNode {
        long key;
        SBTNode l;
        SBTNode r;
        long size;
        long count;

        public SBTNode(long k) {
            key = k;
            size = 1;
            count = 1;
        }
    }

    public static class SBT {
        SBTNode root;
        HashSet<Long> set = new HashSet<>();

        private SBTNode rightRotate(SBTNode cur) {
            long same = cur.count - (cur.l == null ? 0 : cur.l.count) - (cur.r == null ? 0 : cur.r.count);
            SBTNode left = cur.l;
            cur.l = left.r;
            left.r = cur;
            left.size = cur.size;
            cur.size = (cur.l == null ? 0 : cur.l.size) + (cur.r == null ? 0 : cur.r.size) + 1;
            left.count = cur.count;
            cur.count = (cur.l == null ? 0 : cur.l.count) + (cur.r == null ? 0 : cur.r.count) + same;
            return left;
        }

        private SBTNode leftRotate(SBTNode cur) {
            long same = cur.count - (cur.l == null ? 0 : cur.l.count) - (cur.r == null ? 0 : cur.r.count);
            SBTNode right = cur.r;
            cur.r = right.l;
            right.l = cur;
            right.size = cur.size;
            cur.size = (cur.l == null ? 0 : cur.l.size) + (cur.r == null ? 0 : cur.r.size) + 1;
            right.count = cur.count;
            cur.count = (cur.l == null ? 0 : cur.l.count) + (cur.r == null ? 0 : cur.r.count) + same;
            return right;
        }

        private SBTNode maintain(SBTNode cur) {
            if (cur == null) {
                return null;
            }
            long LSize = cur.l == null ? 0 : cur.l.size;
            long LLSize = cur.l == null || cur.l.l == null ? 0 : cur.l.l.size;
            long LRSize = cur.l == null || cur.l.r == null ? 0 : cur.l.r.size;
            long RSize = cur.r == null ? 0 : cur.r.size;
            long RLSize = cur.r == null || cur.r.l == null ? 0 : cur.r.l.size;
            long RRSize = cur.r == null || cur.r.r == null ? 0 : cur.r.r.size;
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
                cur.l = maintain(cur.l);
                cur.r = maintain(cur.r);
                cur = maintain(cur);
            }
            return cur;
        }

        private SBTNode add(SBTNode cur, long key, boolean contains) {
            if (cur == null) {
                return new SBTNode(key);
            }
            cur.count++;
            if (key == cur.key) {
                return cur;
            } else {
                if (!contains) {  // size increases for new node, ie., new key
                    cur.size++;
                }
                if (key < cur.key) {
                    cur.l = add(cur.l, key, contains);
                } else {
                    cur.r = add(cur.r, key, contains);
                }
                return maintain(cur);
            }
        }

        public void put(long key) {
            boolean contains = set.contains(key);
            root = add(root, key, contains);
            set.add(key);
        }

        public long lessKeySize(long key) {
            SBTNode cur = root;
            long ans = 0;
            while (cur != null) {
                if (key == cur.key) {
                    ans += cur.l == null ? 0 : cur.l.count;
                    break;
                } else if (key < cur.key) {
                    cur = cur.l;
                } else {
                    ans += cur.count - (cur.r == null ? 0 : cur.r.count);
                    cur = cur.r;
                }
            }
            return ans;
        }

        public long moreKeySize(long key) {
            if (root == null) {
                return 0;
            } else {
                return root.count - lessKeySize(key + 1);
            }
        }
    }

}
