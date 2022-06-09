package systematic.section45_SimplifyErternalInfoForDP;

/**
 * @Author: duccio
 * @Date: 09, 06, 2022
 * @Description: You are given n balloons, indexed from 0 to n - 1. Each balloon is painted with a number on it
 *      represented by an array nums. You are asked to burst all the balloons.You are given n balloons, indexed
 *      from 0 to n - 1. Each balloon is painted with a number on it represented by an array nums. You are asked
 *      to burst all the balloons. If you burst the ith balloon, you will get nums[i - 1] * nums[i] * nums[i + 1]
 *      coins. If i - 1 or i + 1 goes out of bounds of the array, then treat it as if there is a balloon with a 1
 *      painted on it. Return the maximum coins you can collect by bursting the balloons wisely.
 *      https://leetcode.com/problems/burst-balloons/
 * @Note:   Ver1. brute force.
 *          Ver2. dp.
 *          ======
 *          Key idea: Because the outcome for any current range depends on the situation of positions prior to and
 *                    behind this range, it can only make assumptions to simplify the information needed for any
 *                    range during processing. We can assume for any range, the two dependent positions still have
 *                    balloons, and then discuss based on keeping each position till the last to burst, which ensures
 *                    the following recursive calls also have this assumption.
 */
public class Code01_BurstBalloons {

    public static int maxCoins1(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        if (arr.length == 1) {
            return arr[0];
        }
        int N = arr.length;
        // add two 1's at head of tail
        int[] help = new int[N + 2];
        for (int i = 0; i < N; i++) {
            help[i + 1] = arr[i];
        }
        help[0] = 1;
        help[N + 1] = 1;
        return process1(help, 1, N);
    }

    // assume arr[L-1] and arr[R+1] have balloons
    // return the maximum coins to burst all balloons in arr[L..R]
    private static int process1(int[] arr, int L, int R) {
        if (L == R) {
            return arr[L - 1] * arr[L] * arr[R + 1];
        }
        // try to keep balloon in every position till the end to burst
        int ans = process1(arr, L + 1, R) + arr[L - 1] * arr[L] * arr[R + 1];  // for arr[L]
        ans = Math.max(ans, process1(arr, L, R - 1) + arr[L - 1] * arr[R] * arr[R + 1]);  // for arr[R]
        for (int i = L + 1; i < R; i++) {  // for arr[L+1] to arr[R-1]
            int left = process1(arr, L, i - 1);
            int right = process1(arr, i + 1, R);
            int last = arr[L - 1] * arr[i] * arr[R + 1];
            ans = Math.max(ans, left + right + last);
        }
        return ans;
    }

    public static int maxCoins2(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        if (nums.length == 1) {
            return nums[0];
        }
        int N = nums.length;
        int[] arr = new int[N + 2];
        for (int i = 0; i < N; i++) {
            arr[i + 1] = nums[i];
        }
        arr[0] = 1;
        arr[N + 1] = 1;
        int[][] dp = new int[N + 2][N + 2];
        for (int i = 1; i <= N; i++) {
            dp[i][i] = arr[i - 1] * arr[i] * arr[i + 1];
        }
        for (int L = N; L > 0; L--) {
            for (int R = L + 1; R <= N; R++) {
                int ans = dp[L + 1][R] + arr[L - 1] * arr[L] * arr[R + 1];
                ans = Math.max(ans, dp[L][R - 1] + arr[L - 1] * arr[R] * arr[R + 1]);
                for (int i = L + 1; i < R; i++) {
                    ans = Math.max(ans, dp[L][i - 1] + dp[i + 1][R] + arr[L - 1] * arr[i] * arr[R + 1]);
                }
                dp[L][R] = ans;
            }
        }
        return dp[1][N];
    }

}
