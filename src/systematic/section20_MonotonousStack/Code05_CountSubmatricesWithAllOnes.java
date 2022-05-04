package systematic.section20_MonotonousStack;

/**
 * @Author: duccio
 * @Date: 04, 05, 2022
 * @Description: Given a binary matrix, return the number of submatrices that have all ones.
 *          https://leetcode.com/problems/count-submatrices-with-all-ones
 * @Note:   1. Treat each row of the matrix as the bottom line of a histogram, and the bar height here is just the
 *             consecutive number of 1's in the bottom of column.
 *          2. The key trick is how to calculate the number of valid submatrices given the current height and range.
 *             The number is permutations within the area of range * (heights - higher_nearest_lower_height).
 *          3. For repeated heights in the same row, only compute for the last appearance to avoid duplicate calculation.
 *             To do so, pop the stack when >=, which just ignores the former appearances of repeated values.
 */
public class Code05_CountSubmatricesWithAllOnes {

    public int numSubmat(int[][] mat) {
        if (mat == null || mat.length == 0 || mat[0] == null || mat[0].length == 0) {
            return 0;
        }
        int[] heights = new int[mat[0].length];
        int ans = 0;
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[0].length; j++) {
                heights[j] = mat[i][j] == 0 ? 0 : heights[j] + 1;
            }
            ans += countFromBottom(heights);
        }
        return ans;
    }

    public static int countFromBottom(int[] heights) {
        int ans = 0;
        int[] stack = new int[heights.length];
        int s = 0;
        for (int i = 0; i < heights.length; i++) {
            while (s > 0 && heights[stack[s - 1]] >= heights[i]) {
                int idx = stack[--s];
                int l = s > 0 ? stack[s - 1] : -1;
                int r = i;
                int n = r - 1 - l;
                int min = Math.max(l == -1 ? 0 : heights[l], heights[r]);
                ans += (heights[idx] - min) * (n + 1) * n / 2;
            }
            stack[s++] = i;
        }
        while (s > 0) {
            int idx = stack[--s];
            int l = s > 0 ? stack[s - 1] : -1;
            int r = heights.length;
            int n = r - 1 - l;
            int min = Math.max(l == -1 ? 0 : heights[l], r == heights.length ? 0 : heights[r]);
            ans += (heights[idx] - min) * (n + 1) * n / 2;
        }
        return ans;
    }

}
