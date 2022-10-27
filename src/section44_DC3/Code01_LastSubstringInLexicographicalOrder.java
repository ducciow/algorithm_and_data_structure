package section44_DC3;

/**
 * @Author: duccio
 * @Date: 07, 06, 2022
 * @Description: https://leetcode.com/problems/last-substring-in-lexicographical-order/
 * @Note:
 */
public class Code01_LastSubstringInLexicographicalOrder {

    public String lastSubstring(String s) {
        if (s == null || s.length() == 0) {
            return s;
        }
        int N = s.length();
        char[] str = s.toCharArray();
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (char cha : str) {
            min = Math.min(min, cha);
            max = Math.max(max, cha);
        }
        int[] arr = new int[N];
        for (int i = 0; i < N; i++) {
            arr[i] = str[i] - min + 1;
        }
        DC3 dc3 = new DC3(arr, max - min + 1);
        return s.substring(dc3.sa[N - 1]);
    }


    public class DC3 {

        public int[] sa;

        /**
         *
         * @param nums   an integer array with the minimum element no smaller than 1.
         * @param max   the maximum element in nums.
         */
        public DC3(int[] nums, int max) {
            sa = sa(nums, max);
        }

        private int[] sa(int[] nums, int max) {
            int N = nums.length;
            int[] arr = new int[N + 3];
            for (int i = 0; i < N; i++) {
                arr[i] = nums[i];
            }
            return skew(arr, N, max);
        }

        private int[] skew(int[] nums, int n, int max) {
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
            radixPass(nums, s12, sa12, 2, n02, max);
            radixPass(nums, sa12, s12, 1, n02, max);
            radixPass(nums, s12, sa12, 0, n02, max);
            // check if above sort based on 3-digit is enough to sort group 1+2, ie., no tie
            int ordering = 0, c1 = -1, c2 = -1, c3 = -1;
            for (int i = 0; i < n02; ++i) {
                if (c1 != nums[sa12[i]] || c2 != nums[sa12[i] + 1] || c3 != nums[sa12[i] + 2]) {
                    ordering++;  // increase diff when the current consecutive 3-digit is different from last 3-digit
                    c1 = nums[sa12[i]];
                    c2 = nums[sa12[i] + 1];
                    c3 = nums[sa12[i] + 2];
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
            radixPass(nums, s0, sa0, 0, n0, max);
            // merger group 0 and group 1+2
            int[] sa = new int[n];
            for (int p = 0, t = n0 - n1, k = 0; k < n; k++) {
                int i = sa12[t] < n0 ? sa12[t] * 3 + 1 : (sa12[t] - n0) * 3 + 2;
                int j = sa0[p];
                if (sa12[t] < n0 ? leq(nums[i], s12[sa12[t] + n0], nums[j], s12[j / 3])
                        : leq(nums[i], nums[i + 1], s12[sa12[t] - n0 + 1], nums[j], nums[j + 1], s12[j / 3 + n0])) {
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

        private void radixPass(int[] nums, int[] input, int[] output, int offset, int n, int max) {
            int[] count = new int[max + 1];
            for (int i = 0; i < n; i++) {
                count[nums[input[i] + offset]]++;
            }
            for (int i = 0, sum = 0; i < count.length; i++) {
                int tmp = count[i];
                count[i] = sum;
                sum += tmp;
            }
            for (int i = 0; i < n; i++) {
                output[count[nums[input[i] + offset]]++] = input[i];
            }
        }

        private boolean leq(int a1, int a2, int b1, int b2) {
            return a1 < b1 || (a1 == b1 && a2 <= b2);
        }

        private boolean leq(int a1, int a2, int a3, int b1, int b2, int b3) {
            return a1 < b1 || (a1 == b1 && leq(a2, a3, b2, b3));
        }

    }

}
