package section20_MonotonousStack;

/**
 * @Author: duccio
 * @Date: 04, 05, 2022
 * @Description: Given an array of integers representing the histogram's bar heights where the width of each bar is 1,
 *      return the area of the largest rectangle in the histogram. Note that the height of a rectangle is equal to the
 *      shortest bar in it.
 *      https://leetcode.com/problems/largest-rectangle-in-histogram
 * @Note:   1. Use a monotonous stack for searching the range where current height is the smallest.
 *          2. Multiply current height with range.
 *          ======
 *          - Here the monotonous stack can be optimized by popping elements whenever the element is >= current item
 *            instead of >. The reason is, the maximum range is connected for repeated values, so even only the last
 *            appearance of the value has the correct range, it does not matter for this problem.
 */
public class Code03_LargestRectangleInHistogram {

    public int largestRectangleArea(int[] heights) {
        if (heights == null || heights.length == 0) {
            return 0;
        }
        int[] stack = new int[heights.length];
        int s = 0;
        int ans = 0;
        for (int i = 0; i < heights.length; i++) {
            while (s > 0 && heights[stack[s - 1]] >= heights[i]) {
                int idx = stack[--s];
                int l = s > 0 ? stack[s - 1] : -1;  // otherwise -1, not 0
                int r = i - 1;
                int area = heights[idx] * (r - l);
                ans = Math.max(ans, area);
            }
            stack[s++] = i;
        }
        while (s > 0) {
            int idx = stack[--s];
            int l = s > 0 ? stack[s - 1] : -1;
            int r = heights.length - 1;
            int area = heights[idx] * (r - l);
            ans = Math.max(ans, area);
        }
        return ans;
    }

}
