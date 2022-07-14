package systematic.section05_MergeSort;

/**
 * @Author: duccio
 * @Date: 31, 03, 2022
 * @Description: Given an integer array, count the reverse pairs (i, j), where 0 <= i < j < arr.length, and
 *      arr[i] > 2 * arr[j].
 *      https://leetcode.com/problems/reverse-pairs/
 * @Note:   Add a loop to check the condition and count the answer during merge:
     *          - Add an O(N) operation in merge() just before the classic merging operation, which still results in
 *                O(N*logN) in the whole.
 *              - Which means, as long as the pointers do not go back within one iteration.
 *          Be careful about potential overflow:
 *              - Do not increase values in the sentinel.
 *              - Use bit operation >> 1 instead of / 2.
 *              - Check > for even values, and >= for odd values.
 */
public class Code04_ReversePairsTwice {

    public static int reversePairs(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        return process(arr, 0, arr.length - 1);
    }

    private static int process(int[] arr, int L, int R) {
        if (L == R) {
            return 0;
        }
        int mid = L + ((R - L) >> 1);
        int ansL = process(arr, L, mid);
        int ansR = process(arr, mid + 1, R);
        return ansL + ansR + merge(arr, L, mid, R);
    }

    private static int merge(int[] arr, int L, int M, int R) {
        int boundaryR = M + 1;
        int ans = 0;
        for (int i = L; i <= M; i++) {
            while (boundaryR <= R &&
                    // discuss based on arr[i] is even or odd
                    ((arr[i] & 1) == 0 ? (arr[i] >> 1) > arr[boundaryR] : (arr[i] >> 1) >= arr[boundaryR])) {
                boundaryR++;
            }
            ans += boundaryR - M - 1;  // be careful about this computing
        }
        int idx1 = L;
        int idx2 = M + 1;
        int[] tmp = new int[R - L + 1];
        int i = 0;
        while (idx1 <= M && idx2 <= R) {
            tmp[i++] = arr[idx1] <= arr[idx2] ? arr[idx1++] : arr[idx2++];
        }
        while (idx1 <= M) {
            tmp[i++] = arr[idx1++];
        }
        while (idx2 <= R) {
            tmp[i++] = arr[idx2++];
        }
        for (int j = 0; j < tmp.length; j++) {
            arr[L + j] = tmp[j];
        }
        return ans;
    }
}
