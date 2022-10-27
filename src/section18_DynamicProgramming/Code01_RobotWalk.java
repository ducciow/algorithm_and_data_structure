package section18_DynamicProgramming;

/**
 * @Author: duccio
 * @Date: 22, 04, 2022
 * @Description: A robot can walk along a line forwards or backwards except on the head/tail of the line that it can
 *      only move backwards. Given N (the maximum position of the line, [1, ..., N]), K (the walk steps of the robot),
 *      start (the beginning position of the robot on the line), and aim (the aim position), return the number of ways
 *      the robot stops at the aim position.
 * @Note:   Ver1. Brute forcing recursion.
 *          Ver2. Use a naive cache for lookup.
 *          Ver3. DP, where the varying arguments are current position and remain steps.
 */
public class Code01_RobotWalk {

    public static void main(String[] args) {
        System.out.println(ways1(5, 2, 4, 6));
        System.out.println(ways2(5, 2, 4, 6));
        System.out.println(ways3(5, 2, 4, 6));
    }

    public static int ways1(int N, int start, int aim, int K) {
        if (N < 2 || start < 1 || start > N || aim < 1 || aim > N || K < 0) {
            return 0;
        }
        return process1(N, start, aim, K);
    }

    public static int process1(int N, int cur, int aim, int k) {
        if (k == 0) {
            return cur == aim ? 1 : 0;
        }
        if (cur == 1) {
            return process1(N, 2, aim, k - 1);
        }
        if (cur == N) {
            return process1(N, N - 1, aim, k - 1);
        }
        return process1(N, cur - 1, aim, k - 1) + process1(N, cur + 1, aim, k - 1);
    }


    public static int ways2(int N, int start, int aim, int K) {
        if (N < 2 || start < 1 || start > N || aim < 1 || aim > N || K < 0) {
            return 0;
        }
        int[][] lookup = new int[N + 1][K + 1];
        for (int i = 0; i <= N; i++) {
            for (int j = 0; j <= K; j++) {
                lookup[i][j] = -1;
            }
        }
        return process2(N, start, aim, K, lookup);
    }

    public static int process2(int N, int cur, int aim, int k, int[][] lookup) {
        if (lookup[cur][k] != -1) {
            return lookup[cur][k];
        }
        int ans = 0;
        if (k == 0) {
            ans = cur == aim ? 1 : 0;
        } else if (cur == 1) {
            ans = process2(N, 2, aim, k - 1, lookup);
        } else if (cur == N) {
            ans = process2(N, N - 1, aim, k - 1, lookup);
        } else {
            ans = process2(N, cur - 1, aim, k - 1, lookup) + process2(N, cur + 1, aim, k - 1, lookup);
        }
        lookup[cur][k] = ans;
        return ans;
    }


    public static int ways3(int N, int start, int aim, int K) {
        if (N < 2 || start < 1 || start > N || aim < 1 || aim > N || K < 0) {
            return 0;
        }
        int[][] dp = new int[N + 1][K + 1];
        // base case
        dp[aim][0] = 1;
        // fill other cells, from left to right, ie, column-wise
        for (int k = 1; k <= K; k++) {
            dp[1][k] = dp[2][k - 1];
            for (int cur = 2; cur < N; cur++) {
                dp[cur][k] = dp[cur - 1][k - 1] + dp[cur + 1][k - 1];
            }
            dp[N][k] = dp[N - 1][k - 1];
        }
        return dp[start][K];
    }

}
