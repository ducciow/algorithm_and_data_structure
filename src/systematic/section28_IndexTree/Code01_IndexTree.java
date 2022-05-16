package systematic.section28_IndexTree;

/**
 * @Author: duccio
 * @Date: 16, 05, 2022
 * @Description: An implementation of Index Tree, which performs single add and in-range sum, query in O(logN).
 * @Note:   1. Index coverage starting from 1: _ __ _ ____ _ __ _ ________......
 *          2. With mysterious index transformations.
 */
public class Code01_IndexTree {

    public static void main(String[] args) {
        validate();
    }

    public static class IndexTree {
        int N;
        int[] tree;

        public IndexTree(int size) {
            N = size + 1;  // index starts from 1
            tree = new int[N];
        }

        // add a value onto a single index
        public void add(int idx, int val) {
            while (idx <= N) {
                tree[idx] += val;
                idx += idx & -idx;
            }
        }

        // query the sum of tree[1,..,idx]
        public int sum(int idx) {
            int ans = 0;
            while (idx > 0) {
                ans += tree[idx];
                idx -= idx & -idx;
            }
            return ans;
        }
    }


    public static class Naive {
        int N;
        int[] arr;

        public Naive(int size) {
            N = size;
            arr = new int[N + 1];
        }

        public void add(int idx, int val) {
            arr[idx] += val;
        }

        public int sum(int idx) {
            int ans = 0;
            for (int i = 1; i <= idx; i++) {
                ans += arr[i];
            }
            return ans;
        }
    }

    public static void validate() {
        int testTime = 2000000;
        int N = 100;
        int V = 100;
        IndexTree tree = new IndexTree(N);
        Naive test = new Naive(N);
        for (int i = 0; i < testTime; i++) {
            int index = (int) (Math.random() * N) + 1;
            if (Math.random() <= 0.5) {
                int val = (int) (Math.random() * V);
                tree.add(index, val);
                test.add(index, val);
            } else {
                if (tree.sum(index) != test.sum(index)) {
                    System.out.println("Failed");
                }
            }
        }
        System.out.println("Test passed!");
    }

}
