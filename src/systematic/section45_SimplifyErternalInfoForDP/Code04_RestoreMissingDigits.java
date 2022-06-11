package systematic.section45_SimplifyErternalInfoForDP;

/**
 * @Author: duccio
 * @Date: 11, 06, 2022
 * @Description: An integer array arr of size n (3 <= n <= 10**4), with element in range [1, 200], has three rules:
 *                      1. arr[0] <= arr[1];
 *                      2. arr[n-1] <= arr[n-1];
 *                      3. arr[i] <= max{arr[i-1], arr[i+1]}.
 *               There are some digits int it missed, denoted as 0. Return the ways of restoring these missing values.
 * @Note:   Ver1. brute force.
 *          Ver2. dp.
 *          Ver3. dp with a little acceleration.
 *          ======
 *          Key idea: for any range arr[0..i], introduce an extra argument to indicate the relationship between
 *                    arr[i] and arr[i+1], since the value at arr[i] depends on max{arr[i+1], arr[i-1]}.
 */
public class Code04_RestoreMissingDigits {

    public static int ways1(int[] arr) {
        int N = arr.length;
        if (arr[N - 1] != 0) {
            return process12(arr, N - 1, arr[N - 1], 2);
        } else {
            int ans = 0;
            for (int val = 1; val <= 200; val++) {
                ans += process12(arr, N - 1, val, 2);
            }
            return ans;
        }
    }

    // for arr[0..end], arr[end] must be val
    // mode == 0: arr[end + 1] > arr[end]
    //         1: arr[end + 1] == arr[end]
    //         2: arr[end + 1] < arr[end]
    private static int process1(int[] arr, int end, int val, int mode) {
        if (end == 0) {
            return ((mode != 2) && (arr[0] == 0 || arr[0] == val)) ? 1 : 0;
        }
        if (arr[end] != 0 && arr[end] != val) {
            return 0;
        }
        int ans = 0;
        if (mode == 0 || mode == 1) {
            for (int pre = 1; pre <= 200; pre++) {
                ans += process1(arr, end - 1, pre, pre < val ? 0 : (pre == val ? 1 : 2));
            }
        } else {
            for (int pre = val; pre <= 200; pre++) {
                ans += process1(arr, end - 1, pre, pre == val ? 1 : 2);
            }
        }
        return ans;
    }

    // simplify a little bit
    public static int process12(int[] arr, int i, int v, int s) {
        if (i == 0) {
            return ((s != 2) && (arr[0] == 0 || arr[0] == v)) ? 1 : 0;
        }
        if (arr[i] != 0 && arr[i] != v) {
            return 0;
        }
        int ans = 0;
        if (s == 0 || s == 1) {
            for (int pre = 1; pre < v; pre++) {
                ans += process12(arr, i - 1, pre, 0);
            }
        }
        ans += process12(arr, i - 1, v, 1);
        for (int pre = v + 1; pre <= 200; pre++) {
            ans += process12(arr, i - 1, pre, 2);
        }
        return ans;
    }

    // dp based on above brute force
    public static int ways2(int[] arr) {
        int N = arr.length;
        int[][][] dp = new int[N][201][3];
        // base case
        if (arr[0] == 0) {
            for (int v = 1; v <= 200; v++) {
                dp[0][v][0] = 1;
                dp[0][v][1] = 1;
            }
        } else {
            dp[0][arr[0]][0] = 1;
            dp[0][arr[0]][1] = 1;
        }
        // fill in
        for (int i = 1; i < N; i++) {
            for (int v = 1; v <= 200; v++) {
                for (int s = 0; s < 3; s++) {

                    if (arr[i] == 0 || arr[i] == v) {
                        if (s == 0 || s == 1) {
                            for (int pre = 1; pre < v; pre++) {
                                dp[i][v][s] += dp[i - 1][pre][0];
                            }
                        }
                        dp[i][v][s] += dp[i - 1][v][1];
                        for (int pre = v + 1; pre <= 200; pre++) {
                            dp[i][v][s] += dp[i - 1][pre][2];
                        }
                    }

                }
            }
        }
        if (arr[N - 1] != 0) {
            return dp[N - 1][arr[N - 1]][2];
        } else {
            int ans = 0;
            for (int v = 1; v <= 200; v++) {
                ans += dp[N - 1][v][2];
            }
            return ans;
        }
    }

    // use pre-process structure to accelerate
    public static int ways3(int[] arr) {
        int N = arr.length;
        int[][][] dp = new int[N][201][3];
        // base case
        if (arr[0] != 0) {
            dp[0][arr[0]][0] = 1;
            dp[0][arr[0]][1] = 1;
        } else {
            for (int v = 1; v <= 200; v++) {
                dp[0][v][0] = 1;
                dp[0][v][1] = 1;
            }
        }
        // pre-process structure
        int[][] preSum = new int[201][3];
        for (int v = 1; v <= 200; v++) {
            for (int s = 0; s < 3; s++) {
                preSum[v][s] = preSum[v - 1][s] + dp[0][v][s];
            }
        }
        // fill in
        for (int i = 1; i < N; i++) {
            for (int v = 1; v <= 200; v++) {
                for (int s = 0; s < 3; s++) {
                    if (arr[i] == 0 || arr[i] == v) {
                        if (s == 0 || s == 1) {
                            dp[i][v][s] += preSum[v - 1][0] - preSum[0][0];
                        }
                        dp[i][v][s] += dp[i - 1][v][1];
                        dp[i][v][s] += preSum[200][2] - preSum[v][2];
                    }
                }
            }
            // update pre-sum
            for (int v = 1; v <= 200; v++) {
                for (int s = 0; s < 3; s++) {
                    preSum[v][s] = preSum[v - 1][s] + dp[i][v][s];
                }
            }
        }
        if (arr[N - 1] != 0) {
            return dp[N - 1][arr[N - 1]][2];
        } else {
            return preSum[200][2] - preSum[0][2];
        }
    }


    public static int[] generateRandomArray(int len) {
        int[] ans = new int[len];
        for (int i = 0; i < ans.length; i++) {
            if (Math.random() < 0.5) {
                ans[i] = 0;
            } else {
                ans[i] = (int) (Math.random() * 200) + 1;
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        int testTime = 15;
        int len = 4;
        System.out.println("Test begin");
        for (int i = 0; i < testTime; i++) {
            int N = (int) (Math.random() * len) + 2;
            int[] arr = generateRandomArray(N);
            int ans1 = ways2(arr);
            int ans2 = ways3(arr);
            if (ans1 != ans2) {
                System.out.println("Failed");
                return;
            }
        }
        System.out.println("Test passed");
    }

}
