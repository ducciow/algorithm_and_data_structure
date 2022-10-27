package section27_SegmentTree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

/**
 * @Author: duccio
 * @Date: 12, 05, 2022
 * @Description: There are several squares being dropped onto the X-axis of a 2D plane. You are given a 2D integer
 *      array positions where positions[i] = [left_i, sideLength_i] represents the i-th square with a side length of
 *      sideLength_i that is dropped with its left edge aligned with X-coordinate left_i. Each square is dropped one
 *      at a time from a height above any landed squares. It then falls downward until it either lands on the top side
 *      of another square or on the X-axis. A square brushing the left/right side of another square does not count as
 *      landing on it. Once it lands, it freezes in place and cannot be moved. After each square is dropped, you must
 *      record the height of the current tallest stack of squares.
 *      https://leetcode.com/problems/falling-squares/
 * @Note:   1. Modify original segment tree so that its pushUp collects the maximum value.
 *          2. Only needs update and query.
 *          3. The input range needs to be compressed to avoid hitting memory limit. To do so, TreeSet is used because
 *             it is sorted, and HashSet gives wrong output.
 */
public class Code02_FallingSquares {

    public List<Integer> fallingSquares(int[][] positions) {
        HashMap<Integer, Integer> map = rangeCompression(positions);
        int N = map.size();
        SegmentTree seg = new SegmentTree(map.size());
        int max = 0;
        List<Integer> res = new ArrayList<>();
        for (int[] position : positions) {
            int L = map.get(position[0]);
            int R = map.get(position[0] + position[1] - 1);
            int preRangeHeight = seg.query(L, R, 1, N, 1);
            int curRangeHeight = preRangeHeight + position[1];
            seg.update(L, R, curRangeHeight, 1, N, 1);
            max = Math.max(max, curRangeHeight);
            res.add(max);
        }
        return res;
    }

    public static HashMap<Integer, Integer> rangeCompression(int[][] positions) {
        TreeSet<Integer> set = new TreeSet<>();
        for (int[] position : positions) {
            set.add(position[0]);
            set.add(position[0] + position[1] - 1);
        }
        HashMap<Integer, Integer> map = new HashMap<>();
        int idx = 0;
        for (Integer pos : set) {
            map.put(pos, ++idx);
        }
        return map;
    }

    public static class SegmentTree {
        int[] max;
        int[] update;
        boolean[] updateLazy;

        public SegmentTree(int size) {
            int N = size + 1;
            max = new int[N << 2];
            update = new int[N << 2];
            updateLazy = new boolean[N << 2];
        }

        public void pushUp(int rt) {
            max[rt] = Math.max(max[rt << 1], max[rt << 1 | 1]);
        }

        public void pushDown(int rt, int ln, int rn) {
            if (updateLazy[rt]) {
                updateLazy[rt << 1] = true;
                updateLazy[rt << 1 | 1] = true;
                update[rt << 1] = update[rt];
                update[rt << 1 | 1] = update[rt];
                max[rt << 1] = update[rt];
                max[rt << 1 | 1] = update[rt];
                updateLazy[rt] = false;
            }
        }

        public void update(int aimL, int aimR, int aimV, int L, int R, int idx) {
            if (L >= aimL && R <= aimR) {
                updateLazy[idx] = true;
                update[idx] = aimV;
                max[idx] = aimV;
                return;
            }
            int mid = L + ((R - L) >> 1);
            pushDown(idx, mid - L + 1, R - mid);
            if (aimL <= mid) {
                update(aimL, aimR, aimV, aimL, mid, idx << 1);
            }
            if (aimR > mid) {
                update(aimL, aimR, aimV, mid + 1, R, idx << 1 | 1);
            }
            pushUp(idx);
        }

        public int query(int aimL, int aimR, int L, int R, int idx) {
            if (L >= aimL && R <= aimR) {
                return max[idx];
            }
            int mid = L + ((R - L) >> 1);
            pushDown(idx, mid - L + 1, R - mid);
            int left = 0;
            int right = 0;
            if (aimL <= mid) {
                left = query(aimL, aimR, L, mid, idx << 1);
            }
            if (aimR > mid) {
                right = query(aimL, aimR, mid + 1, R, idx << 1 | 1);
            }
            return Math.max(left, right);
        }
    }

}
