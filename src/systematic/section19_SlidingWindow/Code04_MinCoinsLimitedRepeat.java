package systematic.section19_SlidingWindow;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * @Author: duccio
 * @Date: 03, 05, 2022
 * @Description: Given an array of coins with repeated coin values, and a target value. Return the minimum number of
 *      coins chosen from the array that sum up to the target value.
 * @Note:   Ver1. brute force and dp by treating repeated coins as different, because it does not matter for getting min.
 *                Its time complexity is O(N * target).
 *          Ver2. brute force and dp by grouping coin types, with time complexity O(N) + O(M * target * avg_num_each_type),
 *                where M refers to the number of coins types.
 *          Ver3. dp using a sliding window for tracking minimum, with time complexity O(N) + O(M * target).
 *          ======
 *          1. Ver2 has loops for a single cell, but it cannot be optimized by direct dependencies because the dependent
 *             cell returns minimum instead of sum that likely drops desired information.
 *          2. When use a sliding window to fill in the dp table, fill rows one by one, while fill columns at with
 *             intervals, where the interval is the row-th coin value.
 */
public class Code04_MinCoinsLimitedRepeat {

    public static void main(String[] args) {
        validate();
    }

    public static int min1(int[] coins, int target) {
        if (coins == null || coins.length < 1 || target < 1) {
            return Integer.MAX_VALUE;
        }
        return process1(coins, 0, target);
    }

    public static int process1(int[] coins, int idx, int target) {
        if (target < 0) {
            return Integer.MAX_VALUE;
        }
        if (idx == coins.length) {
            return target == 0 ? 0 : Integer.MAX_VALUE;
        }
        int p1 = process1(coins, idx + 1, target);
        int p2 = process1(coins, idx + 1, target - coins[idx]);
        if (p2 != Integer.MAX_VALUE) {
            p2++;
        }
        return Math.min(p1, p2);
    }

    public static int dp1(int[] coins, int target) {
        if (coins == null || coins.length < 1 || target < 1) {
            return Integer.MAX_VALUE;
        }
        int N = coins.length;
        int[][] dp = new int[N + 1][target + 1];
        for (int j = 1; j <= target; j++) {
            dp[N][j] = Integer.MAX_VALUE;
        }
        for (int i = N - 1; i >= 0; i--) {
            for (int j = 0; j <= target; j++) {
                int p1 = dp[i + 1][j];
                int p2 = j - coins[i] >= 0 ? dp[i + 1][j - coins[i]] : Integer.MAX_VALUE;
                if (p2 != Integer.MAX_VALUE) {
                    p2++;
                }
                dp[i][j] = Math.min(p1, p2);
            }
        }
        return dp[0][target];
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

    public static int min2(int[] coins, int target) {
        if (coins == null || coins.length < 1 || target < 1) {
            return Integer.MAX_VALUE;
        }
        Info info = new Info(coins);
        return process2(info.values, info.nums, 0, target);
    }

    public static int process2(int[] values, int[] nums, int idx, int target) {
        if (idx == values.length) {
            return target == 0 ? 0 : Integer.MAX_VALUE;
        }
        int ans = Integer.MAX_VALUE;
        for (int k = 0; k <= nums[idx] && target >= k * values[idx]; k++) {
            int next = process2(values, nums, idx + 1, target - k * values[idx]);
            if (next != Integer.MAX_VALUE) {
                ans = Math.min(ans, k + next);
            }
        }
        return ans;
    }

    public static int dp2(int[] coins, int target) {
        if (coins == null || coins.length < 1 || target < 1) {
            return Integer.MAX_VALUE;
        }
        Info info = new Info(coins);
        int[] values = info.values;
        int[] nums = info.nums;
        int N = values.length;
        int[][] dp = new int[N + 1][target + 1];
        for (int j = 1; j <= target; j++) {
            dp[N][j] = Integer.MAX_VALUE;
        }
        for (int i = N - 1; i >= 0; i--) {
            for (int j = 0; j <= target; j++) {
                int ans = Integer.MAX_VALUE;
                for (int k = 0; k <= nums[i] && j >= k * values[i]; k++) {
                    int next = dp[i + 1][j - k * values[i]];
                    if (next != Integer.MAX_VALUE) {
                        ans = Math.min(ans, k + next);
                    }
                }
                dp[i][j] = ans;
            }
        }
        return dp[0][target];
    }

    public static int dp3(int[] coins, int target) {
        if (coins == null || coins.length < 1 || target < 1) {
            return Integer.MAX_VALUE;
        }
        Info info = new Info(coins);
        int[] values = info.values;
        int[] nums = info.nums;
        int N = values.length;
        int[][] dp = new int[N + 1][target + 1];
        for (int j = 1; j <= target; j++) {
            dp[N][j] = Integer.MAX_VALUE;
        }
        for (int i = N - 1; i >= 0; i--) {
            // mod is from 0 to values[i] - 1
            for (int mod = 0; mod < Math.min(target + 1, values[i]); mod++) {
                LinkedList<Integer> min = new LinkedList<>();
                // firstly fill in the mod-th column
                min.add(mod);
                dp[i][mod] = dp[i + 1][mod];
                // then fill from mod + values[i], to the last mod + some * values[i]
                for (int j = mod + values[i]; j <= target; j += values[i]) {
                    while (!min.isEmpty() &&
                            (dp[i + 1][min.peekLast()] == Integer.MAX_VALUE ||
                                    dp[i + 1][min.peekLast()] + offset(j, min.peekLast(), values[i]) >= dp[i + 1][j])) {
                        min.pollLast();
                    }
                    min.addLast(j);
                    int L = j - values[i] * nums[i];
                    if (min.peekFirst() == L - values[i]) {
                        min.pollFirst();
                    }
                    dp[i][j] = dp[i + 1][min.peekFirst()] + offset(j, min.peekFirst(), values[i]);
                }
            }
        }
        return dp[0][target];
    }

    public static int offset(int cur, int pre, int value) {
        return (cur - pre) / value;
    }


    public static int[] randomArray(int N, int maxValue) {
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
            int N = (int) (Math.random() * maxLen);
            int[] coins = randomArray(N, maxValue);
            int target = (int) (Math.random() * maxValue);
            int ans1 = dp1(coins, target);
            int ans2 = dp3(coins, target);
            if (ans1 != ans2) {
                System.out.println("Failed");
                System.out.println(ans1);
                System.out.println(ans2);
                return;
            }
        }
        System.out.println("Test passed!");
    }

}
