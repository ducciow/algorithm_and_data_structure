package systematic.section05_MergeSort;

/**
 * @Author: duccio
 * @Date: 01, 04, 2022
 * @Description: Given an integer array and two integers lower and upper, return the number of range sums that
 *      lie in [lower, upper] inclusive.
 *      https://leetcode.com/problems/count-of-range-sum/
 * @Note:   1. Utilize pre-sum array substituting the original array for process.
 *          2. Add a separate operation during merge:
 *              - For any pre-sum s in the right group, meaning every sub-array ending with s's index, compute a new
 *                boundary [s - upper, s - lower], then iterate through left group to find the window of values that
 *                fall within the new boundary.
 *              - Push windowL forward if it is less than new lower.
 *              - Push windowR forward if it is less than or equal to new upper.
 *              - Increase answer by windowR - windowL.
 *              - Although there are nested iterations, the two iterating pointers of window never go back (because
 *                the pre-sums are sorted), resulting in O(N) in time.
 *          ======
 *          To avoid possible overflow, use long instead of int for pre-sum.
 */
public class Code05_CountOfRangeSum {

    public static int countRangeSum(int[] arr, int lower, int upper) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        long[] preSum = new long[arr.length];
        preSum[0] = arr[0];
        for (int i = 1; i < arr.length; i++) {
            preSum[i] = preSum[i - 1] + arr[i];
        }
        return process(preSum, 0, arr.length - 1, lower, upper);
    }

    private static int process(long[] preSum, int L, int R, int lower, int upper) {
        if (L == R) {
            return preSum[L] >= lower && preSum[L] <= upper ? 1 : 0;
        }
        int mid = L + ((R - L) >> 1);
        int ansL = process(preSum, L, mid, lower, upper);
        int ansR = process(preSum, mid + 1, R, lower, upper);
        return ansL + ansR + merge(preSum, L, mid, R, lower, upper);
    }

    private static int merge(long[] preSum, int L, int M, int R, int lower, int upper) {
        int ans = 0;
        int windowLower = L;
        int windowUpper = L;
        for (int j = M + 1; j <= R; j++) {
            long newLower = preSum[j] - upper;
            long newUpper = preSum[j] - lower;
            while (windowLower <= M && preSum[windowLower] < newLower) {
                windowLower++;
            }
            while (windowUpper <= M && preSum[windowUpper] <= newUpper) {
                windowUpper++;
            }
            ans += windowUpper - windowLower;
        }
        long[] tmp = new long[R - L + 1];
        int idx1 = L;
        int idx2 = M + 1;
        int i = 0;
        while (idx1 <= M && idx2 <= R) {
            tmp[i++] = preSum[idx1] <= preSum[idx2] ? preSum[idx1++] : preSum[idx2++];
        }
        while (idx1 <= M) {
            tmp[i++] = preSum[idx1++];
        }
        while (idx2 <= R) {
            tmp[i++] = preSum[idx2++];
        }
        for (i = 0; i < tmp.length; i++) {
            preSum[L + i] = tmp[i];
        }
        return ans;
    }

}
