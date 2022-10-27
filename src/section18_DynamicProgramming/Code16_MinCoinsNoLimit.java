package section18_DynamicProgramming;

/**
 * @Author: duccio
 * @Date: 29, 04, 2022
 * @Description: Given an array of coins with distinct values, where each type of coins can be used any times, and given
 *      a target value. Return the minimum number of coins used summing up to the target value.
 * @Note:   Ver1. brute force.
 *          Ver2. DP with loop for a cell.
 *          Ver3. DP without loop for a cell.
 *          ======
 *          1. Similar to count ways, except minimizing returned values instead of summing up.
 *          2. When minimize according to the dependent cell, notice that it should add an extra 1.
 */
public class Code16_MinCoinsNoLimit {

    public static void main(String[] args) {
        validate();
    }

    public static int min1(int[] coins, int target) {
        return process1(coins, 0, target);
    }

    public static int process1(int[] coins, int idx, int target) {
        if (idx == coins.length) {
            return target == 0 ? 0 : Integer.MAX_VALUE;
        }
        int ans = Integer.MAX_VALUE;
        for (int n = 0; n * coins[idx] <= target; n++) {
            int next = process1(coins, idx + 1, target - n * coins[idx]);
            if (next != Integer.MAX_VALUE) {
                ans = Math.min(ans, n + next);
            }
        }
        return ans;
    }

    public static int min2(int[] coins, int target) {
        if (target == 0) {
            return 0;
        }
        if (coins == null || coins.length < 1) {
            return Integer.MAX_VALUE;
        }
        int N = coins.length;
        int[][] dp = new int[N + 1][target + 1];
        for (int j = 1; j <= target; j++) {
            dp[N][j] = Integer.MAX_VALUE;
        }
        for (int i = N - 1; i >= 0; i--) {
            for (int j = 0; j <= target; j++) {
                int ans = Integer.MAX_VALUE;
                for (int n = 0; n * coins[i] <= j; n++) {
                    int next = dp[i + 1][j - n * coins[i]];
                    if (next != Integer.MAX_VALUE) {
                        ans = Math.min(ans, n + next);
                    }
                }
                dp[i][j] = ans;
            }
        }
        return dp[0][target];
    }

    public static int min3(int[] coins, int target) {
        if (target == 0) {
            return 0;
        }
        if (coins == null || coins.length < 1) {
            return Integer.MAX_VALUE;
        }
        int N = coins.length;
        int[][] dp = new int[N + 1][target + 1];
        for (int j = 1; j <= target; j++) {
            dp[N][j] = Integer.MAX_VALUE;
        }
        for (int i = N - 1; i >= 0; i--) {
            for (int j = 0; j <= target; j++) {
                dp[i][j] = dp[i + 1][j];
                if (j - coins[i] >= 0) {
                    int dependent = dp[i][j - coins[i]];
                    if (dependent != Integer.MAX_VALUE) {
                        dp[i][j] = Math.min(dp[i][j], dependent + 1);
                    }
                }
            }
        }
        return dp[0][target];
    }


    public static int[] randomArray(int maxLen, int maxValue) {
        int N = (int) (Math.random() * maxLen);
        int[] arr = new int[N];
        boolean[] has = new boolean[maxValue + 1];
        for (int i = 0; i < N; i++) {
            do {
                arr[i] = (int) (Math.random() * maxValue) + 1;
            } while (has[arr[i]]);
            has[arr[i]] = true;
        }
        return arr;
    }

    public static void validate() {
        int maxLen = 20;
        int maxValue = 30;
        int testTime = 10000;
        for (int i = 0; i < testTime; i++) {
            int N = (int) (Math.random() * maxLen);
            int[] coins = randomArray(N, maxValue);
            int target = (int) (Math.random() * maxValue);
            int ans1 = min1(coins, target);
            int ans2 = min2(coins, target);
            int ans3 = min3(coins, target);
            if (ans1 != ans2 || ans1 != ans3) {
                System.out.println("Failed");
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println(ans3);
                return;
            }
        }
        System.out.println("Test passed!");
    }

}
