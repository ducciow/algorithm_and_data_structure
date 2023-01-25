package section19_SlidingWindow;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * @Author: duccio
 * @Date: 03, 05, 2022
 * @Description: Given an array of coins with repeated coin values, and a target value, return the minimum number of
 *      coins chosen from the array that sum up to the target value.
 * @Note:   Ver1. brute force and dp by treating repeated coins as different coins, because it does not matter for
 *                getting min. Its time complexity is O(N * target).
 *          Ver2. brute force and dp by grouping coins, with time complexity O(N) + O(M * target * avg_num_each_type),
 *                where M refers to the number of coin types.
 *          Ver3. dp using a sliding window for tracking minimum, with time complexity O(N) + O(M * target).
 *          ======
 *          - Ver2 has loops for a single cell, but it cannot be optimized by direct dependencies. Because dependent
 *            cells here only memorize the minimums within a range, i.e., partial information. To the contrast,
 *            dependent cells that memorize sums within a range do not have such a problem, because they have full
 *            information, where out-of-range information can be easily manipulated by addition/subtraction of
 *            irrelevant cells.
 *          - That is why a sliding window can be utilized here to track minimums within a range. When use a sliding
 *            window to fill in the dp table, fill rows one by one, while fill columns at intervals, where the
 *            interval is equal to the row-th coin value, e.g., for coin value 3: 0, 3, 6,...,1, 4, 7,...,2, 5, 8,...
 *          - When update elements in the sliding window, an offset of coin numbers is needed for comparison.
 */
public class Code04_MinCoinsLimitedRepeat {

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

        public Info(int[] coins) {
            construct(coins);
        }

        public void construct(int[] coins) {
            HashMap<Integer, Integer> map = new HashMap<>();
            for (int coin : coins) {
                map.put(coin, map.getOrDefault(coin, 0) + 1);
            }
            int M = map.size();
            values = new int[M];
            nums = new int[M];
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
        int M = values.length;
        int[][] dp = new int[M + 1][target + 1];
        for (int j = 1; j <= target; j++) {
            dp[M][j] = Integer.MAX_VALUE;
        }
        for (int i = M - 1; i >= 0; i--) {
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
        int M = values.length;
        int[][] dp = new int[M + 1][target + 1];
        for (int j = 1; j <= target; j++) {
            dp[M][j] = Integer.MAX_VALUE;
        }
        for (int i = M - 1; i >= 0; i--) {
            // mod is from 0 to values[i] - 1, e.g., for coin value 3, mod is 0, 1, 2
            // used for filling at intervals
            for (int mod = 0; mod < Math.min(target + 1, values[i]); mod++) {
                // deque, for coin value x, the window covers columns: mod, mod+x, mod+2x, mod+3x, ...,
                // i.e., track minimums among those elements
                LinkedList<Integer> min = new LinkedList<>();
                // first, fill in the mod-th column
                min.add(mod);
                dp[i][mod] = dp[i + 1][mod];
                // then, mod+x, mod+2x, mod+3x, ...
                for (int j = mod + values[i]; j <= target; j += values[i]) {
                    // update deque
                    while (!min.isEmpty() &&
                            (dp[i + 1][min.peekLast()] == Integer.MAX_VALUE ||
                                    dp[i + 1][min.peekLast()] + offset(j, min.peekLast(), values[i]) >= dp[i + 1][j])) {
                        min.pollLast();
                    }
                    min.addLast(j);
                    // check outdated element in deque
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

    public static void main(String[] args) {
        int maxLen = 2000;
        int maxValue = 30;
        int testTime = 10000;
        System.out.println("Test begin...");
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
