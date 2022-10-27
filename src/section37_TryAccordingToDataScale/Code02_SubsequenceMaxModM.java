package section37_TryAccordingToDataScale;

import java.util.TreeSet;

/**
 * @Author: duccio
 * @Date: 26, 05, 2022
 * @Description: Given a positive integer array, and an integer m, return the maximum subsequence sum mod m.
 * @Note:   Ver1. DP
 *          Ver2. DP
 *          Ver3. Divide and conquer
 */
public class Code02_SubsequenceMaxModM {

    // dp
    // varying arguments: index, subsequence_sum
    public static int max1(int[] arr, int m) {
        int sum = 0;
        for (int i : arr) {
            sum += i;
        }
        int N = arr.length;
        // for index 0 ~ i, is there subsequence sum equal to j
        boolean[][] dp = new boolean[N][sum + 1];
        for (int i = 0; i < N; i++) {
            dp[i][0] = true;
        }
        dp[0][arr[0]] = true;
        for (int i = 1; i < N; i++) {
            for (int j = 1; j <= sum; j++) {
                dp[i][j] = dp[i - 1][j];
                if (j - arr[i] >= 0) {
                    dp[i][j] |= dp[i - 1][j - arr[i]];
                }
            }
        }
        int ans = 0;
        for (int j = 0; j <= sum; j++) {
            if (dp[N - 1][j]) {
                ans = Math.max(ans, j % m);
            }
        }
        return ans;
    }

    // dp
    // varying arguments: index, subsequence_sum_mod_m
    public static int max2(int[] arr, int m) {
        int N = arr.length;
        // for index 0 ~ i, is there subsequence sum mod m equal to j
        boolean[][] dp = new boolean[N][m];
        for (int i = 0; i < N; i++) {
            dp[i][0] = true;
        }
        dp[0][arr[0] % m] = true;
        for (int i = 1; i < N; i++) {
            for (int j = 1; j < m; j++) {
                dp[i][j] = dp[i - 1][j];
                int mod = arr[i] % m;
                if (j - mod >= 0) {
                    dp[i][j] |= dp[i - 1][j - mod];
                } else {
                    dp[i][j] |= dp[i - 1][j + m - mod];
                }
            }
        }
        int ans = 0;
        for (int j = m - 1; j > 0; j--) {
            if (dp[N - 1][j]) {
                ans = j;
                break;
            }
        }
        return ans;
    }

    // divide and conquer
    public static int max3(int[] arr, int m) {
        if (arr.length == 1) {
            return arr[0] % m;
        }
        int N = arr.length;
        int mid = N / 2;
        TreeSet<Integer> set1 = new TreeSet<>();
        process3(arr, m, 0, mid, 0, set1);
        TreeSet<Integer> set2 = new TreeSet<>();
        process3(arr, m, mid + 1, N - 1, 0, set2);
        int ans = 0;
        for (Integer leftMod : set1) {
            ans = Math.max(ans, leftMod + set2.floor(m - 1 - leftMod));
        }
        return ans;
    }

    public static void process3(int[] arr, int m, int idx, int end, int sum, TreeSet<Integer> set) {
        if (idx > end) {
            set.add(sum % m);
            return;
        }
        process3(arr, m, idx + 1, end, sum, set);
        process3(arr, m, idx + 1, end, sum + arr[idx], set);
    }


    public static int[] generateRandomArray(int len, int value) {
        int[] ans = new int[(int) (Math.random() * len) + 1];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = (int) (Math.random() * value);
        }
        return ans;
    }

    public static void main(String[] args) {
        int testTime = 500000;
        int len = 10;
        int value = 100;
        int m = 76;
        System.out.println("Test begin");
        for (int i = 0; i < testTime; i++) {
            int[] arr = generateRandomArray(len, value);
            int ans1 = max1(arr, m);
            int ans2 = max3(arr, m);
            if (ans1 != ans2) {
                System.out.println("Failed");
                return;
            }
        }
        System.out.println("Test passed!");

    }

}
