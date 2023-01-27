package section20_MonotonousStack;

/**
 * @Author: duccio
 * @Date: 05, 05, 2022
 * @Description: Given an array of integers, find the sum of min(b), where b ranges over every (contiguous) subarray.
 *      https://leetcode.com/problems/sum-of-subarray-minimums
 * @Note:   1. For each element in array, think about the number of subarrays with the current element as the minimum.
 *          2. When the current item is treated as the minimum, its valid subarrays must have such properties: its left
 *             boundary is on (left_smaller, current], and its right boundary is on [current, right_smaller), which
 *             means there is a combinatorial number of suarrays to be counted.
 *          3. To avoid duplicate counting caused by repeated values, only either left or right boundary is correctly
 *             used, e.g., the stack pops for >= to avoid the right boundary of current value trespassing a repeated
 *             value to its right, and their left boundaries are the same.
 */
public class Code06_SumOfSubarrayMinimums {

    public int sumSubarrayMins(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        long ans = 0;
        int[] stack = new int[arr.length];
        int s = 0;
        for (int i = 0; i < arr.length; i++) {
            while (s > 0 && arr[stack[s - 1]] >= arr[i]) {
                int idx = stack[--s];
                long l = s > 0 ? stack[s - 1] : -1;
                long r = i;
                ans += (idx - l) * (r - idx) * (long) arr[idx];
            }
            stack[s++] = i;
        }
        while (s > 0) {
            int idx = stack[--s];
            long l = s > 0 ? stack[s - 1] : -1;
            long r = arr.length;
            ans += (idx - l) * (r - idx) * (long) arr[idx];
        }
        return (int) (ans % 1000000007);
    }

}
