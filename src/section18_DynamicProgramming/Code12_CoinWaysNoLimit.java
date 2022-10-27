package section18_DynamicProgramming;

/**
 * @Author: duccio
 * @Date: 28, 04, 2022
 * @Description: Given an array of coins with distinct values, where each type of coins can be used any times, and given
 *      a target value, return the number of ways choosing coins to sum up to the target value.
 * @Note:   Ver1. brute force.
 *          Ver2. DP with loop for a cell.
 *          Ver3. DP without loop for a cell.
 */
public class Code12_CoinWaysNoLimit {

    public static int ways1(int[] coins, int target) {
        if (coins == null || coins.length == 0 || target < 0) {
            return 0;
        }
        return process1(coins, 0, target);
    }

    public static int process1(int[] coins, int idx, int target) {
        if (idx == coins.length) {
            return target == 0 ? 1 : 0;
        }
        int ans = 0;
        for (int num = 0; num * coins[idx] <= target; num++) {
            ans += process1(coins, idx + 1, target - num * coins[idx]);
        }
        return ans;
    }

    public static int ways2(int[] coins, int target) {
        if (coins == null || coins.length == 0 || target < 0) {
            return 0;
        }
        int N = coins.length;
        int[][] dp = new int[N + 1][target + 1];
        dp[N][0] = 1;
        for (int i = N - 1; i >= 0; i--) {
            for (int j = 0; j <= target; j++) {
                for (int num = 0; num * coins[i] <= j; num++) {
                    dp[i][j] += dp[i + 1][j - num * coins[i]];
                }
            }
        }
        return dp[0][target];
    }

    public static int ways3(int[] coins, int target) {
        if (coins == null || coins.length == 0 || target < 0) {
            return 0;
        }
        int N = coins.length;
        int[][] dp = new int[N + 1][target + 1];
        dp[N][0] = 1;
        for (int i = N - 1; i >= 0; i--) {
            for (int j = 0; j <= target; j++) {
                // 1) depends on the cell just under it
                dp[i][j] = dp[i + 1][j];
                // 2) plus the cell in a former position of the same row
                if (j - coins[i] >= 0) {
                    dp[i][j] += dp[i][j - coins[i]];
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

    public static void main(String[] args) {
        int maxLen = 10;
        int maxValue = 30;
        int testTime = 100000;
        System.out.println("Test begin...");
        for (int i = 0; i < testTime; i++) {
            int[] coins = randomArray(maxLen, maxValue);
            int target = (int) (Math.random() * maxValue);
            int ans1 = ways1(coins, target);
            int ans2 = ways2(coins, target);
            int ans3 = ways3(coins, target);
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
