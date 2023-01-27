package section20_MonotonousStack;

/**
 * @Author: duccio
 * @Date: 04, 05, 2022
 * @Description: Given a binary matrix filled with 0's and 1's, find the largest rectangle containing only 1's and
 *      return its area.
 *      https://leetcode.com/problems/maximal-rectangle/
 * @Note:   1. Treat each row of the matrix as the bottom line of a histogram, and the bar height here is just the
 *             consecutive number of 1's from that line upwards in its column.
 *          2. Then call largestRectangleArea() for each row of the matrix.
 *          ======
 *          - Reuse an array representing bar heights of a histogram, from the top row of the matrix to the end.
 */
public class Code04_MaximalRectangle {

    public int maximalRectangle(char[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0) {
            return 0;
        }
        int M = matrix.length;
        int N = matrix[0].length;
        int[] heights = new int[N];
        int ans = 0;
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                heights[j] = matrix[i][j] == '0' ? 0 : heights[j] + 1;  // reuse the heights array
            }
            ans = Math.max(ans, largestRectangleArea(heights));
        }
        return ans;
    }

    public static int largestRectangleArea(int[] heights) {
        if (heights == null || heights.length == 0) {
            return 0;
        }
        int[] stack = new int[heights.length];
        int s = 0;
        int ans = 0;
        for (int i = 0; i < heights.length; i++) {
            while (s > 0 && heights[stack[s - 1]] >= heights[i]) {
                int idx = stack[--s];
                int l = s > 0 ? stack[s - 1] : -1;
                int r = i;
                int area = heights[idx] * (r - 1 - l);
                ans = Math.max(ans, area);
            }
            stack[s++] = i;
        }
        while (s > 0) {
            int idx = stack[--s];
            int l = s > 0 ? stack[s - 1] : -1;
            int r = heights.length;
            int area = heights[idx] * (r - 1 - l);
            ans = Math.max(ans, area);
        }
        return ans;
    }

}
