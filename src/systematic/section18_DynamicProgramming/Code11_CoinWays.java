package systematic.section18_DynamicProgramming;

/**
 * @Author: duccio
 * @Date: 28, 04, 2022
 * @Description: Given an array of coins with repeated coin values, and a target value. Return the number of ways using
 *      coins chosen from the array that sum up to the target value.
 * @Note:   Ver1. brute force.
 *          Ver2. DP.
 *          ======
 *          Attempt from left to right.
 */
public class Code11_CoinWays {

    public static void main(String[] args) {
        validate();
    }

    public static int ways1(int[] coins, int target) {
        return process1(coins, 0, target);
    }

    public static int process1(int[] coins, int idx, int target) {
        if (target < 0) {
            return 0;
        }
        if (idx == coins.length) {
            return target == 0 ? 1 : 0;
        }
        int p1 = process1(coins, idx + 1, target);
        int p2 = process1(coins, idx + 1, target - coins[idx]);
        return p1 + p2;
    }

    public static int ways2(int[] coins, int target) {
        int N = coins.length;
        int[][] dp = new int[N + 1][target + 1];
        dp[N][0] = 1;
        for (int i = N - 1; i >= 0; i--) {
            for (int j = 0; j <= target; j++) {
                dp[i][j] = dp[i + 1][j];
                if (j - coins[i] >= 0) {
                    dp[i][j] += dp[i + 1][j - coins[i]];
                }
            }
        }
        return dp[0][target];
    }


    public static int[] randomArray(int maxLen, int maxValue) {
        int N = (int) (Math.random() * maxLen);
        int[] arr = new int[N];
        for (int i = 0; i < N; i++) {
            arr[i] = (int) (Math.random() * maxValue) + 1;
        }
        return arr;
    }

    public static void validate() {
        int maxLen = 20;
        int maxValue = 30;
        int testTime = 10000;
        for (int i = 0; i < testTime; i++) {
            int[] arr = randomArray(maxLen, maxValue);
            int target = (int) (Math.random() * maxValue);
            int ans1 = ways1(arr, target);
            int ans2 = ways2(arr, target);
            if (ans1 != ans2) {
                System.out.println("Failed");
                return;
            }
        }
        System.out.println("Test passed!");
    }

}
