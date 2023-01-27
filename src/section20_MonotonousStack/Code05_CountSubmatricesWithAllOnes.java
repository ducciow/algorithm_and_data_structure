package section20_MonotonousStack;

/**
 * @Author: duccio
 * @Date: 04, 05, 2022
 * @Description: Given a binary matrix, return the number of submatrices that have all ones.
 *          https://leetcode.com/problems/count-submatrices-with-all-ones
 * @Note:   1. Treat each row of the matrix as the bottom line of a histogram, and the bar height here is just the
 *             consecutive number of 1's from that line upwards in its column.
 *          2. The key trick is how to calculate the number of valid submatrices given the current height h and range.
 *             The number is the permutation of rectangles of height h in the range, and then times by (h - d), where d
 *             is the higher one of left and right nearest smaller heights. That means all rectangles of height h, h-1,
 *             h-2, ..., d+1. Rectangles of height d and below will be calculated later when the smallest height of a
 *             range is d.
 *          3. For repeated bar heights in the same row, only compute for the last appearance to avoid duplicate
 *             calculation. To do so, recall from former problems that it can just pop the stack when >=, which just
 *             ignores the former appearances of repeated values.
 */
public class Code05_CountSubmatricesWithAllOnes {

    public int numSubmat(int[][] mat) {
        if (mat == null || mat.length == 0 || mat[0] == null || mat[0].length == 0) {
            return 0;
        }
        int M = mat.length;
        int N = mat[0].length;
        int[] heights = new int[N];
        int ans = 0;
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                heights[j] = mat[i][j] == 0 ? 0 : heights[j] + 1;
            }
            ans += countFromBottom(heights);
        }
        return ans;
    }

    public static int countFromBottom(int[] heights) {
        int[] stack = new int[heights.length];
        int s = 0;
        int ans = 0;
        for (int i = 0; i < heights.length; i++) {
            while (s > 0 && heights[stack[s - 1]] >= heights[i]) {
                int idx = stack[--s];
                int l = s > 0 ? stack[s - 1] : -1;
                int r = i - 1;
                int n = r - l;
                int down = Math.max(l == -1 ? 0 : heights[l], heights[i]);  // the higher one of nearest smaller heights
                ans += (heights[idx] - down) * (n + 1) * n / 2;  // (heights[idx] - down) * permutation
            }
            stack[s++] = i;
        }
        while (s > 0) {
            int idx = stack[--s];
            int l = s > 0 ? stack[s - 1] : -1;
            int r = heights.length - 1;
            int n = r - l;
            int down = l == -1 ? 0 : heights[l];
            ans += (heights[idx] - down) * (n + 1) * n / 2;
        }
        return ans;
    }

}
