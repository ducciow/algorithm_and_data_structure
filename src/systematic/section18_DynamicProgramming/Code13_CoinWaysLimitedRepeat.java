package systematic.section18_DynamicProgramming;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: duccio
 * @Date: 28, 04, 2022
 * @Description: Given an array of coins with repeated coin values, where coins with same value are treated as the same,
 *      and a target value. Return the number of ways using coins chosen from the array that sum up to the target value.
 * @Note:   Ver1. brute force.
 *          Ver2. DP with loop for a cell.
 *          Ver3, Dp without loop for a cell.
 */
public class Code13_CoinWaysLimitedRepeat {

    public static void main(String[] args) {
        validate();
    }

    public static class Info {
        int[] values;
        int[] nums;

        public Info(int[] arr) {
            construct(arr);
        }

        public void construct(int[] arr) {
            HashMap<Integer, Integer> map = new HashMap<>();
            for (int coin : arr) {
                map.put(coin, map.getOrDefault(coin, 0) + 1);
            }
            int N = map.size();
            values = new int[N];
            nums = new int[N];
            int idx = 0;
            for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
                values[idx] = entry.getKey();
                nums[idx++] = entry.getValue();
            }
        }
    }


    public static int ways1(int[] coins, int target) {
        if (coins == null || coins.length == 0 || target < 0) {
            return 0;
        }
        Info info = new Info(coins);
        return process1(info.values, info.nums, 0, target);
    }

    public static int process1(int[] values, int[] nums, int idx, int target) {
        if (idx == values.length) {
            return target == 0 ? 1 : 0;
        }
        int ans = 0;
        for (int n = 0; n <= nums[idx] && n * values[idx] <= target; n++) {
            ans += process1(values, nums, idx + 1, target - n * values[idx]);
        }
        return ans;
    }

    public static int ways2(int[] coins, int target) {
        if (coins == null || coins.length == 0 || target < 0) {
            return 0;
        }
        Info info = new Info(coins);
        int[] values = info.values;
        int[] nums = info.nums;
        int N = values.length;
        int[][] dp = new int[N + 1][target + 1];
        dp[N][0] = 1;
        for (int i = N - 1; i >= 0; i--) {
            for (int j = 0; j <= target; j++) {
                for (int n = 0; n <= nums[i] && n * values[i] <= j; n++) {
                    dp[i][j] += dp[i + 1][j - n * values[i]];
                }
            }
        }
        return dp[0][target];
    }

    public static int ways3(int[] coins, int target) {
        if (coins == null || coins.length == 0 || target < 0) {
            return 0;
        }
        Info info = new Info(coins);
        int[] values = info.values;
        int[] nums = info.nums;
        int N = values.length;
        int[][] dp = new int[N + 1][target + 1];
        dp[N][0] = 1;
        for (int i = N - 1; i >= 0; i--) {
            for (int j = 0; j <= target; j++) {
                dp[i][j] = dp[i + 1][j];
                if (j - values[i] >= 0) {
                    dp[i][j] += dp[i][j - values[i]];
                }
                if (j - (nums[i] + 1) * values[i] >= 0) {
                    dp[i][j] -= dp[i + 1][j - (nums[i] + 1) * values[i]];
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
        int maxLen = 10;
        int maxValue = 20;
        int testTime = 10000;
        for (int i = 0; i < testTime; i++) {
            int[] coins = randomArray(maxLen, maxValue);
            int target = (int) (Math.random() * maxValue);
            int ans1 = ways1(coins, target);
            int ans2 = ways2(coins, target);
            int ans3 = ways3(coins, target);
            if (ans1 != ans2 || ans1 != ans3) {
                System.out.println("Failed");
                return;
            }
        }
        System.out.println("Test passed!");
    }

}
