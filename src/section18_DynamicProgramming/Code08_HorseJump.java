package section18_DynamicProgramming;

/**
 * @Author: duccio
 * @Date: 26, 04, 2022
 * @Description: Imagine a chess board on the first domain of an x-y coordinates, with its bottom-left conner on the
 *      (0, 0) point. The chess board has 10 cells horizontally and 9 cells vertically. Given a horse at (0, 0), which
 *      can only move diagonally two cells away, and a target position on the board, return how many ways it can stop
 *      at the target position with K moves.
 * @Note:   Ver1. Brute force, where there is 8 directions for a horse to move each step.
 *          Ver2. DP. Use a 3-d dp table. To handle out-of-boundary access in the table, use an extra function.
 *          ======
 *          - Base case: out-of-boundary and k == 0.
 */
public class Code08_HorseJump {

    public static void main(String[] args) {
        int x = 7;
        int y = 7;
        int K = 10;
        System.out.println(horse1(x, y, K));
        System.out.println(horse2(x, y, K));
    }

    public static int horse1(int tx, int ty, int K) {
        return process1(tx, ty, 0, 0, K);
    }

    public static int process1(int tx, int ty, int cx, int cy, int k) {
        if (cx < 0 || cx > 9 || cy < 0 || cy > 8) {
            return 0;
        }
        if (k == 0) {
            return cx == tx && cy == ty ? 1 : 0;
        }
        int ans = 0;
        ans += process1(tx, ty, cx + 2, cy + 1, k - 1);
        ans += process1(tx, ty, cx + 2, cy - 1, k - 1);
        ans += process1(tx, ty, cx + 1, cy + 2, k - 1);
        ans += process1(tx, ty, cx + 1, cy - 2, k - 1);
        ans += process1(tx, ty, cx - 1, cy + 2, k - 1);
        ans += process1(tx, ty, cx - 1, cy - 2, k - 1);
        ans += process1(tx, ty, cx - 2, cy - 1, k - 1);
        ans += process1(tx, ty, cx - 2, cy + 1, k - 1);
        return ans;
    }

    public static int horse2(int tx, int ty, int K) {
        int[][][] dp = new int[10][9][K + 1];
        // base case
        dp[tx][ty][0] = 1;
        // filling in
        for (int k = 1; k <= K; k++) {
            for (int cx = 0; cx < 10; cx++) {
                for (int cy = 0; cy < 9; cy++) {
                    dp[cx][cy][k] = 0;
                    dp[cx][cy][k] += pick(cx + 2, cy + 1, k - 1, dp);
                    dp[cx][cy][k] += pick(cx + 2, cy - 1, k - 1, dp);
                    dp[cx][cy][k] += pick(cx + 1, cy + 2, k - 1, dp);
                    dp[cx][cy][k] += pick(cx + 1, cy - 2, k - 1, dp);
                    dp[cx][cy][k] += pick(cx - 2, cy + 1, k - 1, dp);
                    dp[cx][cy][k] += pick(cx - 2, cy - 1, k - 1, dp);
                    dp[cx][cy][k] += pick(cx - 1, cy + 2, k - 1, dp);
                    dp[cx][cy][k] += pick(cx - 1, cy - 2, k - 1, dp);
                }
            }
        }
        return dp[0][0][K];
    }

    public static int pick(int x, int y, int k, int[][][] dp) {
        if (x < 0 || x > 9 || y < 0 || y > 8) {
            return 0;
        }
        return dp[x][y][k];
    }

}
