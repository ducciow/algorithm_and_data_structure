package systematic.section42_QuadrangleInequality;

/**
 * @Author: duccio
 * @Date: 04, 06, 2022
 * @Description: Given an array nums which consists of non-negative integers and an integer m, you can split the array
 *      into m non-empty continuous subarrays. Write an algorithm to minimize the largest sum among these m subarrays.
 *      https://leetcode.com/problems/split-array-largest-sum/
 * @Note:   Ver1. dp with iteration.
 *          Ver2. dp without iteration.
 *          Ver3. another better strategy.
 */
public class Code02_SplitArrayLargestSum {

    // row: 0~i elements
    // column: j subarrays
    // splitting by the index of the last subarray starting to cover
    public static int splitArray1(int[] arr, int k) {
        if (arr == null || arr.length == 0 || k <= 0) {
            return 0;
        }
        int N = arr.length;
        int[] preSums = new int[N + 1];
        for (int i = 0; i < N; i++) {
            preSums[i + 1] = preSums[i] + arr[i];
        }
        int[][] dp = new int[N][k + 1];
        // first row:
        for (int j = 1; j <= k; j++) {
            dp[0][j] = arr[0];
        }
        // first column is 0
        // second column:
        for (int i = 1; i < N; i++) {
            dp[i][1] = preSums[i + 1];
        }
        for (int i = 1; i < N; i++) {
            for (int j = 2; j <= k; j++) {
                int next = Integer.MAX_VALUE;
                for (int split = 0; split <= i; split++) {
                    int costL = dp[split][j - 1];
                    int costR = preSums[i + 1] - preSums[split + 1];
                    int cur = Math.max(costL, costR);
                    if (cur < next) {
                        next = cur;
                    }
                }
                dp[i][j] = next;
            }
        }
        return dp[N - 1][k];
    }

    public static int splitArray2(int[] arr, int k) {
        if (arr == null || arr.length == 0 || k <= 0) {
            return 0;
        }
        int N = arr.length;
        int[] preSums = new int[N + 1];
        for (int i = 0; i < N; i++) {
            preSums[i + 1] = preSums[i] + arr[i];
        }
        int[][] dp = new int[N][k + 1];
        int[][] best = new int[N][k + 1];
        for (int j = 1; j <= k; j++) {
            dp[0][j] = arr[0];
            best[0][j] = -1;  // meaning no previous subarrays, because there is just one item
        }
        for (int i = 1; i < N; i++) {
            dp[i][1] = preSums[i + 1];
            best[i][1] = -1; // meaning no previous subarrays, because j==1 means only one subarray
        }
        // column: from left to right
        // row: from bottom to top
        for (int j = 2; j <= k; j++) {
            for (int i = N - 1; i > 0; i--) {
                int next = Integer.MAX_VALUE;
                int choose = -1;
                int lower = best[i][j - 1];
                int upper = i == N - 1 ? N - 1 : best[i + 1][j];
                for (int split = lower; split <= upper; split++) {
                    int costL = split == -1 ? 0 : dp[split][j - 1];
                    int costR = preSums[i + 1] - preSums[split + 1];
                    int cur = Math.max(costL, costR);
                    if (cur < next) {
                        next = cur;
                        choose = split;
                    }
                }
                dp[i][j] = next;
                best[i][j] = choose;
            }
        }
        return dp[N - 1][k];
    }

    // another strategy:
    // given this array and an aim minimum, find the required number of subarrays
    // binary searching
    public static int splitArray3(int[] arr, int k) {
        int sum = 0;
        for (int i = 0; i < arr.length; i++) {
            sum += arr[i];
        }
        long l = 0;
        long r = sum;
        long ans = 0;
        while (l <= r) {
            long mid = l + (r - l) / 2;
            int cur = getRequired(arr, mid);
            if (cur <= k) {
                ans = mid;
                r = mid - 1;
            } else {
                l = mid + 1;
            }
        }
        return (int) ans;
    }

    public static int getRequired(int[] arr, long aim) {
        int N = arr.length;
        for (int i = 0; i < N; i++) {
            if (arr[i] > aim) {
                return Integer.MAX_VALUE;
            }
        }
        int ans = 1;
        int sum = arr[0];
        for (int i = 1; i < N; i++) {
            if (sum + arr[i] > aim) {
                ans++;
                sum = arr[i];
            } else {
                sum += arr[i];
            }
        }
        return ans;
    }

}
