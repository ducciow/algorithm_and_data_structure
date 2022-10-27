package section44_DC3;

/**
 * @Author: duccio
 * @Date: 08, 06, 2022
 * @Description: Given two integer arrays nums1 and nums2, where nums1 and nums2 represent the digits of two numbers,
 *      and an integer k. Create the maximum number of length k from digits of the two numbers. The relative order of
 *      the digits from the same array must be preserved.
 *      https://leetcode.com/problems/create-maximum-number/
 * @Note:   Ver1. dp: Firstly solve the sub-problem, where in an array, if pick j digits in arr[0..i], what is the
 *                    starting index of maximum number. Then, iterate through by discussing picking some from sums1
 *                    and picking the rest from nums2. Finally merge those two parts, taking the remaining length
 *                    into consideration.
 *          Ver2. dp + DC3 merge
 *
 */
public class Code03_CreateMaximumNumber {

    public static int[] maxNumber1(int[] nums1, int[] nums2, int k) {
        int N1 = nums1.length;
        int N2 = nums2.length;
        if (k < 0 || k > N1 + N2) {
            return null;
        }
        int[][] dp1 = getDp(nums1);
        int[][] dp2 = getDp(nums2);
        int[] res = new int[k];
        // pick some from nums1, and pick the left from nums2
        for (int from1 = Math.max(0, k - N2); from1 <= Math.min(N1, k); from1++) {
            int[] pick1 = maxPick(nums1, dp1, from1);
            int[] pick2 = maxPick(nums2, dp2, k - from1);
            int[] merge = merge(pick1, pick2);
            res = moreThan(res, 0, merge, 0) ? res : merge;
        }
        return res;
    }

    public static int[] merge(int[] nums1, int[] nums2) {
        int k = nums1.length + nums2.length;
        int[] res = new int[k];
        for (int i = 0, j = 0, idx = 0; idx < k; idx++) {
            res[idx] = moreThan(nums1, i, nums2, j) ? nums1[i++] : nums2[j++];
        }
        return res;
    }

    public static boolean moreThan(int[] nums1, int i, int[] nums2, int j) {
        while (i < nums1.length && j < nums2.length && nums1[i] == nums2[j]) {
            i++;
            j++;
        }
        return j == nums2.length || (i < nums1.length && nums1[i] > nums2[j]);
    }

    // row: array index,  column: number of picking
    // cell: from nums[0..i], to pick j digits, return the starting position of maximum number
    private static int[][] getDp(int[] arr) {
        int size = arr.length;  // 0~N-1
        int pick = arr.length + 1;  // 1~N
        int[][] dp = new int[size][pick];
        // dp[i][j] depends on dp[i+1][j]
        for (int j = 1; j < pick; j++) {
            int maxIdx = size - j;
            for (int i = size - j; i >= 0; i--) {
                if (arr[i] >= arr[maxIdx]) {
                    maxIdx = i;
                }
                dp[i][j] = maxIdx;
            }
        }
        return dp;
    }

    private static int[] maxPick(int[] arr, int[][] dp, int pick) {
        int[] res = new int[pick];
        for (int idx = 0, row = 0; pick > 0; idx++, pick--) {
            res[idx] = arr[dp[row][pick]];
            row = dp[row][pick] + 1;
        }
        return res;
    }


    public static int[] maxNumber2(int[] nums1, int[] nums2, int k) {
        int N1 = nums1.length;
        int N2 = nums2.length;
        if (k < 0 || k > N1 + N2) {
            return null;
        }
        int[][] dp1 = getDp(nums1);
        int[][] dp2 = getDp(nums2);
        int[] res = new int[k];
        for (int from1 = Math.max(0, k - N2); from1 <= Math.min(N1, k); from1++) {
            int[] pick1 = maxPick(nums1, dp1, from1);
            int[] pick2 = maxPick(nums2, dp2, k - from1);
            int[] merge = mergeBySuffixArray(pick1, pick2);
            res = moreThan(res, merge) ? res : merge;
        }
        return res;
    }

    public static boolean moreThan(int[] arr1, int[] arr2) {
        int i = 0;
        int j = 0;
        while (i < arr1.length && j < arr2.length && arr1[i] == arr2[j]) {
            i++;
            j++;
        }
        return j == arr2.length || (i < arr1.length && arr1[i] > arr2[j]);
    }

    private static int[] mergeBySuffixArray(int[] arr1, int[] arr2) {
        int n1 = arr1.length;
        int n2 = arr2.length;
        int[] all = new int[n1 + n2 + 1];
        int idx = 0;
        for (int i = 0; i < n1; i++) {
            all[idx++] = arr1[i] + 2;
        }
        all[idx++] = 1;
        for (int i = 0; i < n2; i++) {
            all[idx++] = arr2[i] + 2;
        }

        DC3 dc3 = new DC3(all, 9 + 2);
        int[] rank = dc3.rank;
        int[] res = new int[n1 + n2];
        int i = 0;
        int j = 0;
        int r = 0;
        while (i < n1 && j < n2) {
            res[r++] = rank[i] > rank[n1 + j + 1] ? arr1[i++] : arr2[j++];
        }
        while (i < n1) {
            res[r++] = arr1[i++];
        }
        while (j < n2) {
            res[r++] = arr2[j++];
        }
        return res;
    }

    public static class DC3 {

        public int[] sa;  // sa[i] refers to the starting index of the ranking-i suffix array

        public int[] rank;  // rank[i] refers to the ranking of the suffix array starting at index i

        /**
         * @param nums an integer array with the minimum element no smaller than 1.
         * @param max  the maximum element in nums.
         */
        public DC3(int[] nums, int max) {
            sa = sa(nums, max);
            rank = rank();
        }

        private int[] sa(int[] nums, int max) {
            int N = nums.length;
            int[] arr = new int[N + 3];
            for (int i = 0; i < N; i++) {
                arr[i] = nums[i];
            }
            return skew(arr, N, max);
        }

        private int[] skew(int[] arr, int n, int max) {
            // get the number of each groups
            int n0 = (n + 2) / 3, n1 = (n + 1) / 3, n2 = n / 3, n02 = n0 + n2;
            // get the group 1+2
            int[] s12 = new int[n02 + 3], sa12 = new int[n02 + 3];
            for (int i = 0, idx = 0; i < n + (n0 - n1); i++) {
                if (i % 3 != 0) {
                    s12[idx++] = i;
                }
            }
            // sort group 1+2 based on the consecutive 3 digits starting from each 1 or 2 position
            radixPass(arr, s12, sa12, 2, n02, max);
            radixPass(arr, sa12, s12, 1, n02, max);
            radixPass(arr, s12, sa12, 0, n02, max);
            // check if above sort based on 3-digit is enough to sort group 1+2, ie., no tie
            int ordering = 0, c1 = -1, c2 = -1, c3 = -1;
            for (int i = 0; i < n02; ++i) {
                if (c1 != arr[sa12[i]] || c2 != arr[sa12[i] + 1] || c3 != arr[sa12[i] + 2]) {
                    ordering++;  // increase diff when the current consecutive 3-digit is different from last 3-digit
                    c1 = arr[sa12[i]];
                    c2 = arr[sa12[i] + 1];
                    c3 = arr[sa12[i] + 2];
                }
                // meanwhile, assign the corresponding ordering
                if (sa12[i] % 3 == 1) {
                    s12[sa12[i] / 3] = ordering;
                } else {
                    s12[sa12[i] / 3 + n0] = ordering;
                }
            }
            // if group 1+2 is not sorted, recursively call skew() using ordering instead of original element
            if (ordering < n02) {
                sa12 = skew(s12, n02, ordering);
                for (int i = 0; i < n02; i++) {
                    s12[sa12[i]] = i + 1;
                }
            } else {
                for (int i = 0; i < n02; i++) {
                    sa12[s12[i] - 1] = i;
                }
            }
            // get group 0
            int[] s0 = new int[n0], sa0 = new int[n0];
            for (int i = 0, idx = 0; i < n02; i++) {
                if (sa12[i] < n0) {
                    s0[idx++] = 3 * sa12[i];
                }
            }
            radixPass(arr, s0, sa0, 0, n0, max);
            // merger group 0 and group 1+2
            int[] sa = new int[n];
            for (int p = 0, t = n0 - n1, k = 0; k < n; k++) {
                int i = sa12[t] < n0 ? sa12[t] * 3 + 1 : (sa12[t] - n0) * 3 + 2;
                int j = sa0[p];
                if (sa12[t] < n0 ? leq(arr[i], s12[sa12[t] + n0], arr[j], s12[j / 3])
                        : leq(arr[i], arr[i + 1], s12[sa12[t] - n0 + 1], arr[j], arr[j + 1], s12[j / 3 + n0])) {
                    sa[k] = i;
                    t++;
                    if (t == n02) {
                        for (k++; p < n0; p++, k++) {
                            sa[k] = sa0[p];
                        }
                    }
                } else {
                    sa[k] = j;
                    p++;
                    if (p == n0) {
                        for (k++; t < n02; t++, k++) {
                            sa[k] = sa12[t] < n0 ? sa12[t] * 3 + 1 : (sa12[t] - n0) * 3 + 2;
                        }
                    }
                }
            }
            return sa;
        }

        private void radixPass(int[] arr, int[] input, int[] output, int offset, int n, int max) {
            int[] count = new int[max + 1];
            for (int i = 0; i < n; i++) {
                count[arr[input[i] + offset]]++;
            }
            for (int i = 0, sum = 0; i < count.length; i++) {
                int tmp = count[i];
                count[i] = sum;
                sum += tmp;
            }
            for (int i = 0; i < n; i++) {
                output[count[arr[input[i] + offset]]++] = input[i];
            }
        }

        private boolean leq(int a1, int a2, int b1, int b2) {
            return a1 < b1 || (a1 == b1 && a2 <= b2);
        }

        private boolean leq(int a1, int a2, int a3, int b1, int b2, int b3) {
            return a1 < b1 || (a1 == b1 && leq(a2, a3, b2, b3));
        }

        private int[] rank() {
            int n = sa.length;
            int[] res = new int[n];
            for (int i = 0; i < n; i++) {
                res[sa[i]] = i;
            }
            return res;
        }
    }
}
