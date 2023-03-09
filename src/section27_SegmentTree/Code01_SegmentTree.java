package section27_SegmentTree;

/**
 * @Author: duccio
 * @Date: 12, 05, 2022
 * @Description: An implementation of Segment Tree, which performs range-wise add, update, and query in O(logN).
 * @Note:   - Key Idea:
 *              1. Use array to simulate tree structure, and assign each node a cover range referring to the index
 *                 range of the original given array. Root node covers the sum of entire range and child nodes take
 *                 charge of respective separated ranges. It is like a heap structure with starting index 1.
 *              2. When new command comes, hold the information as lazy as possible to high-level nodes, and distribute
 *                 the held information to children nodes only when it must to.
 *          ======
 *          1. Introduce auxiliary arrays to mark the current accumulated add/update information.
 *          2. Keep lazy if it comes to a node that right takes charge of the given index range. Otherwise, distribute
 *             its accumulated information to its children and zero out itself.
 *          3. The enough length of operating arrays is 4 times of the original array.
 *          ======
 *          - When can it be utilized ?
 *          : For a segment node, it has the left info and right info, meanwhile it does not require the inspection of
 *            sub info details, just needing to simply process the info in O(1).
 */
public class Code01_SegmentTree {

    public static void main(String[] args) {
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

        validate();
    }

    public static class SegmentTree {
        int MAXN;  // given array length + 1
        int[] arr;  // copied array from the given array shifted one item right
        int[] sum;  // sum in range
        int[] sumLazy;  // auxiliary array of lazy info of add, ie, the accumulated value to be added to a single node
        int[] update;  // update value in range
        boolean[] updateLazy;  // auxiliary array of lazy info indicating whether a node needs update

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

        // collect sum values from children nodes to the current node
        public void pushUp(int rt) {
            sum[rt] = sum[rt << 1] + sum[rt << 1 | 1];
        }

        // distribute accumulated info to child nodes
        // ln: number of left nodes
        // rn: number of right nodes
        // operate on children, and for rt, only unmark it
        public void pushDown(int rt, int ln, int rn) {
            if (updateLazy[rt]) {
                // update left and right children
                updateLazy[rt << 1] = true;
                updateLazy[rt << 1 | 1] = true;
                update[rt << 1] = update[rt];
                update[rt << 1 | 1] = update[rt];
                // update accumulated sum info of left and right nodes
                sumLazy[rt << 1] = 0;
                sumLazy[rt << 1 | 1] = 0;
                sum[rt << 1] = update[rt] * ln;
                sum[rt << 1 | 1] = update[rt] * rn;
                // unmark root
                updateLazy[rt] = false;
            }
            if (sumLazy[rt] != 0) {
                // distribute to left and right children
                sumLazy[rt << 1] += sumLazy[rt];
                sumLazy[rt << 1 | 1] += sumLazy[rt];
                sum[rt << 1] += sumLazy[rt] * ln;
                sum[rt << 1 | 1] += sumLazy[rt] * rn;
                // unmark root
                sumLazy[rt] = 0;
            }
        }

        // build sum from arr
        // L, R: index range of arr
        public void build(int L, int R, int rt) {
            // base case: rt now is a leaf node, which only covers one element
            if (L == R) {
                sum[rt] = arr[L];
                return;
            }
            int mid = L + ((R - L) >> 1);
            build(L, mid, rt << 1);
            build(mid + 1, R, rt << 1 | 1);
            pushUp(rt);
        }

        // add values to an index range
        public void add(int aimL, int aimR, int aimV, int L, int R, int rt) {
            // if [L, R] totally falls within [aimL, aimR]
            if (L >= aimL && R <= aimR) {
                // operate on rt
                sumLazy[rt] += aimV;
                sum[rt] += aimV * (R - L + 1);
                return;
            }
            // otherwise, distribute lazy values
            int mid = L + ((R - L) >> 1);
            pushDown(rt, mid - L + 1, R - mid);
            if (aimL <= mid) {
                add(aimL, aimR, aimV, L, mid, rt << 1);
            }
            if (aimR > mid) {
                add(aimL, aimR, aimV, mid + 1, R, rt << 1 | 1);
            }
            // collect sum value for rt
            pushUp(rt);
        }

        // update values for an index range
        public void update(int aimL, int aimR, int aimV, int L, int R, int rt) {
            // if [L, R] totally falls within [aimL, aimR]
            if (L >= aimL && R <= aimR) {
                // operate on rt
                updateLazy[rt] = true;
                update[rt] = aimV;
                sumLazy[rt] = 0;
                sum[rt] = aimV * (R - L + 1);
                return;
            }
            // otherwise, distribute lazy values
            int mid = L + ((R - L) >> 1);
            pushDown(rt, mid - L + 1, R - mid);
            if (aimL <= mid) {
                update(aimL, aimR, aimV, L, mid, rt << 1);
            }
            if (aimR > mid) {
                update(aimL, aimR, aimV, mid + 1, R, rt << 1 | 1);
            }
            // collect sum value for rt
            pushUp(rt);
        }

        // query the sum value of a given index range
        // notice that unlike add() and update(), it does not pushUp() in the end because it does not change sum
        public long query(int aimL, int aimR, int L, int R, int rt) {
            // if [L, R] totally falls within [aimL, aimR]
            if (L >= aimL && R <= aimR) {
                return sum[rt];
            }
            // otherwise, distribute its accumulated information to get the latest value
            int mid = L + ((R - L) >> 1);
            pushDown(rt, mid - L + 1, R - mid);
            // collect answer
            long ans = 0;
            if (aimL <= mid) {
                ans += query(aimL, aimR, L, mid, rt << 1);
            }
            if (aimR > mid) {
                ans += query(aimL, aimR, mid + 1, R, rt << 1 | 1);
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
        System.out.println("Test begin...");
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
