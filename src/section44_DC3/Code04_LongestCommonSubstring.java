package section44_DC3;

/**
 * @Author: duccio
 * @Date: 08, 06, 2022
 * @Description: Find the longest common substring of two strings, and return its length.
 * @Note:   Ver1. dp with space compression.
 *          Ver2. dc3
 *                key idea: the two longest substrings are adjacent in the dc3.sa of concatenate(str1, 1, str2), so
 *                use dc3.height can easily get the result.
 */
public class Code04_LongestCommonSubstring {

    public static int lcs1(String str1, String str2) {
        if (str1 == null || str2 == null || str1.length() == 0 || str2.length() == 0) {
            return 0;
        }
        char[] chars1 = str1.toCharArray();
        char[] chars2 = str2.toCharArray();
        int ans = 0;
        int row = 0;  // index of chars1
        int col = chars2.length - 1;  // index of chars2
        // fill in the reverse diagonals from top-right to bottom-left
        while (row < chars1.length) {
            int i = row;
            int j = col;
            int len = 0;
            while (i < chars1.length && j < chars2.length) {
                if (chars1[i] != chars2[j]) {
                    len = 0;
                } else {
                    len++;
                }
                if (len > ans) {
                    ans = len;
                }
                i++;
                j++;
            }
            if (col > 0) {
                col--;
            } else {
                row++;
            }
        }
        return ans;
    }


    public static int lcs2(String str1, String str2) {
        if (str1 == null || str2 == null || str1.length() == 0 || str2.length() == 0) {
            return 0;
        }
        char[] chars1 = str1.toCharArray();
        char[] chars2 = str2.toCharArray();
        int N1 = chars1.length;
        int N2 = chars2.length;
        int min = chars1[0];
        int max = chars1[0];
        for (int i = 1; i < N1; i++) {
            min = Math.min(min, chars1[i]);
            max = Math.max(max, chars1[i]);
        }
        for (int i = 0; i < N2; i++) {
            min = Math.min(min, chars2[i]);
            max = Math.max(max, chars2[i]);
        }
        int[] all = new int[N1 + N2 + 1];
        int idx = 0;
        for (int i = 0; i < N1; i++) {
            all[idx++] = chars1[i] - min + 2;
        }
        all[idx++] = 1;
        for (int i = 0; i < N2; i++) {
            all[idx++] = chars2[i] - min + 2;
        }
        DC3 dc3 = new DC3(all, max - min + 2);
        int N = all.length;
        int[] sa = dc3.sa;
        int[] height = dc3.height;
        int ans = 0;
        for (int i = 1; i < N; i++) {
            int idx1 = sa[i];
            int idx2 = sa[i - 1];
            if (Math.min(idx1, idx2) < N1 && Math.max(idx1, idx2) > N1) {
                ans = Math.max(ans, height[i]);
            }
        }
        return ans;
    }

    public static class DC3 {

        public int[] sa;

        public int[] rank;

        public int[] height;

        public DC3(int[] nums, int max) {
            sa = sa(nums, max);
            rank = rank();
            height = height(nums);
        }

        private int[] sa(int[] nums, int max) {
            int n = nums.length;
            int[] arr = new int[n + 3];
            for (int i = 0; i < n; i++) {
                arr[i] = nums[i];
            }
            return skew(arr, n, max);
        }

        private int[] skew(int[] nums, int n, int K) {
            int n0 = (n + 2) / 3, n1 = (n + 1) / 3, n2 = n / 3, n02 = n0 + n2;
            int[] s12 = new int[n02 + 3], sa12 = new int[n02 + 3];
            for (int i = 0, j = 0; i < n + (n0 - n1); ++i) {
                if (0 != i % 3) {
                    s12[j++] = i;
                }
            }
            radixPass(nums, s12, sa12, 2, n02, K);
            radixPass(nums, sa12, s12, 1, n02, K);
            radixPass(nums, s12, sa12, 0, n02, K);
            int name = 0, c0 = -1, c1 = -1, c2 = -1;
            for (int i = 0; i < n02; ++i) {
                if (c0 != nums[sa12[i]] || c1 != nums[sa12[i] + 1] || c2 != nums[sa12[i] + 2]) {
                    name++;
                    c0 = nums[sa12[i]];
                    c1 = nums[sa12[i] + 1];
                    c2 = nums[sa12[i] + 2];
                }
                if (1 == sa12[i] % 3) {
                    s12[sa12[i] / 3] = name;
                } else {
                    s12[sa12[i] / 3 + n0] = name;
                }
            }
            if (name < n02) {
                sa12 = skew(s12, n02, name);
                for (int i = 0; i < n02; i++) {
                    s12[sa12[i]] = i + 1;
                }
            } else {
                for (int i = 0; i < n02; i++) {
                    sa12[s12[i] - 1] = i;
                }
            }
            int[] s0 = new int[n0], sa0 = new int[n0];
            for (int i = 0, j = 0; i < n02; i++) {
                if (sa12[i] < n0) {
                    s0[j++] = 3 * sa12[i];
                }
            }
            radixPass(nums, s0, sa0, 0, n0, K);
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

        private void radixPass(int[] nums, int[] input, int[] output, int offset, int n, int k) {
            int[] cnt = new int[k + 1];
            for (int i = 0; i < n; ++i) {
                cnt[nums[input[i] + offset]]++;
            }
            for (int i = 0, sum = 0; i < cnt.length; ++i) {
                int t = cnt[i];
                cnt[i] = sum;
                sum += t;
            }
            for (int i = 0; i < n; ++i) {
                output[cnt[nums[input[i] + offset]]++] = input[i];
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
            int[] ans = new int[n];
            for (int i = 0; i < n; i++) {
                ans[sa[i]] = i;
            }
            return ans;
        }

        private int[] height(int[] s) {
            int n = s.length;
            int[] ans = new int[n];
            for (int i = 0, k = 0; i < n; ++i) {
                if (rank[i] != 0) {
                    if (k > 0) {
                        --k;
                    }
                    int j = sa[rank[i] - 1];
                    while (i + k < n && j + k < n && s[i + k] == s[j + k]) {
                        ++k;
                    }
                    // h[i] = k
                    ans[rank[i]] = k;
                }
            }
            return ans;
        }

    }


    public static String randomNumberString(int len, int range) {
        char[] str = new char[len];
        for (int i = 0; i < len; i++) {
            str[i] = (char) ((int) (Math.random() * range) + 'a');
        }
        return String.valueOf(str);
    }

    public static void main(String[] args) {
        int testTime = 10000;
        int len = 30;
        int range = 5;
        System.out.println("Test begin");
        for (int i = 0; i < testTime; i++) {
            int N1 = (int) (Math.random() * len);
            int N2 = (int) (Math.random() * len);
            String str1 = randomNumberString(N1, range);
            String str2 = randomNumberString(N2, range);
            int ans1 = lcs1(str1, str2);
            int ans2 = lcs2(str1, str2);
            if (ans1 != ans2) {
                System.out.println("Failed");
                return;
            }
        }
        System.out.println("Test passed");

//        System.out.println("==========");
//
//        len = 80000;
//        range = 26;
//        long start;
//        long end;
//
//        String str1 = randomNumberString(len, range);
//        String str2 = randomNumberString(len, range);
//
//        start = System.currentTimeMillis();
//        int ans1 = lcs1(str1, str2);
//        end = System.currentTimeMillis();
//        System.out.println("dp : " + ans1 + " , running time : " + (end - start) + " ms");
//
//        start = System.currentTimeMillis();
//        int ans2 = lcs2(str1, str2);
//        end = System.currentTimeMillis();
//        System.out.println("dc3 : " + ans2 + " , running time : " + (end - start) + " ms");

    }

}
