package systematic.section20_MonotonousStack;

/**
 * @Author: duccio
 * @Date: 04, 05, 2022
 * @Description: Given an array of integers representing the histogram's bar height where the width of each bar is 1,
 *      return the area of the largest rectangle in the histogram.
 *      https://leetcode.com/problems/largest-rectangle-in-histogram
 * @Note:   1. Use a monotonous stack for searching the range where current height is the smallest.
 *          2. Multiply current height with range.
 *          ======
 *          Here the monotonous stack pops elements when the element is >= current item, instead of >. By doing this,
 *              for repeated values, only the last appearance of the value has the correct range, which does not matter
 *              for this problem because the maximum can still be captured.
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
