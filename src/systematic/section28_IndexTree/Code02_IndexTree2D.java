package systematic.section28_IndexTree;

/**
 * @Author: duccio
 * @Date: 16, 05, 2022
 * @Description: Implement 2D version of Index Tree
 *      https://leetcode.com/problems/range-sum-query-2d-mutable (locked)
 * @Note:   Compared to Segment Tree, Index Tree is easier to upgrade to 2D, 3D.
 */
public class Code02_IndexTree2D {

    public static class IndexTree2D {
        int M;  // row
        int N;  // column
        int[][] arr;  // original array, index starting from 0
        int[][] tree;  // index tree, index starting from 1

        public IndexTree2D(int[][] matrix) {
            if (matrix.length == 0 || matrix[0].length == 0) {
                return;
            }
            M = matrix.length;
            N = matrix[0].length;
            arr = new int[M][N];
            tree = new int[M + 1][N + 1];
            for (int i = 0; i < M; i++) {
                for (int j = 0; j < N; j++) {
                    update(i, j, matrix[i][j]);
                }
            }
        }

        // set value for a single position
        public void update(int r, int c, int val) {
            int addVal = val - arr[r][c];
            arr[r][c] += addVal;
            for (int i = r + 1; i <= M; i += i & -i) {
                for (int j = c + 1; j <= N; j += j & -j) {
                    tree[i][j] += addVal;
                }
            }
        }

        // query the range sum from (0, 0) to (r, c)
        public int sum(int r, int c) {
            int ans = 0;
            for (int i = r + 1; i > 0; i -= i & -i) {
                for (int j = c + 1; j > 0; j -= j & -j) {
                    ans += tree[i][j];
                }
            }
            return ans;
        }

        public int regionSum(int r1, int c1, int r2, int c2) {
            return sum(r2, c2) - sum(r1 - 1, c2) - sum(r2, c1 - 1) + sum(r1 - 1, c1 - 1);
        }

    }
}
