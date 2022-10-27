package section18_DynamicProgramming;

/**
 * @Author: duccio
 * @Date: 26, 04, 2022
 * @Description: Given a string, return the longest length of its palindrome subsequence.
 *      https://leetcode.com/problems/longest-palindromic-subsequence/
 * @Note:   Ver1. Brute force.
 *          Ver2. DP.
 *                - the ordering of filling in is bounded from the diagonal.
 *          ======
 *          - Varying arguments are index L and R.
 *          - Base cases: L == R, and L+1 == R.
 *          - At each processing step, there are three possibilities.
 *          ======
 *          - Here attempts within a range of the string.
 *          - Another idea is to find the longest common subsequence between its reverse string and itself.
 */
public class Code07_PalindromeSubsequence {

    public int longestPalindromeSubseq1(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        return process1(s.toCharArray(), 0, s.length() - 1);
    }

    public static int process1(char[] chars, int L, int R) {
        if (L == R) {
            return 1;
        }
        if (L == R - 1) {
            return chars[L] == chars[R] ? 2 : 1;
        }
        int p1 = process1(chars, L + 1, R);
        int p2 = process1(chars, L, R - 1);
        int p3 = chars[L] == chars[R] ? 2 + process1(chars, L + 1, R - 1) : 0;
        return Math.max(Math.max(p1, p2), p3);
    }

    public int longestPalindromeSubseq2(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        char[] chars = s.toCharArray();
        int N = chars.length;
        int[][] dp = new int[N][N];
        // base case 1
        for (int i = 0; i < N; i++) {
            dp[i][i] = 1;
        }
        // base case 2
        for (int j = 1; j < N; j++) {
            dp[j - 1][j] = chars[j - 1] == chars[j] ? 2 : 1;
        }
        // from bottom to up
        for (int L = N - 3; L >= 0; L--) {
            /// from diagonal to right
            for (int R = L + 2; R < N; R++) {
                int p1 = dp[L + 1][R];
                int p2 = dp[L][R - 1];
                int p3 = chars[L] == chars[R] ? 2 + dp[L + 1][R - 1] : 0;
                dp[L][R] = Math.max(Math.max(p1, p2), p3);
            }
        }
        return dp[0][N - 1];
    }

}
