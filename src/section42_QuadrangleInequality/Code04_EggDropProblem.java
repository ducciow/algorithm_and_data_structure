package section42_QuadrangleInequality;

/**
 * @Author: duccio
 * @Date: 05, 06, 2022
 * @Description: You are given k identical eggs and you have access to a building with n floors labeled from 1 to n.
 *      You know that there exists a floor f where 1 <= f <= n such that any egg dropped at a floor higher than f will
 *      break, and any egg dropped at or below floor f will not break. Each move, you may take an unbroken egg and
 *      drop it from any floor x (where 1 <= x <= n). If the egg breaks, you can no longer use it. However, if the egg
 *      does not break, you may reuse it in future moves. Return the minimum number of moves that you need to determine
 *      with certainty what the value of f is.
 *      https://leetcode.com/problems/super-egg-drop/
 * @Note:   Ver1. brute force, Time Limit Exceeded
 *          Ver2. dp with iteration, Time Limit Exceeded
 *          Ver3. dp with iteration, Time Limit Exceeded
 *          Ver4. dp using another strategy
 */
public class Code04_EggDropProblem {

    public static int superEggDrop1(int k, int n) {
        if (k < 1 || n < 1) {
            return 0;
        }
        return process(n, k);
    }

    // there are n floors need to test with k eggs
    private static int process(int n, int k) {
        if (n == 0) {
            return 0;
        }
        if (k == 1) {
            return n;
        }
        int ans = Integer.MAX_VALUE;
        for (int i = 1; i <= n; i++) {  // drop at the i-th floor
            int p1 = process(i - 1, k - 1);  // the egg breaks, go test floors below
            int p2 = process(n - i, k);  // the egg does not break go test floors above
            ans = Math.min(ans, Math.max(p1, p2));
        }
        return ans + 1;
    }

    public static int superEggDrop2(int kEgg, int nLevel) {
        if (kEgg < 1 || nLevel < 1) {
            return 0;
        }
        if (kEgg == 1) {
            return nLevel;
        }
        int[][] dp = new int[nLevel + 1][kEgg + 1];
        for (int n = 1; n <= nLevel; n++) {
            dp[n][1] = n;
        }
        for (int k = 2; k <= kEgg; k++) {
            for (int n = 1; n <= nLevel; n++) {
                int ans = Integer.MAX_VALUE;
                for (int i = 1; i <= n; i++) {
                    int p1 = dp[i - 1][k - 1];
                    int p2 = dp[n - i][k];
                    ans = Math.min(ans, Math.max(p1, p2));
                }
                dp[n][k] = ans + 1;
            }
        }
        return dp[nLevel][kEgg];
    }

    public static int superEggDrop3(int kEgg, int nLevel) {
        if (kEgg < 1 || nLevel < 1) {
            return 0;
        }
        if (kEgg == 1) {
            return nLevel;
        }
        int[][] dp = new int[nLevel + 1][kEgg + 1];
        int[][] best = new int[nLevel + 1][kEgg + 1];
        for (int n = 1; n <= nLevel; n++) {
            dp[n][1] = n;
        }
        for (int k = 1; k <= kEgg; k++) {
            dp[1][k] = 1;
            best[1][k] = 1;
        }
        for (int n = 2; n <= nLevel; n++) {
            for (int k = kEgg; k > 1; k--) {
                int ans = Integer.MAX_VALUE;
                int choose = -1;
                int lower = best[n - 1][k];
                int upper = k == kEgg ? n : best[n][k + 1];
                for (int i = lower; i <= upper; i++) {
                    int cur = Math.max(dp[i - 1][k - 1], dp[n - i][k]);
                    if (cur <= ans) {
                        ans = cur;
                        choose = i;
                    }
                }
                dp[n][k] = ans + 1;
                best[n][k] = choose;
            }
        }
        return dp[nLevel][kEgg];
    }

    // row: number of eggs, column: number of tests
    // cell: given i eggs and j tests, how many floors can be solved
    // key idea of filling in:
    //  assume dp[i-1][j-1] = a, and dp[i][j-1] = b,
    //  then dp[i][j] = a + b + 1
    public static int superEggDrop4(int kEgg, int nLevel) {
        if (kEgg < 1 || nLevel < 1) {
            return 0;
        }
        if (kEgg == 1) {
            return nLevel;
        }
        int[] dp = new int[kEgg + 1];  // column that is reused iteratively
        int numTest = 0;
        while (true) {
            numTest++;  // num of tests starts from 1
            int preUp = 0;
            for (int k = 1; k <= kEgg; k++) {
                int pre = dp[k];
                dp[k] = preUp + pre + 1;
                preUp = pre;
                if (dp[k] >= nLevel) {
                    return numTest;
                }
            }
        }
    }

}
