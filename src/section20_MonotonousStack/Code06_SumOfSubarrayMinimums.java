package section20_MonotonousStack;

/**
 * @Author: duccio
 * @Date: 05, 05, 2022
 * @Description: Given an array of integers, find the sum of min(b), where b ranges over every (contiguous) subarray.
 * @Note:   1. For each element in array, think about the number of sub-arrays with the current element as the minimum.
 *          2. If the current item is the minimum, the valid subarray must has left/right boundary to be left/right or
 *             equal to the current item.
 *          3. To avoid duplicate counting, only either left or right subarray boundary is correctly used for repeated
 *             values, eg., the stack pops for >= to avoid the right boundary of current value trespassing a repeated
 *             value on its right, and their left boundaries are same.
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
