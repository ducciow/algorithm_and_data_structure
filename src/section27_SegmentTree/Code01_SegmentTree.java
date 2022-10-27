package section27_SegmentTree;

/**
 * @Author: duccio
 * @Date: 12, 05, 2022
 * @Description: An implementation of Segment Tree, which performs in-range add, update, and query in O(logN). The index
 *      starts from 1 in convention.
 * @Note:   1. Use array to simulate tree structure, with root node covers the sum of entire range and child nodes take
 *             charge of respective separated ranges. It is like a heap with starting index 1.
 *          2. The enough length of operating array is 4 times of original array.
 *          3. Use lazy arrays to mark the current necessary add/update, accumulate values in current node until it can
 *             no longer keep the laziness.
 *          4. When current node must communicate its child node, distribute its accumulated value to its children, and
 *             zero out itself.
 *          ======
 *          When can it be utilized ?
 *          For a segment node, it has the left info and right info, meanwhile it does not require the inspection of
 *          sub info details, just needing to simply process the info in O(1).
 */
public class Code01_SegmentTree {

    public static void main(String[] args) {
        validate();

//        int[] arr = { 2, 1, 1, 2, 3, 4, 5 };
//        SegmentTree seg = new SegmentTree(arr);
//        int S = 1; // starting idx of entire range
//        int N = arr.length; // end idx of entire range
//        int root = 1; // root idx of entire range
//        int L = 2;
//        int R = 5;
//        int C = 4;
//        // init
//        seg.build(S, N, root);
//        // add
//        seg.add(L, R, C, S, N, root);
//        // update
//        seg.update(L, R, C, S, N, root);
//        // query
//        long sum = seg.query(L, R, S, N, root);
//        System.out.println(sum);
    }

    public static class SegmentTree {
        int MAXN;  // original array length + 1
        int[] arr;  // original array shifted one item right
        int[] sum;  // sum in range
        int[] sumLazy;  // lazy marking of sum
        int[] update;  // update value
        boolean[] updateLazy;  // lazy marking of update value

        public SegmentTree(int[] original) {
            MAXN = original.length + 1;
            arr = new int[MAXN];
            for (int i = 0; i < MAXN - 1; i++) {
                arr[i + 1] = original[i];
            }
            sum = new int[MAXN << 2];
            sumLazy = new int[MAXN << 2];
            update = new int[MAXN << 2];
            updateLazy = new boolean[MAXN << 2];
        }

        public void pushUp(int rt) {
            sum[rt] = sum[rt << 1] + sum[rt << 1 | 1];
        }

        // ln: number of left nodes
        // rn: number of right nodes
        public void pushDown(int rt, int ln, int rn) {
            if (updateLazy[rt]) {
                // update left and right child
                updateLazy[rt << 1] = true;
                updateLazy[rt << 1 | 1] = true;
                update[rt << 1] = update[rt];
                update[rt << 1 | 1] = update[rt];
                // check out left and right sums
                sumLazy[rt << 1] = 0;
                sumLazy[rt << 1 | 1] = 0;
                sum[rt << 1] = update[rt] * ln;
                sum[rt << 1 | 1] = update[rt] * rn;
                // unmark root
                updateLazy[rt] = false;
            }
            if (sumLazy[rt] != 0) {
                // distribute to left and right child
                sumLazy[rt << 1] += sumLazy[rt];
                sumLazy[rt << 1 | 1] += sumLazy[rt];
                sum[rt << 1] += sumLazy[rt] * ln;
                sum[rt << 1 | 1] += sumLazy[rt] * rn;
                // unmark root
                sumLazy[rt] = 0;
            }
        }

        // build sum from arr
        public void build(int L, int R, int idx) {
            if (L == R) {
                sum[idx] = arr[L];
                return;
            }
            int mid = L + ((R - L) >> 1);
            build(L, mid, idx << 1);
            build(mid + 1, R, idx << 1 | 1);
            pushUp(idx);
        }

        public void add(int aimL, int aimR, int aimV, int L, int R, int idx) {
            // [aimL, aimR] covers [L, R]
            if (L >= aimL && R <= aimR) {
                sumLazy[idx] += aimV;
                sum[idx] += aimV * (R - L + 1);
                return;
            }
            // otherwise
            int mid = L + ((R - L) >> 1);
            // distribute lazy value
            pushDown(idx, mid - L + 1, R - mid);
            if (aimL <= mid) {
                add(aimL, aimR, aimV, L, mid, idx << 1);
            }
            if (aimR > mid) {
                add(aimL, aimR, aimV, mid + 1, R, idx << 1 | 1);
            }
            pushUp(idx);
        }

        public void update(int aimL, int aimR, int aimV, int L, int R, int idx) {
            // [aimL, aimR] covers [L, R]
            if (L >= aimL && R <= aimR) {
                updateLazy[idx] = true;
                update[idx] = aimV;
                sumLazy[idx] = 0;
                sum[idx] = aimV * (R - L + 1);
                return;
            }
            // otherwise
            int mid = L + ((R - L) >> 1);
            // distribute lazy value
            pushDown(idx, mid - L + 1, R - mid);
            if (aimL <= mid) {
                update(aimL, aimR, aimV, L, mid, idx << 1);
            }
            if (aimR > mid) {
                update(aimL, aimR, aimV, mid + 1, R, idx << 1 | 1);
            }
            pushUp(idx);
        }

        public long query(int aimL, int aimR, int L, int R, int idx) {
            if (L >= aimL && R <= aimR) {
                return sum[idx];
            }
            int mid = L + ((R - L) >> 1);
            pushDown(idx, mid - L + 1, R - mid);
            long ans = 0;
            if (aimL <= mid) {
                ans += query(aimL, aimR, L, mid, idx << 1);
            }
            if (aimR > mid) {
                ans += query(aimL, aimR, mid + 1, R, idx << 1 | 1);
            }
            return ans;
        }
    }


    public static class Naive {
        public int[] arr;

        public Naive(int[] original) {
            arr = new int[original.length + 1];
            for (int i = 0; i < original.length; i++) {
                arr[i + 1] = original[i];
            }
        }

        public void add(int L, int R, int C) {
            for (int i = L; i <= R; i++) {
                arr[i] += C;
            }
        }

        public void update(int L, int R, int C) {
            for (int i = L; i <= R; i++) {
                arr[i] = C;
            }
        }

        public long query(int L, int R) {
            long ans = 0;
            for (int i = L; i <= R; i++) {
                ans += arr[i];
            }
            return ans;
        }
    }

    public static int[] generateRandomArray(int len, int max) {
        int size = (int) (Math.random() * len) + 1;
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = (int) (Math.random() * max) - (int) (Math.random() * max);
        }
        return arr;
    }

    public static void validate() {
        int testTimes = 5000;
        int maxLen = 100;
        int maxVal = 1000;
        int addOrUpdateTimes = 1000;
        int queryTimes = 500;
        for (int i = 0; i < testTimes; i++) {
            int[] arr = generateRandomArray(maxLen, maxVal);
            SegmentTree seg = new SegmentTree(arr);
            int S = 1;
            int N = arr.length;
            seg.build(S, N, 1);
            Naive naive = new Naive(arr);
            for (int j = 0; j < addOrUpdateTimes; j++) {
                int num1 = (int) (Math.random() * N) + 1;
                int num2 = (int) (Math.random() * N) + 1;
                int L = Math.min(num1, num2);
                int R = Math.max(num1, num2);
                int C = (int) (Math.random() * maxVal) - (int) (Math.random() * maxVal);
                if (Math.random() < 0.5) {
                    seg.add(L, R, C, S, N, 1);
                    naive.add(L, R, C);
                } else {
                    seg.update(L, R, C, S, N, 1);
                    naive.update(L, R, C);
                }
            }
            for (int k = 0; k < queryTimes; k++) {
                int num1 = (int) (Math.random() * N) + 1;
                int num2 = (int) (Math.random() * N) + 1;
                int L = Math.min(num1, num2);
                int R = Math.max(num1, num2);
                long ans1 = seg.query(L, R, S, N, 1);
                long ans2 = naive.query(L, R);
                if (ans1 != ans2) {
                    System.out.println("Failed");
                    return;
                }
            }
        }
        System.out.println("Test passed!");
    }

}
